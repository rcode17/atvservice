package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ResumenMetricasDTO;
import org.springframework.stereotype.Service;

@Service
public interface AtvffegaService {
	 ResumenMetricasDTO obtenerMetricasSucursales(String usuario);
}
