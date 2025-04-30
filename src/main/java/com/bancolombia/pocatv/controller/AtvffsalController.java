package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvffsalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atvffsal")
public class AtvffsalController {
    
    private final AtvffsalService atvffsalService;
    
    @Autowired
    public AtvffsalController(AtvffsalService atvffsalService) {
        this.atvffsalService = atvffsalService;
    }
    
    @PostMapping("/update-saofco")
    public ResponseEntity<String> updateSaofcoWithSaofic() {
        try {
            int updatedRecords = atvffsalService.updateSaofcoWithSaofic();
            return ResponseEntity.ok("Actualización completada. Registros actualizados: " + updatedRecords);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en la actualización: " + e.getMessage());
        }
    }
}