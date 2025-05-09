package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.model.Atvffiaa;

public interface AtvffiaaService {
    /**
     * Genera estadísticas de calidad de información por área para un año específico
     * @param ano Año para el cual se generarán las estadísticas
     * @return Lista de estadísticas generadas
     */
    List<Atvffiaa> generarEstadisticas(Integer ano);
    
    /**
     * Obtiene las estadísticas para un año específico
     * @param ano Año para el cual se consultarán las estadísticas
     * @return Lista de estadísticas
     */
    List<Atvffiaa> obtenerEstadisticasPorAno(Integer ano);
}
