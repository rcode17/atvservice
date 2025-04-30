package com.bancolombia.pocatv.service;


import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AtvrcargueService {
	/**
	 * Método principal que inicia el procesamiento de datos.
	 */
	void procesarDatos();
    void procesarCargue();
    void procesarAtvffsai1();
    void procesarAtvffsai2();
}
