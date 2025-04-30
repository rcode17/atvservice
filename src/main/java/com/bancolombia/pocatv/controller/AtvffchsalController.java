package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.service.AtvrcarajdService;

@RestController
@RequestMapping("/api/atvffchsal")
public class AtvffchsalController {

	private final AtvrcarajdService atvffchsalService;

	@Autowired
	public AtvffchsalController(AtvrcarajdService atvffchsalService) {
		this.atvffchsalService = atvffchsalService;
	}

	@PostMapping("/procesar-carga")
	public ResponseEntity<String> procesarCargaJDE() {
		try {
			atvffchsalService.procesarCargaJDE();
			return new ResponseEntity<>("Proceso de carga completado exitosamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error en el proceso de carga: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
