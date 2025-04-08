package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.atvffcpsfechaResponseDTO;
import com.bancolombia.pocatv.model.Atvffcps;
import com.bancolombia.pocatv.repository.AtvffcpsRepository;
import com.bancolombia.pocatv.service.AtvffcpsService;

import java.util.List;
import java.util.Map;

@Service
public class AtvffcpsServiceImpl implements AtvffcpsService {

    @Autowired
    private AtvffcpsRepository repository;

	@Override
	public List<atvffcpsfechaResponseDTO> findDescriptionsByYearAndMonth(String cscopr, String cscodo) {
		
		return repository.findDescriptionsByYearAndMonth(cscopr, cscodo);
	}

}