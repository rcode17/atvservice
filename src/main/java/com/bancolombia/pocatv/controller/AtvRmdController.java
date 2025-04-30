package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.FechaResponse;
import com.bancolombia.pocatv.service.AtvRmdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/AtvRmd")
public class AtvRmdController {

    //private static final Logger logger = LoggerFactory.getLogger(AtvRmdController.class);
    private final AtvRmdService atvRmdService;

    @Autowired
    public AtvRmdController(AtvRmdService atvRmdService) {
        this.atvRmdService = atvRmdService;
    }

    @PostMapping("/validarAnio")
    public ResponseEntity<?> validarAnio(@RequestBody Map<String, Integer> request) {
        try {
            Integer anio = request.get("anio");
            Integer resultado = atvRmdService.procesarAnio(anio);

            // Crear respuesta exitosa
            FechaResponse response = new FechaResponse();
            response.setResultado(resultado.toString());
            response.setMensajeError(null); // o puedes omitir este campo si prefieres

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Crear respuesta de error
            FechaResponse errorResponse = new FechaResponse();
            errorResponse.setResultado(null);
            errorResponse.setMensajeError(e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
