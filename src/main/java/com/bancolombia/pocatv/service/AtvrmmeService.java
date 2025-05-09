package com.bancolombia.pocatv.service;

import java.util.List;
import java.util.Map;

/**
 * Servicio para gestionar el mantenimiento de días por mes
 * Migración del programa RPG ATVRMME
 */
public interface AtvrmmeService {

	/**
	 * Consulta los registros por producto, documento y año
	 * @param codpro Código de producto
	 * @param coddoc Código de documento
	 * @param ano Año
	 * @return Lista de registros encontrados
	 */
	List<Object> consultarPorProductoDocumentoAno(String codpro, String coddoc, Integer ano);

	/**
	 * Obtiene los días de mantenimiento por mes para un producto, documento y año específicos
	 * @param codpro Código de producto
	 * @param coddoc Código de documento
	 * @param ano Año
	 * @return Mapa con los datos de mantenimiento
	 */
	Map<String, Object> obtenerDiasPorMes(String codpro, String coddoc, Integer ano);

	/**
	 * Actualiza los días de mantenimiento por mes
	 * @param codpro Código de producto
	 * @param coddoc Código de documento
	 * @param ano Año
	 * @param diasPorMes Mapa con los días por mes
	 * @param rango1 Rango desde
	 * @param rango2 Rango hasta
	 */
	void actualizarDiasMes(String codpro, String coddoc, Integer ano,
						   Map<Integer, Integer> diasPorMes, Integer rango1, Integer rango2);

	/**
	 * Duplica la configuración para todos los productos
	 * @param codproOrigen Código de producto origen
	 * @param coddocOrigen Código de documento origen
	 * @param ano Año
	 * @param diasPorMes Mapa con los días por mes
	 * @param rango1 Rango desde
	 * @param rango2 Rango hasta
	 */
	void duplicarParaTodosProductos(String codproOrigen, String coddocOrigen, Integer ano,
									Map<Integer, Integer> diasPorMes, Integer rango1, Integer rango2);

	/**
	 * Valida si un año cumple con las reglas de negocio
	 * @param ano Año a validar
	 * @return true si el año es válido, false en caso contrario
	 */
	boolean validarAno(Integer ano);
}