package com.bancolombia.pocatv.service;

public interface AtvrcaracaService {
	
	/**
     * Procesa todos los registros de arqueo y los carga en la tabla temporal
     * @return Número de registros procesados
     */
    int procesarArqueos();
    
}
