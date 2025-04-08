package com.bancolombia.pocatv.service.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.service.AtvffarqService;


@Service
public class AtvffarqServiceImpl implements AtvffarqService {

    private final AtvffarqRepository atvffarqRepository;

    public AtvffarqServiceImpl(AtvffarqRepository atvffarqRepository) {
        this.atvffarqRepository = atvffarqRepository;
    }



    @Override
    public Optional<Atvffarq> findByFields(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear) {
        return atvffarqRepository.findByAqcdsuAndAqcoprAndAqcodoAndAqfear(aqcdsu, aqcopr, aqcodo, aqfear);
    }


	@Override
	public List<Atvffarq> buscarRangosInconsistentes(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear,
			String aqcedan) {
		List<Atvffarq> results = atvffarqRepository.findByAqcdsuAndAqcoprAndAqcodoAndAqfearAndAqcedan(aqcdsu, aqcopr, aqcodo, aqfear, aqcedan);
		
		  List<Atvffarq> validResults = results.stream()
		            .filter(Atvffarq::hasDataInAqrain)
		            .toList();
		  
		  if (validResults.isEmpty()) {
		        // Si no hay resultados válidos, puedes lanzar una excepción o devolver una lista vacía
		        throw new IllegalArgumentException("No hay registros con datos válidos en los campos aqrain para los parámetros proporcionados.");
		    }
		
		
		return validResults;
	}
}