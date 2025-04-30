package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.UsuarioResponseDTO;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.service.AtvrcargueService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atvrcargue")
public class AtvrcargueController {

    @Autowired
    private AtvrcargueService atvrcargueService;

    @PostMapping("/procesar")
    public ResponseEntity<String> procesarDatos() {
        try {
            atvrcargueService.procesarDatos();
            return ResponseEntity.ok("Procesamiento de datos completado con éxito");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar datos: " + e.getMessage());
        }
    }
    
    
    
    @PostMapping("/procesarcargue")
    public ResponseEntity<Map<String, Object>> procesarCargue() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            atvrcargueService.procesarCargue();
            
            response.put("estado", "éxito");
            response.put("mensaje", "Proceso de carga completado correctamente");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("estado", "error");
            response.put("mensaje", "Error en el proceso de carga: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para procesar solo los registros de ATVFFSAI1
     * 
     * @return Respuesta con el resultado del proceso
     */
    @PostMapping("/procesar-atvffsai1")
    public ResponseEntity<Map<String, Object>> procesarAtvffsai1() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            atvrcargueService.procesarAtvffsai1();
            
            response.put("estado", "éxito");
            response.put("mensaje", "Proceso de carga de ATVFFSAI1 completado correctamente");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("estado", "error");
            response.put("mensaje", "Error en el proceso de carga de ATVFFSAI1: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para procesar solo los registros de ATVFFSAI2
     * 
     * @return Respuesta con el resultado del proceso
     */
    @PostMapping("/procesar-atvffsai2")
    public ResponseEntity<Map<String, Object>> procesarAtvffsai2() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            atvrcargueService.procesarAtvffsai2();
            
            response.put("estado", "éxito");
            response.put("mensaje", "Proceso de carga de ATVFFSAI2 completado correctamente");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("estado", "error");
            response.put("mensaje", "Error en el proceso de carga de ATVFFSAI2: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para verificar el estado del servicio
     * 
     * @return Respuesta indicando que el servicio está activo
     */
    @GetMapping("/estado")
    public ResponseEntity<Map<String, Object>> verificarEstado() {
        Map<String, Object> response = new HashMap<>();
        response.put("estado", "activo");
        response.put("servicio", "Servicio de carga de arqueos");
        response.put("timestamp", System.currentTimeMillis());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    
    
    
    
    
    
    
}