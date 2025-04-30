package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffPdsId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AtvRmdService {
	boolean validarAnio(Integer anio);
	Integer procesarAnio(Integer anio);
}
