package com.bancolombia.pocatv.controller;
import com.bancolombia.pocatv.dto.FechaConsultaDTO;
import com.bancolombia.pocatv.service.AtvrmpercService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrmperc")
public class AtvrmpercController {

    @Autowired
    private AtvrmpercService batchService;

    @PostMapping("/atvrCAE2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCAE2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CAE2
        String resultado = batchService.ejecutarBatchCAE2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrOAS2")
    public ResponseEntity<Map<String, String>> ejecutarBatchOAS2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch OAS2
        String resultado = batchService.ejecutarBatchOAS2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCEP2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCEP2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CEP2
        String resultado = batchService.ejecutarBatchCEP2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCPS2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCPS2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CPS2
        String resultado = batchService.ejecutarBatchCPS2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCRI2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCRI2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CRI2
        String resultado = batchService.ejecutarBatchCRI2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCRD2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCRD2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CRD2
        String resultado = batchService.ejecutarBatchCRD2(fechaConsulta.getFechaConsulta());
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }
}
