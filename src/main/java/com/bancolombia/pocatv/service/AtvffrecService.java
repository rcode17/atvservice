package com.bancolombia.pocatv.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bancolombia.pocatv.dto.AtvffrecResponseDTO;

public interface AtvffrecService {
	Page<AtvffrecResponseDTO> findByFechaRechazo(String fecha, Pageable pageable);
}
