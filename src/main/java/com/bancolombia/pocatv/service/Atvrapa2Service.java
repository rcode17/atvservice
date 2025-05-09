package com.bancolombia.pocatv.service;


import com.bancolombia.pocatv.model.Atvffapa;

import java.util.List;

public interface Atvrapa2Service {
    /**
     * Procesa los arqueos para identificar aquellos con periodicidad anormal
     * @param ano Año a procesar
     * @param fecha Fecha opcional para filtrar (formato ISO)
     * @return Número de arqueos procesados
     */
    int procesarArqueosAnormales(Integer ano, String fecha);

    /**
     * Obtiene la lista de arqueos con periodicidad anormal
     * @param ano Año a consultar
     * @return Lista de arqueos anormales
     */
    List<Atvffapa> obtenerArqueosAnormales(Integer ano);

    /**
     * Obtiene la lista de arqueos con periodicidad anormal por mes
     * @param ano Año a consultar
     * @param mes Mes a consultar
     * @return Lista de arqueos anormales del mes
     */
    List<Atvffapa> obtenerArqueosAnormalesPorMes(Integer ano, Integer mes);
}