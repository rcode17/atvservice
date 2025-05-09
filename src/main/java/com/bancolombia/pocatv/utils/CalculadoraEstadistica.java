package com.bancolombia.pocatv.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffmdRepository;

@Component
public class CalculadoraEstadistica {
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    /**
     * Calcula el porcentaje de calidad para un mes específico
     * @param sucursal Código de sucursal
     * @param codigoProducto Código de producto
     * @param codigoDocumento Código de documento
     * @param ano Año
     * @param mes Mes (1-12)
     * @param frecuencia Información de frecuencia de arqueo
     * @return Porcentaje de calidad (0-100)
     */
    public int calcularCalidadMes(Integer sucursal, String codigoProducto, String codigoDocumento, 
                                 Integer ano, Short mes, AtvffFre frecuencia) {
        
        // Verificar si hay datos para este mes
        Optional<Atvffmd> md = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(
            codigoProducto, codigoDocumento, ano, mes);
        
        if (md.isEmpty()) {
            return 0; // No hay configuración para este mes
        }
        
        // Obtener cantidad de arqueos cuadrados
        Integer arqueosCuadrados = atvffarqRepository.countCuadradosBySucursalProductoDocumentoYearAndMonth(
            sucursal, codigoProducto, codigoDocumento, ano, mes.intValue());
        
        // Si no hay días de frecuencia configurados, no se puede calcular
        if (frecuencia.getFxDifr() == null || frecuencia.getFxDifr() == 0) {
            return 0;
        }
        
        // Calcular porcentaje de calidad
        int porcentaje = (arqueosCuadrados * 100) / frecuencia.getFxDifr();
        
        return Math.min(porcentaje, 100); // Limitar a 100%
    }
}