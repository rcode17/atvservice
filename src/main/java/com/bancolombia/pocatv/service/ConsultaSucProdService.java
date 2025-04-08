package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ConsultaSucProdDetalleRequestDTO;
import com.bancolombia.pocatv.dto.ConsultaSucProdDetalleResponseDTO;

public interface ConsultaSucProdService {

	
	 public ConsultaSucProdDetalleResponseDTO procesarConsulta(ConsultaSucProdDetalleRequestDTO request);
}
