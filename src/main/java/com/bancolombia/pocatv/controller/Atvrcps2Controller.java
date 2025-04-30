package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.Atvrcps2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrcps2")
public class Atvrcps2Controller {
    @Autowired
    private Atvrcps2Service atvrcps2Service;

    @PostMapping("/procesar")
    public ResponseEntity<Map<String, String>> procesarActualizacion(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {

        try {
            atvrcps2Service.procesarActualizacion(mes, ano);
            return ResponseEntity.ok(Map.of("mensaje", "Proceso completado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<Object>> consultarResultados(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {

        try {
            List<Object> resultados = atvrcps2Service.consultarResultados(mes, ano);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
