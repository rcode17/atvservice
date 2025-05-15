package com.bancolombia.pocatv.service;

public interface AtvrcrdService {
    /**
     * Genera archivo de arqueos descuadrados para un mes y año específicos
     * @param mes Mes (1-12)
     * @param anno Año (formato YYYY)
     * @return Número de registros procesados
     */
    int generarArqueosDescuadrados(int mes, int anno);
}