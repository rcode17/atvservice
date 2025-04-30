package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.ResponseDTO;
import com.bancolombia.pocatv.service.AtvrcascaService;

@RestController
@RequestMapping("/api/atvrcasca")
public class AtvrcascaController {

	@Autowired
	private AtvrcascaService atvrcascaService;

	@PostMapping("/procesar")
	public ResponseEntity<ResponseDTO> procesarCargaInformacion() {
		atvrcascaService.procesarCargaInformacion();
		return ResponseEntity.ok(new ResponseDTO(true, "Proceso Exitoso"));
	}

}
