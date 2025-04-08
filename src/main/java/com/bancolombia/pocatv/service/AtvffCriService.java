package com.bancolombia.pocatv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.AtvffCri;
import com.bancolombia.pocatv.repository.AtvffcriRepository;

@Service
public class AtvffCriService {

@Autowired
private AtvffcriRepository atvffCriRepository;

public List<AtvffCri> buscarPorTodosLosParametros(Integer crano, Integer crmes, Integer crcodsuc, String crcopr,
		String crcodo) {
	return atvffCriRepository.findByIdCranoAndIdCrmesAndIdCrcodsucAndIdCrcoprAndIdCrcodo(crano, crmes, crcodsuc, crcopr,
			crcodo);
}
}