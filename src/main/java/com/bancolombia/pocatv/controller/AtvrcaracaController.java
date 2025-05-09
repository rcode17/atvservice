package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvrcaracaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atvrcaraca")
public class AtvrcaracaController {
	
	private final AtvrcaracaService atvrcaracaService;

    @Autowired
    public AtvrcaracaController(AtvrcaracaService atvrcaracaService) {
        this.atvrcaracaService = atvrcaracaService;
    }
    
    @PostMapping("/procesar")
    public ResponseEntity<String> procesarArqueos() {
        int registrosProcesados = atvrcaracaService.procesarArqueos();
        return ResponseEntity.ok("Procesamiento completado. Registros procesados: " + registrosProcesados);
    }
    
}
