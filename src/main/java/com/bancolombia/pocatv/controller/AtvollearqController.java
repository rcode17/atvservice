package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.service.AtvffapaService;
import com.bancolombia.pocatv.service.AtvffcharsService;
import com.bancolombia.pocatv.service.AtvrchplaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvollearq")
public class AtvollearqController {
	@Autowired
    private AtvrchplaaService atvrchplaaService;
    @Autowired
    private AtvffcharsService atvffcharsService;

    @GetMapping("/Atvollearq")
    public ResponseEntity<?> Atvollearq() {
        try {
            //ATVRCHPLAA
            atvrchplaaService.procesarArqueos();
            //ATVRCHPLAS
            atvffcharsService.procesarArqueos();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Registros procesados correctamente");

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al procesar los registros: " + e.getMessage()));
        }
    }
}
