package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ResultadoProcesamientoDTO;

/**
 * Servicio para gestionar operaciones relacionadas con los arqueos (ATVFFMEARQ)
 */
public interface AtvffmearqService {
    
    
    /**
     * Procesa todos los arqueos y los carga en la tabla temporal ATVFFTEM
     * Implementa la lógica principal del programa RPG ATVRCARAME
     * @throws IllegalArgumentException si no hay arqueos para procesar
     */
	ResultadoProcesamientoDTO procesarArqueos();
    
}