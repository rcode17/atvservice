package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.Atvfftem;

import java.util.List;

public interface AtvrvalService {
	List<Atvfftem> findAll();

	void procesarArqueos();

	void procesarDatosAtvffmesal();
}
