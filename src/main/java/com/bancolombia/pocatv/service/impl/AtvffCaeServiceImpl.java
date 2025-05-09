package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffCaeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AtvffCaeServiceImpl implements AtvffCaeService {

    @Autowired
    private AtvffFreRepository atvffFreRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffPdoRepository atvffPdoRepository;
    
    @Autowired
    private AtvffPdsRepository atvffPdsRepository;
    
    @Autowired
    private XbknamRepository xbknamRepository;
    
    @Autowired
    private XmgregRepository xmgregRepository;
    
    @Autowired
    private AtvffCaeRepository atvffCaeRepository;

    @Override
    @Transactional
    public List<AtvffCae> generarReporteCumplimiento(Integer mes, Integer ano) {
        // Limpiamos datos anteriores
        limpiarDatosAnteriores(mes, ano);
        
        // Lista para almacenar resultados
        List<AtvffCae> resultados = new ArrayList<>();
        
        // Obtenemos todas las regiones
        List<Xbknam> sucursales = xbknamRepository.findAll();
        Set<String> regiones = new HashSet<>();
        
        // Extraemos las regiones únicas
        for (Xbknam sucursal : sucursales) {
            if (sucursal.getXncdmr() != null && !sucursal.getXncdmr().trim().isEmpty()) {
                regiones.add(sucursal.getXncdmr());
            }
        }
        
        // Procesamos cada región
        for (String region : regiones) {
            AtvffCae resultado = procesarRegion(region, mes, ano);
            if (resultado != null) {
                resultados.add(resultado);
                atvffCaeRepository.save(resultado);
            }
        }
        
        return resultados;
    }

    @Override
    @Transactional
    public void limpiarDatosAnteriores(Integer mes, Integer ano) {
        atvffCaeRepository.deleteByCamesAndCaano(mes, ano);
    }
    
    private AtvffCae procesarRegion(String region, Integer mes, Integer ano) {
        // Obtenemos información de la región
        Optional<Xmgreg> infoRegionOpt = xmgregRepository.findByXmcdmr(region);
        if (infoRegionOpt.isEmpty()) {
            return null;
        }
        
        Xmgreg infoRegion = infoRegionOpt.get();
        
        // Inicializamos contadores
        int totalArqueos = 0;
        int arqueosEjecutados = 0;
        int arqueosCuadrados = 0;
        int totalProductos = 0;
        
        // Obtenemos todos los productos
        List<AtvffPdo> productos = atvffPdoRepository.findAll();
        
        for (AtvffPdo producto : productos) {
            // Obtenemos las sucursales asociadas al producto
            List<AtvffPds> sucursalesProducto = atvffPdsRepository.findByXscoprAndXscodo(
                    producto.getXpcopr(), producto.getXpcodo());
            
        	for (AtvffPds sucursalProducto : sucursalesProducto) {
                // Verificamos si la sucursal pertenece a la región actual
            Optional<Xbknam> sucursalOpt = xbknamRepository.findByXnnmky(sucursalProducto.getXscosu());
                
                if (sucursalOpt.isPresent() && region.equals(sucursalOpt.get().getXncdmr())) {
                    // Obtenemos la frecuencia de arqueos
                    Optional<AtvffFre> frecuenciaOpt = atvffFreRepository.findById_fxCoprAndId_fxCodo(
                            producto.getXpcopr(), producto.getXpcodo());
                    
                    if (frecuenciaOpt.isPresent()) {
                        AtvffFre frecuencia = frecuenciaOpt.get();
                        
                        // Calculamos el número esperado de arqueos según la frecuencia
                        int arqueosEsperados = calcularArqueosEsperados(frecuencia, mes);
                        totalArqueos += arqueosEsperados;
                        
                        // Obtenemos los arqueos realizados
                        String yearStr = String.format("%04d", ano);
                        String monthStr = String.format("%02d", mes);
                        
                        List<Atvffarq> arqueos = atvffarqRepository.findBySucursalAndCoprAndCodoAndMesAno(
                        		sucursalProducto.getXscosu(),
                                producto.getXpcopr(), 
                                producto.getXpcodo(),
                                monthStr,
                                yearStr);
                        
                        // Contamos los arqueos ejecutados y cuadrados
                        for (Atvffarq arqueo : arqueos) {
                            if (!arqueo.hasDataInAqrain()) {
                                throw new IllegalArgumentException("No hay registros con datos válidos en los campos aqrain para los parámetros proporcionados.");
                            }
                            
                            arqueosEjecutados++;
                            if ("C".equals(arqueo.getAqres())) {
                                arqueosCuadrados++;
                            }
                        }
                        
                        totalProductos++;
                    }
                }
        	}
        }
        
        // Calculamos porcentajes
        int porcentajeCumplimiento = totalArqueos > 0 ? (arqueosEjecutados * 100) / totalArqueos : 0;
        int porcentajeCalidad = arqueosEjecutados > 0 ? (arqueosCuadrados * 100) / arqueosEjecutados : 0;
        
        // Creamos el resultado
        AtvffCae resultado = new AtvffCae();
        resultado.setCaano(ano);
        resultado.setCames(mes);
        resultado.setCacumpli(porcentajeCumplimiento);
        resultado.setCacalid(porcentajeCalidad);
        resultado.setCanombre(infoRegion.getXmnmr());
        resultado.setCaregion(region);
        
        return resultado;
    }
    
    private int calcularArqueosEsperados(AtvffFre frecuencia, Integer mes) {
        String tipoFrecuencia = frecuencia.getFxFrar();
        int numeroArqueos = frecuencia.getFxDifr();
        
        // Lógica según el tipo de frecuencia (M: Mensual, T: Trimestral, S: Semestral, A: Anual)
        switch (tipoFrecuencia) {
            case "M": // Mensual
                return numeroArqueos;
            case "T": // Trimestral
                return (mes % 3 == 0) ? numeroArqueos : 0;
            case "S": // Semestral
                return (mes % 6 == 0) ? numeroArqueos : 0;
            case "A": // Anual
                return (mes == 12) ? numeroArqueos : 0;
            default:
                return 0;
        }
    }
    
    /**
     * Método para verificar si un arqueo se realizó en el último día del mes
     */
    private boolean esArqueoUltimoDiaMes(Atvffarq arqueo, Integer mes, Integer ano) {
        // Obtenemos la fecha del arqueo
        String fechaArqueo = arqueo.getAqfear();
        
        // Verificamos si la fecha corresponde al último día del mes
        LocalDate fecha = LocalDate.parse(fechaArqueo, DateTimeFormatter.ISO_DATE);
        LocalDate ultimoDiaMes = LocalDate.of(ano, mes, 1).plusMonths(1).minusDays(1);
        
        return fecha.equals(ultimoDiaMes);
    }
    
    /**
     * Método para obtener el último día del mes
     */
    private int obtenerUltimoDiaMes(int ano, int mes) {
        return LocalDate.of(ano, mes, 1).plusMonths(1).minusDays(1).getDayOfMonth();
    }
    
    /**
     * Método para verificar si un arqueo tiene datos válidos en los campos aqrain
     */
    private boolean tieneDataValida(Atvffarq arqueo) {
        return arqueo.hasDataInAqrain();
    }
    
    /**
     * Método para obtener la fecha de un arqueo en formato LocalDate
     */
    private LocalDate obtenerFechaArqueo(Atvffarq arqueo) {
        return LocalDate.parse(arqueo.getAqfear(), DateTimeFormatter.ISO_DATE);
    }
    
    /**
     * Método para verificar si un mes está dentro del período de frecuencia
     */
    private boolean esMesEnPeriodo(String tipoFrecuencia, int mes) {
        switch (tipoFrecuencia) {
            case "M": // Mensual
                return true;
            case "T": // Trimestral
                return mes % 3 == 0;
            case "S": // Semestral
                return mes == 6 || mes == 12;
            case "A": // Anual
                return mes == 12;
            default:
                return false;
        }
    }
    
    /**
     * Método para obtener el rango de fechas según la frecuencia
     */
    private Map<String, LocalDate> obtenerRangoFechas(String tipoFrecuencia, int mes, int ano) {
        Map<String, LocalDate> rango = new HashMap<>();
        LocalDate fechaInicio;
        LocalDate fechaFin = LocalDate.of(ano, mes, obtenerUltimoDiaMes(ano, mes));
        
        switch (tipoFrecuencia) {
            case "M": // Mensual
                fechaInicio = LocalDate.of(ano, mes, 1);
                break;
            case "T": // Trimestral
                int mesInicio = ((mes - 1) / 3) * 3 + 1;
                fechaInicio = LocalDate.of(ano, mesInicio, 1);
                break;
            case "S": // Semestral
                int mesInicioSemestral = mes <= 6 ? 1 : 7;
                fechaInicio = LocalDate.of(ano, mesInicioSemestral, 1);
                break;
            case "A": // Anual
                fechaInicio = LocalDate.of(ano, 1, 1);
                break;
            default:
                fechaInicio = LocalDate.of(ano, mes, 1);
        }
        
        rango.put("inicio", fechaInicio);
        rango.put("fin", fechaFin);
        
        return rango;
    }
    
    /**
     * Método para verificar si una fecha está dentro de un rango
     */
    private boolean fechaEnRango(LocalDate fecha, LocalDate inicio, LocalDate fin) {
        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }
}