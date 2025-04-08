package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.dto.atvffcepfechaResponseDTO;


public interface AtvffcepService {
	List<atvffcepfechaResponseDTO> findDescriptionsByYearAndMonth(Integer ano, Integer mes);

}
