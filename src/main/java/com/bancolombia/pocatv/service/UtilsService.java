package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.FechaMesAnioRequestDTO;
import com.bancolombia.pocatv.dto.FechaResponse;

public interface UtilsService {
	public FechaResponse procesarFecha(FechaMesAnioRequestDTO request);

}
