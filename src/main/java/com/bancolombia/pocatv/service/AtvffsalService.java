package com.bancolombia.pocatv.service;

public interface AtvffsalService {
    
    /**
     * Actualiza el campo SAOFCO con el valor de SAOFIC en todos los registros
     * @return número de registros actualizados
     */
    int updateSaofcoWithSaofic();
}