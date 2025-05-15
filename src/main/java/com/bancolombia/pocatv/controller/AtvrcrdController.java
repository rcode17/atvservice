package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvrcrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrcrd")
public class AtvrcrdController {

    @Autowired
    private AtvrcrdService atvrcrdService;
    
    /**
     * Endpoint para generar arqueos descuadrados
     * @param mes Mes (1-12)
     * @param anno Año (formato YYYY)
     * @return Respuesta con el resultado del proceso
     */
    @PostMapping("/descuadrados/generar")
    public ResponseEntity<?> generarArqueosDescuadrados(
            @RequestParam("mes") int mes,
            @RequestParam("anno") int anno) {
        
        try {
            // Validar parámetros
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "El mes debe estar entre 1 y 12")
                );
            }
            
            if (anno < 1900 || anno > 2100) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "El año debe estar entre 1900 y 2100")
                );
            }
            
            int registrosProcesados = atvrcrdService.generarArqueosDescuadrados(mes, anno);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Proceso completado exitosamente");
            response.put("registrosProcesados", registrosProcesados);
            response.put("mes", mes);
            response.put("anno", anno);
            
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error al procesar la solicitud: " + e.getMessage())
            );
        }
    }
}