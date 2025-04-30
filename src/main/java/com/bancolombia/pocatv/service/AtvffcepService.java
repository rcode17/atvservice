package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.dto.ConsultaEspecificaDTO;
import com.bancolombia.pocatv.dto.atvffcepfechaResponseDTO;


public interface AtvffcepService {
	List<atvffcepfechaResponseDTO> findDescriptionsByYearAndMonth(Integer ano, Integer mes);
	
	 /**
     * Realiza la consulta específica por producto para el mes y año especificados
     * 
     * @param mes Mes para la consulta
     * @param ano Año para la consulta
     * @return Lista de resultados de la consulta
     */
    List<ConsultaEspecificaDTO> consultarEspecificaPorProducto(Integer mes, Integer ano);
    
    /**
     * Ejecuta el proceso completo de consulta específica por producto
     * similar al programa RPG ATVRFCEP
     * 
     * @param mes Mes para procesar
     * @param ano Año para procesar
     * @return true si el proceso se completó correctamente
     */
    boolean procesarConsultaEspecifica(Integer mes, Integer ano);

}
