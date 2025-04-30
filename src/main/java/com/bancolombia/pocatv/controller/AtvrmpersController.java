package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.FechaConsultaDTO;
import com.bancolombia.pocatv.service.AtvrmpercService;
import com.bancolombia.pocatv.service.AtvrmpersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrmpers")
public class AtvrmpersController {

    @Autowired
    private AtvrmpersService batchService;

    @PostMapping("/ATVOFREIN")
    public ResponseEntity<Map<String, String>> ejecutarBatchATVOFREIN(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CAE2
        String resultado = batchService.ejecutarBatchATVOFREIN(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ATVRSAA2")
    public ResponseEntity<Map<String, String>> ejecutarBatchATVRSAA2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch OAS2
        String resultado = batchService.ejecutarBatchATVRSAA2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ATVRSIA2")
    public ResponseEntity<Map<String, String>> ejecutarBatchATVRSIA2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CEP2
        String resultado = batchService.ejecutarBatchATVRSIA2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ATVRIAP2")
    public ResponseEntity<Map<String, String>> ejecutarBatchATVRIAP2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CPS2
        String resultado = batchService.ejecutarBatchATVRIAP2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ATVREGA2")
    public ResponseEntity<Map<String, String>> ejecutarBatchATVREGA2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CRI2
        String resultado = batchService.ejecutarBatchATVREGA2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }


}
