package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.AtvffCae;
import com.bancolombia.pocatv.service.AtvffCaeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvrcae")
public class AtvrcaeController {

    @Autowired
    private AtvffCaeService atvffCaeService;

    @GetMapping("/generar-reporte")
    public ResponseEntity<List<AtvffCae>> generarReporteCumplimiento(
            @RequestParam Integer mes, 
            @RequestParam Integer ano) {
        
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        
        if (ano < 1900 || ano > 2100) {
            throw new IllegalArgumentException("El año debe estar en un rango válido");
        }
        
        List<AtvffCae> resultados = atvffCaeService.generarReporteCumplimiento(mes, ano);
        return ResponseEntity.ok(resultados);
    }
    
    @DeleteMapping("/limpiar-datos")
    public ResponseEntity<Void> limpiarDatosAnteriores(
            @RequestParam Integer mes, 
            @RequestParam Integer ano) {
        
        atvffCaeService.limpiarDatosAnteriores(mes, ano);
        return ResponseEntity.ok().build();
    }
}