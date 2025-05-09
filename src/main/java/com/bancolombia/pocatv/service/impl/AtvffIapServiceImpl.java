package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.model.AtvffIap.AtvffIapId;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffIapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AtvffIapServiceImpl implements AtvffIapService {

    @Autowired
    private AtvffIapRepository atvffIapRepository;
    
    @Autowired
    private AtvffPdoRepository atvffPdoRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffFreRepository atvffFreRepository;
    
    @Autowired
    private XbknamRepository xbknamRepository;

    @Override
    @Transactional
    public List<AtvffIap> generarArchivoCalidadInformacion(Integer ano, String fechaParam) {
        List<AtvffIap> resultados = new ArrayList<>();
        
        // Obtener fecha actual si no se proporciona
        String fecha = (fechaParam != null && !fechaParam.isEmpty()) 
            ? fechaParam 
            : LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Extraer mes y año de la fecha
        int year = Integer.parseInt(fecha.substring(0, 4));
        int month = Integer.parseInt(fecha.substring(5, 7));
        
        // Obtener todos los productos
        List<AtvffPdo> productos = atvffPdoRepository.findAll();
        
        for (AtvffPdo producto : productos) {
            // Procesar cada producto
            AtvffIap registroIap = procesarProducto(producto, ano, year, month);
            if (registroIap != null) {
                resultados.add(registroIap);
            }
        }
        
        return resultados;
    }
    
    private AtvffIap procesarProducto(AtvffPdo producto, Integer ano, int yearActual, int monthActual) {
        String copr = producto.getXpcopr();
        String codo = producto.getXpcodo();
        
        // Inicializar valores mensuales
        int[] valoresMensuales = new int[12];
        int totalMeses = 0;
        int sumaTotal = 0;
        
        // Determinar hasta qué mes procesar
        int mesLimite = (yearActual == ano) ? monthActual : 12;
        
        for (int mes = 1; mes <= mesLimite; mes++) {
            int calidad = calcularCalidadMes(copr, codo, ano, mes);
            valoresMensuales[mes-1] = calidad;
            
            if (calidad > 0) {
                sumaTotal += calidad;
                totalMeses++;
            }
        }
        
        // Calcular promedio
        int promedio = (totalMeses > 0) ? sumaTotal / totalMeses : 0;
        
        // Crear o actualizar registro en ATVFFIAP
        AtvffIapId id = new AtvffIapId();
        id.setIpAnos(ano);
        id.setIpCodpro(Integer.parseInt(copr));
        id.setIpCoddoc(Integer.parseInt(codo));
        
        AtvffIap atvffIap = atvffIapRepository.findById(id)
                .orElse(new AtvffIap());
        
        if (atvffIap.getId() == null) {
            atvffIap.setId(id);
        }
        
        // Actualizar datos
        atvffIap.setIpDocument(producto.getXpdsdo());
        atvffIap.setIpProma(promedio);
        atvffIap.setIpEnero(valoresMensuales[0]);
        atvffIap.setIpFeb(valoresMensuales[1]);
        atvffIap.setIpMarzo(valoresMensuales[2]);
        atvffIap.setIpAbril(valoresMensuales[3]);
        atvffIap.setIpMayo(valoresMensuales[4]);
        atvffIap.setIpJunio(valoresMensuales[5]);
        atvffIap.setIpJulio(valoresMensuales[6]);
        atvffIap.setIpAgosto(valoresMensuales[7]);
        atvffIap.setIpSep(valoresMensuales[8]);
        atvffIap.setIpOctubre(valoresMensuales[9]);
        atvffIap.setIpNov(valoresMensuales[10]);
        atvffIap.setIpDic(valoresMensuales[11]);
        
        // Guardar en base de datos
        return atvffIapRepository.save(atvffIap);
    }
    
    private int calcularCalidadMes(String copr, String codo, Integer ano, int mes) {
        // Obtener frecuencia de arqueo para el producto
        Optional<AtvffFre> frecuenciaOpt = atvffFreRepository.findByFxCoprAndFxCodo(copr, codo);
        if (frecuenciaOpt.isEmpty()) {
            return 0;
        }
        AtvffFre frecuencia = frecuenciaOpt.get();
        
        // Obtener información del mes
        AtvffmdId mdId = new AtvffmdId();
        mdId.setMdcopr(copr);
        mdId.setMdcodo(codo);
        mdId.setMdano(ano);
        mdId.setMdmes((short)mes);
        
        Optional<Atvffmd> infoMesOpt = atvffmdRepository.findById(mdId);
        if (infoMesOpt.isEmpty()) {
            return 0;
        }
        Atvffmd infoMes = infoMesOpt.get();
        
        // Formatear año y mes para consultas
        String anoStr = String.valueOf(ano);
        String mesStr = String.format("%02d", mes);
        
        // Obtener arqueos del mes para el producto y documento
        List<Atvffarq> arqueosMes = atvffarqRepository.findByAqcoprAndAqcodoAndYearAndMonth(
            copr, codo, anoStr, mesStr);
        
        if (arqueosMes.isEmpty()) {
            return 0;
        }
        
        // Contar arqueos cuadrados y ejecutados
        int arqueosEjecutados = arqueosMes.size();
        int arqueosCuadrados = atvffarqRepository.countCuadradosByAqcoprAndAqcodoAndYearAndMonth(
            copr, codo, anoStr, mesStr);
        
        // Calcular calidad
        return (arqueosEjecutados > 0) ? (arqueosCuadrados * 100) / arqueosEjecutados : 0;
    }

    @Override
    public List<AtvffIap> obtenerTodos() {
        return atvffIapRepository.findAll();
    }

    @Override
    public AtvffIap obtenerPorId(Integer ano, Integer codpro, Integer coddoc) {
        AtvffIapId id = new AtvffIapId();
        id.setIpAnos(ano);
        id.setIpCodpro(codpro);
        id.setIpCoddoc(coddoc);
        
        return atvffIapRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el registro con los parámetros proporcionados"));
    }

    @Override
    public AtvffIap guardar(AtvffIap atvffIap) {
        return atvffIapRepository.save(atvffIap);
    }

    @Override
    public void eliminar(Integer ano, Integer codpro, Integer coddoc) {
        AtvffIapId id = new AtvffIapId();
        id.setIpAnos(ano);
        id.setIpCodpro(codpro);
        id.setIpCoddoc(coddoc);
        
        atvffIapRepository.deleteById(id);
    }
    
    /**
     * Método adicional para procesar un rango de fechas específico
     */
    @Override
    @Transactional
    public List<AtvffIap> generarPorRangoFechas(Integer ano, String fechaInicio, String fechaFin) {
        List<AtvffIap> resultados = new ArrayList<>();
        
        // Obtener todos los productos
        List<AtvffPdo> productos = atvffPdoRepository.findAll();
        
        // Extraer año y mes de las fechas
        int yearInicio = Integer.parseInt(fechaInicio.substring(0, 4));
        int mesInicio = Integer.parseInt(fechaInicio.substring(5, 7));
        
        int yearFin = Integer.parseInt(fechaFin.substring(0, 4));
        int mesFin = Integer.parseInt(fechaFin.substring(5, 7));
        
        for (AtvffPdo producto : productos) {
            // Procesar cada producto en el rango de fechas
            AtvffIap registroIap = procesarProductoRango(producto, ano, yearInicio, mesInicio, yearFin, mesFin);
            if (registroIap != null) {
                resultados.add(registroIap);
            }
        }
        
        return resultados;
    }
    
    private AtvffIap procesarProductoRango(AtvffPdo producto, Integer ano, int yearInicio, int mesInicio, int yearFin, int mesFin) {
        String copr = producto.getXpcopr();
        String codo = producto.getXpcodo();
        
        // Inicializar valores mensuales
        int[] valoresMensuales = new int[12];
        int totalMeses = 0;
        int sumaTotal = 0;
        
        // Procesar cada mes en el rango
        for (int year = yearInicio; year <= yearFin; year++) {
            int mesDesde = (year == yearInicio) ? mesInicio : 1;
            int mesHasta = (year == yearFin) ? mesFin : 12;
            
            for (int mes = mesDesde; mes <= mesHasta; mes++) {
                if (year == ano) {
                    int calidad = calcularCalidadMes(copr, codo, ano, mes);
                    valoresMensuales[mes-1] = calidad;
                    
                    if (calidad > 0) {
                        sumaTotal += calidad;
                        totalMeses++;
                    }
                }
            }
        }
        
        // Calcular promedio
        int promedio = (totalMeses > 0) ? sumaTotal / totalMeses : 0;
        
        // Crear o actualizar registro en ATVFFIAP
        AtvffIapId id = new AtvffIapId();
        id.setIpAnos(ano);
        id.setIpCodpro(Integer.parseInt(copr));
        id.setIpCoddoc(Integer.parseInt(codo));
        
        AtvffIap atvffIap = atvffIapRepository.findById(id)
                .orElse(new AtvffIap());
        
        if (atvffIap.getId() == null) {
            atvffIap.setId(id);
        }
        
        // Actualizar datos
        atvffIap.setIpDocument(producto.getXpdsdo());
        atvffIap.setIpProma(promedio);
        atvffIap.setIpEnero(valoresMensuales[0]);
        atvffIap.setIpFeb(valoresMensuales[1]);
        atvffIap.setIpMarzo(valoresMensuales[2]);
        atvffIap.setIpAbril(valoresMensuales[3]);
        atvffIap.setIpMayo(valoresMensuales[4]);
        atvffIap.setIpJunio(valoresMensuales[5]);
        atvffIap.setIpJulio(valoresMensuales[6]);
        atvffIap.setIpAgosto(valoresMensuales[7]);
        atvffIap.setIpSep(valoresMensuales[8]);
        atvffIap.setIpOctubre(valoresMensuales[9]);
        atvffIap.setIpNov(valoresMensuales[10]);
        atvffIap.setIpDic(valoresMensuales[11]);
        
        // Guardar en base de datos
        return atvffIapRepository.save(atvffIap);
    }
}