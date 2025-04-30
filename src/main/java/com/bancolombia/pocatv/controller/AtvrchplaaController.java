package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvrchplaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atvrchplaa")
public class AtvrchplaaController {
	@Autowired
    private AtvrchplaaService atvrchplaaService;

    @PostMapping("/procesar")
    public ResponseEntity<String> procesarArqueos() {
        atvrchplaaService.procesarArqueos();
        return ResponseEntity.ok("Procesamiento de arqueos completado");
    }
}
