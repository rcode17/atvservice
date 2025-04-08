package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.AtvffrecResponseDTO;
import com.bancolombia.pocatv.service.AtvffrecService;

@RestController
@RequestMapping("/api/atvffrec")
public class AtvffrecController {
	
	@Autowired
    private AtvffrecService service;

	@GetMapping("/fecha")
    public ResponseEntity<Page<AtvffrecResponseDTO>> getRechazosPorFecha(
            @RequestParam("fecha") String fecha,
            Pageable pageable) {
        Page<AtvffrecResponseDTO> result = service.findByFechaRechazo(fecha, pageable);
        return ResponseEntity.ok(result);
    }

}
