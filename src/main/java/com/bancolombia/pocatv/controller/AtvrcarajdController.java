package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.service.AtvrcarajdService;

@RestController
@RequestMapping("/api/atvrcarajd")
public class AtvrcarajdController {

	@Autowired
	private AtvrcarajdService atvrcarajdService;

	@PostMapping("/procesar")
	public ResponseEntity<String> procesarArqueos() {
		int registrosProcesados = atvrcarajdService.procesarAtvrcarajd();
		return ResponseEntity.ok("Procesamiento completado. Registros procesados: " + registrosProcesados);
	}

}
