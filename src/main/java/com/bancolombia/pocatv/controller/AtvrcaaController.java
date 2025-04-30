package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.GrupoResponseDTO;
import com.bancolombia.pocatv.model.Atvffcaa;
import com.bancolombia.pocatv.model.Atvffoas;
import com.bancolombia.pocatv.service.AtvffoasService;
import com.bancolombia.pocatv.service.AtvrcaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvcaa")
public class AtvrcaaController {

    @Autowired
    private AtvrcaaService atvrcaaService;

    @Autowired
    public AtvrcaaController(AtvrcaaService atvrcaaService) {
        this.atvrcaaService = atvrcaaService;
    }

    @PostMapping("/procesar/{ano}")
    public ResponseEntity<?> procesarDatosAnuales(@PathVariable Integer ano) {
        try {
            List<Atvffcaa> resultados = atvrcaaService.procesarDatosAnuales(ano);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Procesamiento completado con éxito");
            response.put("registros", resultados.size());
            response.put("datos", resultados);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error interno del servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene los datos de arqueos para un año específico
     */
    @GetMapping("/{ano}")
    public ResponseEntity<?> obtenerDatosPorAno(@PathVariable Integer ano) {
        try {
            List<Atvffcaa> resultados = atvrcaaService.obtenerDatosPorAno(ano);

            if (resultados.isEmpty()) {
                return new ResponseEntity<>(Map.of("mensaje", "No se encontraron datos para el año: " + ano),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error interno del servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina los datos de arqueos para un año específico
     */
    @DeleteMapping("/{ano}")
    public ResponseEntity<?> eliminarDatosPorAno(@PathVariable Integer ano) {
        try {
            atvrcaaService.eliminarDatosPorAno(ano);

            return new ResponseEntity<>(Map.of("mensaje", "Datos eliminados con éxito para el año: " + ano),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error interno del servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}