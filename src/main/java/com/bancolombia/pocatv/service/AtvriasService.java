package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.Xbknam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtvriasService {
	
	Page<Xbknam> obtenerAreasPorUsuarioYAno(String usuario, Integer ano, Pageable pageable);
	
}
