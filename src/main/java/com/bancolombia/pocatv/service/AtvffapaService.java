package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.AtvffFreResponseDto;
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AtvffapaService {
    /**
     * Procesa los arqueos con periodicidad anormal para un año específico
     * @param ano Año a procesar
     * @return Número de registros procesados
     */
    int procesarArqueosAnormales(Integer ano);

    /**
     * Consulta los arqueos con periodicidad anormal para un año y mes específicos
     * @param ano Año a consultar
     * @param mes Mes a consultar
     * @return Lista de arqueos anormales
     */
    List<ArqueoAnormalDTO> consultarArqueosAnormales(Integer ano, Integer mes);
}