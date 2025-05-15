package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.FechaConsultaDTO;
import com.bancolombia.pocatv.service.AtvfreinService;
import com.bancolombia.pocatv.service.Atvrega2Service;
import com.bancolombia.pocatv.service.AtvrmpersService;
import com.bancolombia.pocatv.service.AtvrvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrval")
public class AtvrvalController {

    @Autowired
    private AtvrvalService atvrvalService;



    @GetMapping("/procesarArqueos")
    public ResponseEntity<Map<String, String>> procesarArqueos() {
try {
    atvrvalService.procesarArqueos();
    String resultado = "Proceso ejecutado con exito: ";
    Map<String, String> response = new HashMap<>();
    response.put("message", resultado);

    return ResponseEntity.ok(response);
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

}
