package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvffdsunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvffdsun")
public class AtvffdsunController {
	 @Autowired
	 private AtvffdsunService atvffdsunService;
	 
	 @PostMapping("/procesar")
	 public ResponseEntity<Map<String, Object>> procesarActualizacion(
	        @RequestParam Integer mes,
	        @RequestParam Integer ano,
	        @RequestParam String usuario) {
	    
	    try {
	        atvffdsunService.procesarActualizacion(mes, ano, usuario);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("mensaje", "Proceso completado exitosamente");
	        response.put("estado", "OK");
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("mensaje", "Error al procesar la actualización: " + e.getMessage());
	        response.put("estado", "ERROR");
	        
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/consultar")
	public ResponseEntity<?> consultarDatos(
	        @RequestParam Integer mes,
	        @RequestParam Integer ano) {
	    
	    try {
	        List<Object> datos = atvffdsunService.consultarDatos(mes, ano);
	        
	        return new ResponseEntity<>(datos, HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("mensaje", e.getMessage());
	        response.put("estado", "ERROR");
	        
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("mensaje", "Error al consultar datos: " + e.getMessage());
	        response.put("estado", "ERROR");
	        
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
