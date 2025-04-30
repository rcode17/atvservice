package com.bancolombia.pocatv.service;

import java.time.LocalDate;
import java.util.List;

import com.bancolombia.pocatv.dto.ConciliacionDTO;

public interface ConciliacionService {
	List<ConciliacionDTO> obtenerConciliacion(String codigoProducto, String codigoDocumento, LocalDate fecha);

}
