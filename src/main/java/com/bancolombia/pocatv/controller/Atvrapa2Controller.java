package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.Atvffapa;
import com.bancolombia.pocatv.service.Atvrapa2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrapa2")
@Slf4j
public class Atvrapa2Controller {
    @Autowired
    private Atvrapa2Service arqueoAnormalService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("API funcionando correctamente", HttpStatus.OK);
    }

    @PostMapping("/procesar")
    public ResponseEntity<Map<String, Object>> procesarArqueosAnormales(
            @RequestParam Integer ano,
            @RequestParam(required = false) String fecha) {

        log.info("Procesando arqueos anormales para año: {}, fecha: {}", ano, fecha);

        try {
            // Validar parámetros
            if (ano == null) {
                return new ResponseEntity<>(Map.of("error", "El año es obligatorio"), HttpStatus.BAD_REQUEST);
            }

            int arqueosProcesados = arqueoAnormalService.procesarArqueosAnormales(ano, fecha);
            log.info("Proceso completado. Arqueos procesados: {}", arqueosProcesados);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Proceso completado exitosamente");
            response.put("arqueosProcesados", arqueosProcesados);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error de validación: {}", e.getMessage(), e);
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage(), e);
            return new ResponseEntity<>(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{ano}")
    public ResponseEntity<List<Atvffapa>> obtenerArqueosAnormales(@PathVariable Integer ano) {
        try {
            List<Atvffapa> arqueos = arqueoAnormalService.obtenerArqueosAnormales(ano);
            return new ResponseEntity<>(arqueos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<Atvffapa>> obtenerArqueosAnormalesPorMes(
            @PathVariable Integer ano,
            @PathVariable Integer mes) {

        try {
            List<Atvffapa> arqueos = arqueoAnormalService.obtenerArqueosAnormalesPorMes(ano, mes);
            return new ResponseEntity<>(arqueos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
