package com.bancolombia.pocatv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.atvffcepfechaResponseDTO;
import com.bancolombia.pocatv.repository.AtvffcepRepository;

import com.bancolombia.pocatv.service.AtvffcepService;

@Service
public class AtvffcepServiceImpl implements AtvffcepService{
	
    @Autowired
    private AtvffcepRepository repository;

	@Override
	public List<atvffcepfechaResponseDTO> findDescriptionsByYearAndMonth(Integer ano, Integer mes) {
		
		return repository.findDescriptionsByYearAndMonth(ano, mes);
	}

}
