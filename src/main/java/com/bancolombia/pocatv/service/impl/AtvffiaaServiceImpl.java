package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffiaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AtvffiaaServiceImpl implements AtvffiaaService {

    @Autowired
    private AtvffiaaRepository atvffiaaRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffPdoRepository atvffpdoRepository;
    
    @Autowired
    private AtvffFreRepository atvfffreRepository;
    
    @Override
    @Transactional
    public List<Atvffiaa> generarEstadisticas(Integer ano) {
        // Primero limpiamos los registros existentes para el año
        atvffiaaRepository.deleteByIaano(ano);
        
        // Obtenemos la fecha actual para determinar el mes actual
        LocalDate fechaActual = LocalDate.now();
        int yearActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonthValue();
        
        // Lista para almacenar los resultados
        List<Atvffiaa> resultados = new ArrayList<>();
        
        // Obtenemos todas las regiones
        List<String> regiones = obtenerRegiones();
        
        // Para cada región, calculamos las estadísticas mensuales
        for (String region : regiones) {
            Atvffiaa estadistica = new Atvffiaa();
            estadistica.setIaregion(region);
            estadistica.setIanombre(obtenerNombreRegion(region));
            estadistica.setIaano(ano);
            
            // Inicializamos los valores mensuales
            estadistica.setIaenero(0);
            estadistica.setIafeb(0);
            estadistica.setIamartzo(0);
            estadistica.setIaabril(0);
            estadistica.setIamay(0);
            estadistica.setIajunio(0);
            estadistica.setIajulio(0);
            estadistica.setIaagosto(0);
            estadistica.setIasep(0);
            estadistica.setIaoctubre(0);
            estadistica.setIanov(0);
            estadistica.setIadic(0);
            
            int totalMeses = 0;
            int sumaPorcentajes = 0;
            
            // Calculamos estadísticas para cada mes
            int mesLimite = (yearActual == ano) ? mesActual : 12;
            
            for (int mes = 1; mes <= mesLimite; mes++) {
                int porcentaje = calcularPorcentajeCalidad(region, ano, mes);
                
                // Asignamos el porcentaje al mes correspondiente
                switch (mes) {
                    case 1: estadistica.setIaenero(porcentaje); break;
                    case 2: estadistica.setIafeb(porcentaje); break;
                    case 3: estadistica.setIamartzo(porcentaje); break;
                    case 4: estadistica.setIaabril(porcentaje); break;
                    case 5: estadistica.setIamay(porcentaje); break;
                    case 6: estadistica.setIajunio(porcentaje); break;
                    case 7: estadistica.setIajulio(porcentaje); break;
                    case 8: estadistica.setIaagosto(porcentaje); break;
                    case 9: estadistica.setIasep(porcentaje); break;
                    case 10: estadistica.setIaoctubre(porcentaje); break;
                    case 11: estadistica.setIanov(porcentaje); break;
                    case 12: estadistica.setIadic(porcentaje); break;
                }
                
                if (porcentaje > 0) {
                    totalMeses++;
                    sumaPorcentajes += porcentaje;
                }
            }
            
            // Calculamos el promedio
            int promedio = totalMeses > 0 ? sumaPorcentajes / totalMeses : 0;
            estadistica.setIaproma(promedio);
            
            // Guardamos la estadística
            resultados.add(atvffiaaRepository.save(estadistica));
        }
        
        return resultados;
    }
    
    @Override
    public List<Atvffiaa> obtenerEstadisticasPorAno(Integer ano) {
        return atvffiaaRepository.findByIaano(ano);
    }
    
    /**
     * Método para obtener todas las regiones disponibles
     */
    private List<String> obtenerRegiones() {
        // Implementación simplificada - en un caso real se obtendría de la base de datos
        List<String> regiones = new ArrayList<>();
        
        // Ejemplo de regiones
        regiones.add("REG001");
        regiones.add("REG002");
        regiones.add("REG003");
        
        return regiones;
    }
    
    /**
     * Método para obtener el nombre de una región
     */
    private String obtenerNombreRegion(String codigoRegion) {
        // Implementación simplificada - en un caso real se obtendría de la base de datos
        
        // Ejemplo de nombres de regiones
        Map<String, String> nombresRegiones = new HashMap<>();
        nombresRegiones.put("REG001", "REGIÓN NORTE");
        nombresRegiones.put("REG002", "REGIÓN CENTRO");
        nombresRegiones.put("REG003", "REGIÓN SUR");
        
        return nombresRegiones.getOrDefault(codigoRegion, "REGIÓN DESCONOCIDA");
    }
    
    /**
     * Método para calcular el porcentaje de calidad para una región y mes específicos
     */
    private int calcularPorcentajeCalidad(String region, int ano, int mes) {
        // Implementación de la lógica de cálculo basada en el código RPG
        
        // 1. Obtener los productos asociados a la región
        List<AtvffPdo> productos = obtenerProductosPorRegion(region);
        
        if (productos.isEmpty()) {
            return 0;
        }
        
        int totalArqueos = 0;
        int arqueosCorrectos = 0;
        
        // 2. Para cada producto, calcular los arqueos
        for (AtvffPdo producto : productos) {
            // Obtener la frecuencia de arqueo
            AtvffFre frecuencia = obtenerFrecuencia(producto.getXpcopr(), producto.getXpcodo());
            
            if (frecuencia == null) {
                continue;
            }
            
            // Obtener las sucursales de la región
            List<Integer> sucursales = obtenerSucursalesPorRegion(region);
            
            for (Integer sucursal : sucursales) {
                // Obtener los arqueos para la sucursal y producto
                List<Atvffarq> arqueos = atvffarqRepository.findBySucursalAndCoprAndCodo(
                    sucursal, producto.getXpcopr(), producto.getXpcodo()
                );
                
                // Filtrar arqueos por año y mes
                List<Atvffarq> arqueosFiltrados = filtrarArqueosPorFecha(arqueos, ano, mes);
                
                // Obtener información del día de arqueo desde Atvffmd
                Optional<Atvffmd> mdOptional = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(
                    producto.getXpcopr(), 
                    producto.getXpcodo(), 
                    ano, 
                    (short) mes
                );
                
                // Si existe información de día de arqueo, aplicar filtros adicionales
                if (mdOptional.isPresent()) {
                    Atvffmd md = mdOptional.get();
                    arqueosFiltrados = filtrarArqueosPorDia(arqueosFiltrados, md);
                }
                
                totalArqueos += arqueosFiltrados.size();
                
                // Contar arqueos correctos (donde aqres = 'C')
                for (Atvffarq arqueo : arqueosFiltrados) {
                    if ("C".equals(arqueo.getAqres())) {
                        arqueosCorrectos++;
                    }
                }
            }
        }
        
        // 3. Calcular el porcentaje
        return totalArqueos > 0 ? (arqueosCorrectos * 100) / totalArqueos : 0;
    }
    
    /**
     * Método para filtrar arqueos por día según la configuración en Atvffmd
     */
    private List<Atvffarq> filtrarArqueosPorDia(List<Atvffarq> arqueos, Atvffmd md) {
        List<Atvffarq> filtrados = new ArrayList<>();
        
        // Obtener el día de arqueo y los rangos
        short diaArqueo = md.getMddia();
        short rango1 = md.getMdrang1();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Atvffarq arqueo : arqueos) {
            try {
                LocalDate fecha = LocalDate.parse(arqueo.getAqfear(), formatter);
                int dia = fecha.getDayOfMonth();
                
                // Verificar si el día está dentro del rango de arqueo
                if (dia == diaArqueo || (dia >= (diaArqueo - rango1) && dia < diaArqueo)) {
                    filtrados.add(arqueo);
                }
            } catch (Exception e) {
                // Manejar error de formato de fecha
            }
        }
        
        return filtrados;
    }
    
    /**
     * Método para obtener los productos asociados a una región
     */
    private List<AtvffPdo> obtenerProductosPorRegion(String region) {
        // Implementación simplificada - en un caso real se obtendría de la base de datos
        return atvffpdoRepository.findAll();
    }
    
    /**
     * Método para obtener la frecuencia de arqueo de un producto
     */
    private AtvffFre obtenerFrecuencia(String copr, String codo) {
        AtvffFreId id = new AtvffFreId();
        id.setFxCopr(copr);
        id.setFxCodo(codo);
        
        return atvfffreRepository.findById(id).orElse(null);
    }
    
    /**
     * Método para obtener las sucursales de una región
     */
    private List<Integer> obtenerSucursalesPorRegion(String region) {
        // Implementación simplificada - en un caso real se obtendría de la base de datos
        List<Integer> sucursales = new ArrayList<>();
        
        // Ejemplo de sucursales
        sucursales.add(1001);
        sucursales.add(1002);
        sucursales.add(1003);
        
        return sucursales;
    }
    
    /**
     * Método para filtrar arqueos por fecha
     */
    private List<Atvffarq> filtrarArqueosPorFecha(List<Atvffarq> arqueos, int ano, int mes) {
        List<Atvffarq> filtrados = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Atvffarq arqueo : arqueos) {
            try {
                LocalDate fecha = LocalDate.parse(arqueo.getAqfear(), formatter);
                
                if (fecha.getYear() == ano && fecha.getMonthValue() == mes) {
                    filtrados.add(arqueo);
                }
            } catch (Exception e) {
                // Manejar error de formato de fecha
            }
        }
        
        return filtrados;
    }
}