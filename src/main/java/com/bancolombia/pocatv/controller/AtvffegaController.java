package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ResumenMetricasDTO;
import com.bancolombia.pocatv.service.AtvffegaService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/regiones")
public class AtvffegaController {
	
	@Autowired
    private AtvffegaService atvffegaService;
    
    @GetMapping("/metricas")
    public ResponseEntity<ResumenMetricasDTO> obtenerMetricasSucursales(
            @RequestParam String usuario) {
        
        ResumenMetricasDTO resumen = atvffegaService.obtenerMetricasSucursales(usuario);
        return ResponseEntity.ok(resumen);
    }
}
