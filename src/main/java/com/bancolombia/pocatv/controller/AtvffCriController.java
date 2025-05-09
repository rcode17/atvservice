package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvffCriTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atvffcri")
public class AtvffCriController {
	 @Autowired
	    private AtvffCriTwoService atvffCriTwoService;
	    
	    @PostMapping("/generar")
	    public ResponseEntity<String> generarArchivoRangosInconsistentes(
	            @RequestParam int mes,
	            @RequestParam int anno,
	            @RequestParam(required = false, defaultValue = "0") int dia) {
	        
	        try {
	            int registrosProcesados = atvffCriTwoService.generarArchivoRangosInconsistentes(mes, anno, dia);
	            return ResponseEntity.ok("Proceso completado exitosamente. Registros procesados: " + registrosProcesados);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error al generar archivo: " + e.getMessage());
	        }
	    }
}
