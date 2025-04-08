package com.bancolombia.pocatv.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bancolombia.pocatv.model.Atvffoas;

public interface AtvffoasService {
	Page<Atvffoas> findAll(Pageable pageable);

    // Método para obtener registros por año y mes
    Page<Atvffoas> findByAnoMes(Integer oaano, Integer oames, Pageable pageable);
    
    Page<Atvffoas> getRegistrosArchivoProducto(String xuUser, Integer oaano, Integer oames,Integer oaxnnmky, Pageable pageable);
    
    
    /**
     * Busca registros de Atvffoas filtrados por usuario, año y mes, con resultados paginados.
     *
     * @param usuario El identificador del usuario.
     * @param ano     El año a filtrar.
     * @param mes     El mes a filtrar.
     * @param pageable Datos de paginación y ordenamiento.
     * @return Página de registros Atvffoas.
     */
    Page<Atvffoas> buscarPorUsuarioAnoMes(String usuario, Integer ano, Integer mes, Pageable pageable);

}
