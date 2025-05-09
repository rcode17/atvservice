package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.Atvfffrein;
import com.bancolombia.pocatv.service.AtvfreinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios-reincidentes")
public class AtvfreinController {

    @Autowired
    private AtvfreinService atvfreinService;
    
    @PostMapping("/procesar")
    public ResponseEntity<Map<String, Object>> procesarFuncionariosReincidentes(
            @RequestParam(required = false) String fecha) {
        try {
            int procesados = atvfreinService.procesarFuncionariosReincidentes(fecha);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Proceso completado correctamente",
                    "funcionariosProcesados", procesados
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Atvfffrein>> obtenerFuncionariosReincidentes() {
        return ResponseEntity.ok(atvfreinService.obtenerFuncionariosReincidentes());
    }
    
    @GetMapping("/{responsable}")
    public ResponseEntity<Atvfffrein> obtenerFuncionarioReincidente(@PathVariable String responsable) {
        try {
            Atvfffrein funcionario = atvfreinService.obtenerFuncionarioReincidente(responsable);
            return ResponseEntity.ok(funcionario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{responsable}")
    public ResponseEntity<Void> eliminarFuncionarioReincidente(@PathVariable String responsable) {
        try {
            atvfreinService.eliminarFuncionarioReincidente(responsable);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        return ResponseEntity.ok(atvfreinService.obtenerEstadisticas());
    }
}