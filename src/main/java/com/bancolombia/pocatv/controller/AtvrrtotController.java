package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.FechaVerificacionDTO;
import com.bancolombia.pocatv.service.AtvffapaService;
import com.bancolombia.pocatv.service.AtvrrtotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrrtot")
public class AtvrrtotController {

    private final AtvrrtotService verificacionesService;

    @Autowired
    public AtvrrtotController(AtvrrtotService verificacionesService) {
        this.verificacionesService = verificacionesService;
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("fecha", LocalDate.now().toString());
        response.put("hora", LocalTime.now().toString());
        response.put("usuario", System.getProperty("user.name"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recalcular")
    public ResponseEntity<?> recalcularTotales(@RequestBody FechaVerificacionDTO fechaVerificacion) {
        try {
            verificacionesService.validarFecha(fechaVerificacion);
            return ResponseEntity.ok(Map.of("mensaje", "Fecha valida"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la solicitud: " + e.getMessage()));
        }
    }
}
