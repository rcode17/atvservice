package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.ResponseDTO;
import com.bancolombia.pocatv.service.AtvrvalService;

@RestController
@RequestMapping("/api/atvffmesal")
public class AtvffmesalController {

	@Autowired
	private AtvrvalService atvlfarqService;

	@PostMapping("/procesar")
	public ResponseEntity<ResponseDTO> procesarDatos() {

		atvlfarqService.procesarDatosAtvffmesal();
		return ResponseEntity.ok(new ResponseDTO(true, "Proceso Exitoso"));

	}
}
