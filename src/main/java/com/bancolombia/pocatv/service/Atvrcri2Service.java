package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffCri;

import java.util.List;

public interface Atvrcri2Service {
	/**
	 * Genera el archivo de rangos inconsistentes para un mes y año específicos
	 * @param mes Mes para el cual se generarán los rangos
	 * @param anno Año para el cual se generarán los rangos
	 * @return Lista de registros de criterios generados
	 */
	List<AtvffCri> generarArchivoRangosInconsistentes(Integer mes, Integer anno);

	/**
	 * Consulta los registros de criterios para un mes y año específicos
	 * @param mes Mes a consultar
	 * @param anno Año a consultar
	 * @return Lista de registros de criterios
	 */
	List<AtvffCri> consultarCriterios(Integer mes, Integer anno);

	/**
	 * Consulta los registros de criterios para un mes, año y sucursal específicos
	 * @param mes Mes a consultar
	 * @param anno Año a consultar
	 * @param codsuc Código de sucursal
	 * @return Lista de registros de criterios
	 */
	List<AtvffCri> consultarCriteriosPorSucursal(Integer mes, Integer anno, Integer codsuc);
}
