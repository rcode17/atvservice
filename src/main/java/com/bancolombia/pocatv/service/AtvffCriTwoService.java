package com.bancolombia.pocatv.service; 


public interface AtvffCriTwoService {
    
	  /**
     * Genera el archivo de rangos inconsistentes para el mes y año especificados
     * @param mes Mes para el cual se generará el reporte
     * @param anno Año para el cual se generará el reporte
     * @param dia Día para el cual se generará el reporte (opcional)
     * @return Número de registros procesados
     */
    int generarArchivoRangosInconsistentes(int mes, int anno, int dia);
	
}