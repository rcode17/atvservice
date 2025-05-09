package com.bancolombia.pocatv.service;

import java.util.List;
import java.util.Map;

import com.bancolombia.pocatv.model.Atvfffrein;

public interface AtvfreinService {
    
    /**
     * Procesa los funcionarios reincidentes basado en la fecha proporcionada
     * @param fecha Fecha en formato YYYYMMDD o null para usar la fecha actual
     * @return Número de funcionarios reincidentes procesados
     */
    int procesarFuncionariosReincidentes(String fecha);
    
    /**
     * Obtiene la lista de funcionarios reincidentes
     * @return Lista de funcionarios reincidentes
     */
    List<Atvfffrein> obtenerFuncionariosReincidentes();
    
    /**
     * Obtiene un funcionario reincidente por su identificador
     * @param responsable Identificador del responsable
     * @return Funcionario reincidente
     */
    Atvfffrein obtenerFuncionarioReincidente(String responsable);
    
    /**
     * Elimina un funcionario reincidente
     * @param responsable Identificador del responsable
     */
    void eliminarFuncionarioReincidente(String responsable);
    
    /**
     * Obtiene estadísticas de funcionarios reincidentes
     * @return Mapa con estadísticas
     */
    Map<String, Object> obtenerEstadisticas();
}