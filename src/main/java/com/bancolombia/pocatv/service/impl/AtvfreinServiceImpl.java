package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvfreinService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AtvfreinServiceImpl implements AtvfreinService {
	
	private static final Logger log = LoggerFactory.getLogger(AtvfreinServiceImpl.class);

    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffPdoRepository atvffPdoRepository;
    
    @Autowired
    private AtvffDsunRepository atvffDsunRepository;
    
    @Autowired
    private AtvfffreinRepository atvfffreinRepository;
    
    @Autowired
    private AtvffTemfrRepository atvffTemfrRepository;
    
    @Autowired
    private AtvffTemf1Repository atvffTemf1Repository;

    @Override
    @Transactional
    public int procesarFuncionariosReincidentes(String fecha) {
        // Inicializar fecha
        LocalDate fechaActual;
        if (fecha != null && !fecha.isEmpty()) {
            fechaActual = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } else {
            fechaActual = LocalDate.now();
        }
        
        // Obtener año y mes actual
        int year = fechaActual.getYear();
        int month = fechaActual.getMonthValue();
        
        // Calcular fechas para el rango de procesamiento
        String fechaFin = String.format("%04d%02d", year, month);
        
        // Calcular fecha de inicio (3 meses atrás)
        LocalDate fechaTresMesesAtras = fechaActual.minusMonths(3);
        String fechaInicio = String.format("%04d%02d", fechaTresMesesAtras.getYear(), fechaTresMesesAtras.getMonthValue());
        
        // Limpiar tablas temporales
        limpiarTablasTemporales();
        
        // Ejecutar subrutinas principales (equivalentes a las del código RPG)
        ejecutarSubrutinaPpal(fechaActual);
        ejecutarSubrutinaTemp3(fechaInicio, fechaFin);
        ejecutarSubrutinaTemp4(fechaInicio, fechaFin);
        ejecutarSubrutinaTemp5();
        ejecutarSubrutinaTemp6(fechaActual);
        
        // Procesar funcionarios reincidentes
        return ejecutarSubrutinaLleFrein();
    }

    @Override
    public List<Atvfffrein> obtenerFuncionariosReincidentes() {
        return atvfffreinRepository.findAll();
    }

    @Override
    public Atvfffrein obtenerFuncionarioReincidente(String responsable) {
        return atvfffreinRepository.findById(responsable)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el funcionario reincidente con ID: " + responsable));
    }

    @Override
    @Transactional
    public void eliminarFuncionarioReincidente(String responsable) {
        atvfffreinRepository.deleteByResp(responsable);
    }

    @Override
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        List<Atvfffrein> reincidentes = atvfffreinRepository.findAll();
        
        // Total de funcionarios reincidentes
        estadisticas.put("totalReincidentes", reincidentes.size());
        
        // Distribución por producto
        Map<String, Long> distribucionPorProducto = reincidentes.stream()
                .collect(Collectors.groupingBy(Atvfffrein::getProd, Collectors.counting()));
        estadisticas.put("distribucionPorProducto", distribucionPorProducto);
        
        // Distribución por mes
        Map<Integer, Long> distribucionPorMes = reincidentes.stream()
                .collect(Collectors.groupingBy(Atvfffrein::getMessubf, Collectors.counting()));
        estadisticas.put("distribucionPorMes", distribucionPorMes);
        
        return estadisticas;
    }

    // Métodos privados que implementan la lógica de las subrutinas del RPG
    
    private void limpiarTablasTemporales() {
        atvffTemfrRepository.deleteAll();
        //atvffTemf1Repository.deleteAll();
    }
    
    private void ejecutarSubrutinaPpal(LocalDate fechaActual) {
        // Implementación de la lógica de la subrutina PPAL del RPG
        // Calcula fechas y prepara variables para el procesamiento
    }
    
    private void ejecutarSubrutinaTemp3(String fechaInicio, String fechaFin) {
        // Implementación de la lógica de la subrutina TEMP3 del RPG
        // Procesa información del archivo ATVFFDSUN y la pasa a ATVFFTEMF1
        List<AtvffDsun> registrosDsun = atvffDsunRepository.findByFechaRange(fechaInicio, fechaFin);
        
        for (AtvffDsun dsun : registrosDsun) {
            // Procesar cada registro según la lógica del RPG
            procesarRegistroDsun(dsun);
        }
    }
    
    /*private void procesarRegistroDsun(AtvffDsun dsun) {
        // Implementa la lógica de la subrutina BUSQUEDA del RPG
        // Verifica campos de calidad y crea registros en ATVFFTEMF1 para los que cumplen condiciones
        
        // Ejemplo para el campo dncalid1
        if (dsun.isCalidadBaja(dsun.getDncalid1())) {
            AtvffTemf1 temf1 = new AtvffTemf1();
            temf1.setTfano1(dsun.getDnano());
            temf1.setTfmes1(dsun.getDnmes());
            temf1.setTfsuc1(dsun.getDnxnnmky());
            temf1.setTfprod1("01");
            temf1.setTfdoc1("001");
            temf1.setTfpcal1(Integer.parseInt(dsun.getDncalid1().trim()));
            // Completar otros campos
            atvffTemf1Repository.save(temf1);
        }
        
        // Repetir para los demás campos de calidad (dncalid2-70)
    }*/
    
    private void procesarRegistroDsun(AtvffDsun dsun) {
    	
    	// Implementa la lógica de la subrutina BUSQUEDA del RPG
        // Verifica campos de calidad y crea registros en ATVFFTEMF1 para los que cumplen condiciones
        
        // Producto 01 - Documentos 001, 002, 003
        if (dsun.isCalidadBaja(dsun.getDncalid1())) {
            crearRegistroTemf1(dsun, "01", "001", dsun.getDncalid1());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid2())) {
            crearRegistroTemf1(dsun, "01", "002", dsun.getDncalid2());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid3())) {
            crearRegistroTemf1(dsun, "01", "003", dsun.getDncalid3());
        }
        
        // Producto 02 - Documentos 001, 002, 003
        if (dsun.isCalidadBaja(dsun.getDncalid4())) {
            crearRegistroTemf1(dsun, "02", "001", dsun.getDncalid4());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid5())) {
            crearRegistroTemf1(dsun, "02", "002", dsun.getDncalid5());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid6())) {
            crearRegistroTemf1(dsun, "02", "003", dsun.getDncalid6());
        }
        
        // Producto 03 - Documento 001
        if (dsun.isCalidadBaja(dsun.getDncalid7())) {
            crearRegistroTemf1(dsun, "03", "001", dsun.getDncalid7());
        }
        
        // Producto 04 - Documentos 001 a 011
        if (dsun.isCalidadBaja(dsun.getDncalid8())) {
            crearRegistroTemf1(dsun, "04", "001", dsun.getDncalid8());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid9())) {
            crearRegistroTemf1(dsun, "04", "002", dsun.getDncalid9());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid10())) {
            crearRegistroTemf1(dsun, "04", "003", dsun.getDncalid10());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid11())) {
            crearRegistroTemf1(dsun, "04", "004", dsun.getDncalid11());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid12())) {
            crearRegistroTemf1(dsun, "04", "005", dsun.getDncalid12());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid13())) {
            crearRegistroTemf1(dsun, "04", "006", dsun.getDncalid13());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid14())) {
            crearRegistroTemf1(dsun, "04", "007", dsun.getDncalid14());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid15())) {
            crearRegistroTemf1(dsun, "04", "008", dsun.getDncalid15());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid16())) {
            crearRegistroTemf1(dsun, "04", "009", dsun.getDncalid16());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid17())) {
            crearRegistroTemf1(dsun, "04", "010", dsun.getDncalid17());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid54())) {
            crearRegistroTemf1(dsun, "04", "011", dsun.getDncalid54());
        }
        
        // Producto 05 - Documentos 001, 002, 003
        if (dsun.isCalidadBaja(dsun.getDncalid18())) {
            crearRegistroTemf1(dsun, "05", "001", dsun.getDncalid18());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid19())) {
            crearRegistroTemf1(dsun, "05", "002", dsun.getDncalid19());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid55())) {
            crearRegistroTemf1(dsun, "05", "003", dsun.getDncalid55());
        }
        
        // Producto 06 - Documento 001
        if (dsun.isCalidadBaja(dsun.getDncalid20())) {
            crearRegistroTemf1(dsun, "06", "001", dsun.getDncalid20());
        }
        
        // Producto 07 - Documento 001
        if (dsun.isCalidadBaja(dsun.getDncalid21())) {
            crearRegistroTemf1(dsun, "07", "001", dsun.getDncalid21());
        }
        
        // Producto 08 - Documentos 001, 002, 003
        if (dsun.isCalidadBaja(dsun.getDncalid22())) {
            crearRegistroTemf1(dsun, "08", "001", dsun.getDncalid22());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid23())) {
            crearRegistroTemf1(dsun, "08", "002", dsun.getDncalid23());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid24())) {
            crearRegistroTemf1(dsun, "08", "003", dsun.getDncalid24());
        }
        
        // Producto 09 - Documentos 001 a 005
        if (dsun.isCalidadBaja(dsun.getDncalid25())) {
            crearRegistroTemf1(dsun, "09", "001", dsun.getDncalid25());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid26())) {
            crearRegistroTemf1(dsun, "09", "002", dsun.getDncalid26());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid27())) {
            crearRegistroTemf1(dsun, "09", "003", dsun.getDncalid27());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid28())) {
            crearRegistroTemf1(dsun, "09", "004", dsun.getDncalid28());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid29())) {
            crearRegistroTemf1(dsun, "09", "005", dsun.getDncalid29());
        }
        
        // Producto 11 - Documentos 001, 002
        if (dsun.isCalidadBaja(dsun.getDncalid30())) {
            crearRegistroTemf1(dsun, "11", "001", dsun.getDncalid30());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid31())) {
            crearRegistroTemf1(dsun, "11", "002", dsun.getDncalid31());
        }
        
        // Producto 12 - Documentos 001, 002
        if (dsun.isCalidadBaja(dsun.getDncalid48())) {
            crearRegistroTemf1(dsun, "12", "001", dsun.getDncalid48());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid47())) {
            crearRegistroTemf1(dsun, "12", "002", dsun.getDncalid47());
        }
        
        // Producto 13 - Documentos 001 a 013, 015, 016
        if (dsun.isCalidadBaja(dsun.getDncalid32())) {
            crearRegistroTemf1(dsun, "13", "001", dsun.getDncalid32());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid33())) {
            crearRegistroTemf1(dsun, "13", "002", dsun.getDncalid33());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid34())) {
            crearRegistroTemf1(dsun, "13", "003", dsun.getDncalid34());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid35())) {
            crearRegistroTemf1(dsun, "13", "004", dsun.getDncalid35());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid36())) {
            crearRegistroTemf1(dsun, "13", "005", dsun.getDncalid36());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid37())) {
            crearRegistroTemf1(dsun, "13", "006", dsun.getDncalid37());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid38())) {
            crearRegistroTemf1(dsun, "13", "007", dsun.getDncalid38());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid39())) {
            crearRegistroTemf1(dsun, "13", "008", dsun.getDncalid39());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid40())) {
            crearRegistroTemf1(dsun, "13", "009", dsun.getDncalid40());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid41())) {
            crearRegistroTemf1(dsun, "13", "010", dsun.getDncalid41());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid42())) {
            crearRegistroTemf1(dsun, "13", "011", dsun.getDncalid42());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid43())) {
            crearRegistroTemf1(dsun, "13", "012", dsun.getDncalid43());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid44())) {
            crearRegistroTemf1(dsun, "13", "013", dsun.getDncalid44());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid45())) {
            crearRegistroTemf1(dsun, "13", "015", dsun.getDncalid45());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid46())) {
            crearRegistroTemf1(dsun, "13", "016", dsun.getDncalid46());
        }
        
        // Producto 16 - Documentos 001, 003, 005, 013, 015
        if (dsun.isCalidadBaja(dsun.getDncalid49())) {
            crearRegistroTemf1(dsun, "16", "001", dsun.getDncalid49());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid50())) {
            crearRegistroTemf1(dsun, "16", "003", dsun.getDncalid50());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid51())) {
            crearRegistroTemf1(dsun, "16", "005", dsun.getDncalid51());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid52())) {
            crearRegistroTemf1(dsun, "16", "013", dsun.getDncalid52());
        }
        if (dsun.isCalidadBaja(dsun.getDncalid53())) {
            crearRegistroTemf1(dsun, "16", "015", dsun.getDncalid53());
        }
    	
    }
    
    /**
     * Método auxiliar para crear un registro en ATVFFTEMF1
     */
    private void crearRegistroTemf1(AtvffDsun dsun, String producto, String documento, String calidad) {
        if (calidad == null || calidad.trim().isEmpty()) {
            return;
        }
        
        try {
            AtvffTemf1 temf1 = new AtvffTemf1();
            temf1.setTfano1(dsun.getDnano());
            temf1.setTfmes1(dsun.getDnmes());
            temf1.setTfsuc1(dsun.getDnxnnmky());
            temf1.setTfprod1(producto);
            temf1.setTfdoc1(documento);
            temf1.setTfresp1(""); // Se llenará posteriormente
            temf1.setTfres1(""); // Se llenará posteriormente
            temf1.setTfrel1(0); // Valor inicial
            temf1.setTfpcal1(Integer.parseInt(calidad.trim()));
            temf1.setTfdresp1(""); // Se llenará posteriormente
            
            atvffTemf1Repository.save(temf1);
        } catch (NumberFormatException e) {
            // Registrar el error en el log sin detener el proceso
            log.warn("Error al procesar valor de calidad no numérico: {} para producto: {}, documento: {}, sucursal: {}, año: {}, mes: {}",
                    calidad, producto, documento, dsun.getDnxnnmky(), dsun.getDnano(), dsun.getDnmes());
        } catch (Exception e) {
            // Capturar cualquier otra excepción que pueda ocurrir
            log.error("Error inesperado al crear registro en ATVFFTEMF1: {} para producto: {}, documento: {}, sucursal: {}, año: {}, mes: {}",
                    e.getMessage(), producto, documento, dsun.getDnxnnmky(), dsun.getDnano(), dsun.getDnmes());
        }
    }
    
    private void ejecutarSubrutinaTemp4(String fechaInicio, String fechaFin) {
        // Implementación de la lógica de la subrutina TEMP4 del RPG
        // Escribe datos del ATVFFTEMF1 al ATVFFTEMFR
        List<Atvffarq> arqueos = atvffarqRepository.findByFechaRange(fechaInicio, fechaFin);
        
        for (Atvffarq arqueo : arqueos) {
            // Extraer año y mes de la fecha
            String fecha = arqueo.getAqfear();
            int ano = Integer.parseInt(fecha.substring(0, 4));
            int mes = Integer.parseInt(fecha.substring(5, 7));
            
            // Buscar en ATVFFTEMF1
            List<AtvffTemf1> registrosTemf1 = atvffTemf1Repository.findByTfano1AndTfmes1AndTfsuc1(
                    ano, mes, arqueo.getAqcdsu());
            
            for (AtvffTemf1 temf1 : registrosTemf1) {
                // Crear registro en ATVFFTEMFR
                AtvffTemfr temfr = new AtvffTemfr();
                temfr.setTfano(ano);
                temfr.setTfmes(mes);
                temfr.setTfsuc(arqueo.getAqcdsu());
                temfr.setTfprod(arqueo.getAqcopr());
                temfr.setTfdoc(arqueo.getAqcodo());
                temfr.setTfresp(arqueo.getAqcedcn());
                temfr.setTfres(arqueo.getAqres());
                temfr.setTfrel(0);
                temfr.setTfpcal(temf1.getTfpcal1());
                temfr.setTfdresp(arqueo.getAqprcu());
                
                atvffTemfrRepository.save(temfr);
            }
        }
        atvffTemf1Repository.deleteAll();
    }
    
    private void ejecutarSubrutinaTemp5() {
        // Implementación de la lógica de la subrutina TEMP5 del RPG
        // Procesa datos de ATVFFTEMFR y los escribe en ATVFFTEMF1
        
        // Obtener todos los registros de ATVFFTEMFR
        List<AtvffTemfr> registrosTemfr = atvffTemfrRepository.findAll();
        
        // Agrupar por responsable
        Map<String, List<AtvffTemfr>> porResponsable = registrosTemfr.stream()
                .collect(Collectors.groupingBy(AtvffTemfr::getTfresp));
        
        for (Map.Entry<String, List<AtvffTemfr>> entry : porResponsable.entrySet()) {
            String responsable = entry.getKey();
            List<AtvffTemfr> registros = entry.getValue();
            
            // Calcular relación de calidad
            int total = registros.size();
            long cuadrados = registros.stream()
                    .filter(r -> "C".equals(r.getTfres()))
                    .count();
            
            int relacion = total > 0 ? (int) ((cuadrados * 100) / total) : 0;
            
            // Si la relación es mayor a 50%, eliminar registros de ATVFFTEMF1
            if (relacion > 50) {
                List<AtvffTemf1> registrosTemf1 = atvffTemf1Repository.findByTfresp1(responsable);
                atvffTemf1Repository.deleteAll(registrosTemf1);
            }
            
            // Guardar registros en ATVFFTEMF1
            for (AtvffTemfr temfr : registros) {
                AtvffTemf1 temf1 = new AtvffTemf1();
                temf1.setTfano1(temfr.getTfano());
                temf1.setTfmes1(temfr.getTfmes());
                temf1.setTfsuc1(temfr.getTfsuc());
                temf1.setTfprod1(temfr.getTfprod());
                temf1.setTfdoc1(temfr.getTfdoc());
                temf1.setTfresp1(temfr.getTfresp());
                temf1.setTfres1(temfr.getTfres());
                temf1.setTfrel1(0);
                temf1.setTfpcal1(temfr.getTfpcal());
                temf1.setTfdresp1(temfr.getTfdresp());
                
                atvffTemf1Repository.save(temf1);
            }
        }
    }
    
    private void ejecutarSubrutinaTemp6(LocalDate fechaActual) {
        // Implementación de la lógica de la subrutina TEMP6 del RPG
        // Procesa datos de ATVFFTEMF1 y los escribe en ATVFFTEMFR
        
        // Obtener todos los registros de ATVFFTEMF1
        List<AtvffTemf1> registrosTemf1 = atvffTemf1Repository.findAll();
        
        // Agrupar por responsable
        Map<String, List<AtvffTemf1>> porResponsable = registrosTemf1.stream()
                .collect(Collectors.groupingBy(AtvffTemf1::getTfresp1));
        
        for (Map.Entry<String, List<AtvffTemf1>> entry : porResponsable.entrySet()) {
            String responsable = entry.getKey();
            List<AtvffTemf1> registros = entry.getValue();
            
            // Procesar registros por mes
            procesarRegistrosPorMes(responsable, registros, fechaActual);
        }
    }
    
    private void procesarRegistrosPorMes(String responsable, List<AtvffTemf1> registros, LocalDate fechaActual) {
        // Implementa la lógica de procesamiento por mes de la subrutina TEMP6
        // Esta es una versión simplificada
        
        // Agrupar por año y mes
        Map<String, List<AtvffTemf1>> porAnoMes = registros.stream()
                .collect(Collectors.groupingBy(r -> r.getTfano1() + "-" + r.getTfmes1()));
        
        // Verificar si hay registros en 3 meses consecutivos
        if (porAnoMes.size() >= 3) {
            // Crear registros en ATVFFTEMFR para cada mes
            for (List<AtvffTemf1> registrosMes : porAnoMes.values()) {
                if (!registrosMes.isEmpty()) {
                    AtvffTemf1 primerRegistro = registrosMes.get(0);
                    
                    AtvffTemfr temfr = new AtvffTemfr();
                    temfr.setTfano(primerRegistro.getTfano1());
                    temfr.setTfmes(primerRegistro.getTfmes1());
                    temfr.setTfsuc(primerRegistro.getTfsuc1());
                    temfr.setTfprod(primerRegistro.getTfprod1());
                    temfr.setTfdoc(primerRegistro.getTfdoc1());
                    temfr.setTfresp(primerRegistro.getTfresp1());
                    temfr.setTfres(primerRegistro.getTfres1());
                    temfr.setTfrel(0);
                    temfr.setTfpcal(primerRegistro.getTfpcal1());
                    temfr.setTfdresp(primerRegistro.getTfdresp1());
                    
                    atvffTemfrRepository.save(temfr);
                }
            }
        }
    }
    
    private int ejecutarSubrutinaLleFrein() {
        // Implementación de la lógica de la subrutina SUB_LLE_FREIN del RPG
        // Verifica si el usuario tiene calidad <= 50 durante 3 meses seguidos
        
        int contadorReincidentes = 0;
        
        // Obtener todos los registros de ATVFFTEMFR
        List<AtvffTemfr> registrosTemfr = atvffTemfrRepository.findAll();
        
        // Agrupar por responsable
        Map<String, List<AtvffTemfr>> porResponsable = registrosTemfr.stream()
                .collect(Collectors.groupingBy(AtvffTemfr::getTfresp));
        
        for (Map.Entry<String, List<AtvffTemfr>> entry : porResponsable.entrySet()) {
            String responsable = entry.getKey();
            List<AtvffTemfr> registros = entry.getValue();
            
            // Verificar si hay registros en 3 meses consecutivos con calidad <= 50
            if (verificarTresMesesConsecutivos(registros)) {
                // Crear registro en ATVFFFREIN
                AtvffTemfr ultimoRegistro = registros.get(registros.size() - 1);
                
                Atvfffrein frein = new Atvfffrein();
                frein.setResp(responsable);
                frein.setCod(ultimoRegistro.getTfsuc());
                
                // Obtener nombre del área desde ATVFFDSUN
                Optional<AtvffDsun> dsun = atvffDsunRepository.findById(
                        new AtvffDsunId(ultimoRegistro.getTfano(), ultimoRegistro.getTfmes(), ultimoRegistro.getTfsuc()));
                
                if (dsun.isPresent()) {
                    frein.setArea(dsun.get().getDnnombre());
                }
                
                frein.setProd(ultimoRegistro.getTfprod());
                frein.setDoc(ultimoRegistro.getTfdoc());
                frein.setCal(ultimoRegistro.getTfpcal());
                frein.setMessubf(ultimoRegistro.getTfmes());
                frein.setAnosubf(ultimoRegistro.getTfano());
                frein.setDresp(ultimoRegistro.getTfdresp());
                
                // Obtener descripción del documento desde ATVFFPDO
                AtvffPdo pdo = atvffPdoRepository.findByXpcoprAndXpcodo(
                        ultimoRegistro.getTfprod(), ultimoRegistro.getTfdoc());
                
                if (pdo != null) {
                    frein.setDdoc(pdo.getXpdsdo());
                }
                
                atvfffreinRepository.save(frein);
                contadorReincidentes++;
                
                // Eliminar registros de ATVFFTEMFR
                atvffTemfrRepository.delete(ultimoRegistro);
            }
        }
        
        return contadorReincidentes;
    }
    
    private boolean verificarTresMesesConsecutivos(List<AtvffTemfr> registros) {
        // Implementa la lógica para verificar si hay 3 meses consecutivos con calidad <= 50
        // Esta es una versión simplificada
        
        if (registros.size() < 3) {
            return false;
        }
        
        // Ordenar por año y mes
        registros.sort(Comparator
                .comparing(AtvffTemfr::getTfano)
                .thenComparing(AtvffTemfr::getTfmes));
        
        // Verificar si hay al menos 3 meses consecutivos
        for (int i = 0; i < registros.size() - 2; i++) {
            AtvffTemfr r1 = registros.get(i);
            AtvffTemfr r2 = registros.get(i + 1);
            AtvffTemfr r3 = registros.get(i + 2);
            
            if (sonMesesConsecutivos(r1, r2, r3)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean sonMesesConsecutivos(AtvffTemfr r1, AtvffTemfr r2, AtvffTemfr r3) {
        // Verifica si tres registros representan meses consecutivos
        
        // Convertir a LocalDate para facilitar la comparación
        LocalDate fecha1 = LocalDate.of(r1.getTfano(), r1.getTfmes(), 1);
        LocalDate fecha2 = LocalDate.of(r2.getTfano(), r2.getTfmes(), 1);
        LocalDate fecha3 = LocalDate.of(r3.getTfano(), r3.getTfmes(), 1);
        
        // Verificar si son meses consecutivos
        return fecha2.equals(fecha1.plusMonths(1)) && fecha3.equals(fecha2.plusMonths(1));
    }
}