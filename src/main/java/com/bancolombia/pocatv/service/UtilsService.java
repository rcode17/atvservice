package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.FechaMesAnioRequestDTO;
import com.bancolombia.pocatv.dto.FechaTransformResponseDTO;
import com.bancolombia.pocatv.dto.FechaResponse;

public interface UtilsService {
	public FechaResponse procesarFecha(FechaMesAnioRequestDTO request);
	public FechaTransformResponseDTO restarUnDia(String fecha);

}
