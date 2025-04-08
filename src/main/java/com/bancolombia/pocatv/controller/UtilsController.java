package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.FechaMesAnioRequestDTO;
import com.bancolombia.pocatv.dto.FechaResponse;
import com.bancolombia.pocatv.service.UtilsService;

@RestController
@RequestMapping("/api")
public class UtilsController {
	
	@Autowired
    private UtilsService fechaService;

    @PostMapping("/procesarFecha")
    public ResponseEntity<FechaResponse> procesarFecha(@RequestBody FechaMesAnioRequestDTO request) {
        FechaResponse response = fechaService.procesarFecha(request);
        if(response.getMensajeError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

}
