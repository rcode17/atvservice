package com.bancolombia.pocatv.service;

public interface AtvrcarajdService {
	   /**
     * Procesa los datos de arqueo desde ATVFFCHARQ a ATVFFTEM
     * @return Número de registros procesados
     */
    int procesarAtvrcarajd();
    
    void procesarCargaJDE();
}
