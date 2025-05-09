package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.Atvffcaa;
import com.bancolombia.pocatv.service.AtvffCaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvffcaa")
public class AtvffcaaController {

    @Autowired
    private AtvffCaaService atvffCaaService;

    @PostMapping("/generar")
    public ResponseEntity<List<Atvffcaa>> generarReporte(
            @RequestParam Integer ano,
            @RequestParam(required = false) String fecha) {
        List<Atvffcaa> resultado = atvffCaaService.generarReporteCumplimiento(ano, fecha);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{ano}")
    public ResponseEntity<List<Atvffcaa>> obtenerReportePorAno(@PathVariable Integer ano) {
        List<Atvffcaa> reporte = atvffCaaService.obtenerReportePorAno(ano);
        if (reporte.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte);
    }

    @DeleteMapping("/{ano}")
    public ResponseEntity<Void> eliminarReportePorAno(@PathVariable Integer ano) {
        atvffCaaService.eliminarReportePorAno(ano);
        return ResponseEntity.noContent().build();
    }
}