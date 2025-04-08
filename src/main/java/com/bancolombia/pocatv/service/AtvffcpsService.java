package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.dto.atvffcpsfechaResponseDTO;


public interface AtvffcpsService {
	List<atvffcpsfechaResponseDTO> findDescriptionsByYearAndMonth(String cscopr, String cscodo);
}
