package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.dto.Conciliacion2DTO;
import com.bancolombia.pocatv.dto.ConciliacionDTO;
import com.bancolombia.pocatv.dto.ConciliacionRequest;

public interface AtvffcadService {
	void generarConciliacion(ConciliacionRequest request);

	
	 List<Conciliacion2DTO> consultarConciliacionesPorFecha(Integer caano, Integer cames, Integer cadia, String xuuser);
	    
	 boolean procesarConciliacion(Integer caano, Integer cames, Integer cadia, 
	                               String cacopr, String cacodo, 
	                               String xuuser);
}