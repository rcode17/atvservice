package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.EstadisticaRegionDTO;
import com.bancolombia.pocatv.dto.ParametrosConsultaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AtvriaaService {

	List<EstadisticaRegionDTO> generarEstadisticasPorRegion(Integer ano , String fechaStr);
	void limpiarEstadisticas(Integer ano);
}