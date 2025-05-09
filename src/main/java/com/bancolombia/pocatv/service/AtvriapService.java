package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ResumenEstadisticasDTO;
import com.bancolombia.pocatv.model.AtvffIap;
import java.util.List;

public interface AtvriapService {
	ResumenEstadisticasDTO obtenerEstadisticasCalidad(String user, Integer ano);
	
	/**
     * Genera el archivo de calidad de información acumulado por producto
     * @param ano Año para el cual se generará la información
     * @return Lista de registros generados
     */
    List<AtvffIap> generarArchivoCalidadInformacion(int ano);
    
    /**
     * Obtiene la información de calidad por producto y año
     * @param ano Año a consultar
     * @param codigoProducto Código del producto
     * @param codigoDocumento Código del documento
     * @return Registro de calidad de información
     */
    AtvffIap obtenerCalidadInformacion(int ano, int codigoProducto, int codigoDocumento);
    
    /**
     * Guarda un registro de calidad de información
     * @param atvffiap Registro a guardar
     * @return Registro guardado
     */
    AtvffIap guardarCalidadInformacion(AtvffIap atvffiap);
	
}
