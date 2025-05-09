package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ResultadoProcesamientoDTO;
import com.bancolombia.pocatv.service.AtvffmearqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atvffmearq")
public class AtvffmearqController {
	private final AtvffmearqService atvffmearqService;

    @Autowired
    public AtvffmearqController(AtvffmearqService atvffmearqService) {
        this.atvffmearqService = atvffmearqService;
    }

    /**
     * Procesa todos los arqueos y los carga en la tabla temporal
     * @return Mensaje de confirmación
     */
    @PostMapping("/procesar")
    public ResponseEntity<Map<String, Object>> procesarArqueos() {
    	try {
            // Llamada al servicio para procesar los arqueos
            ResultadoProcesamientoDTO resultado = atvffmearqService.procesarArqueos();

            // Construcción de la respuesta
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Arqueos procesados correctamente");
            respuesta.put("totalRegistros", resultado.getTotalRegistros());
            respuesta.put("registrosExitosos", resultado.getRegistrosExitosos());
            respuesta.put("registrosFallidos", resultado.getRegistrosFallidos());

            // Si hay registros fallidos, incluir los errores en la respuesta
            if (resultado.getRegistrosFallidos() > 0) {
                respuesta.put("errores", resultado.getErrores());
                return new ResponseEntity<>(respuesta, HttpStatus.PARTIAL_CONTENT);
            }

            // Respuesta exitosa
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Manejo de excepciones específicas
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // Manejo de excepciones generales
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("error", "Error al procesar los arqueos: " + e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
