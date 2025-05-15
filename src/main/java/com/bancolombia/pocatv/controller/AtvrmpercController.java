package com.bancolombia.pocatv.controller;
import com.bancolombia.pocatv.dto.FechaConsultaDTO;
import com.bancolombia.pocatv.service.*;
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

    @Autowired
    private AtvffCaeService atvffCaeService;

    @Autowired
    private AtvffoasService atvffoasService;

    @Autowired
    private AtvffcepService atvffcepService;

    @Autowired
    private AtvrcrdService atvrcrdService;

    @Autowired
    private Atvrcri2Service atvrcri2Service;

    @Autowired
    private Atvrcps2Service atvrcps2Service;


    @PostMapping("/atvrCAE2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCAE2(@RequestBody FechaConsultaDTO fechaConsulta) {
        String fechaStr = fechaConsulta.getFechaConsulta();

        // Validar que la longitud de la fecha sea correcta
        if (fechaStr.length() != 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Debe ser 'MMYYYY'"));
        }

        Integer mes = Integer.parseInt(fechaStr.substring(0, 2));
        Integer ano = Integer.parseInt(fechaStr.substring(2, 6));

        atvffCaeService.generarReporteCumplimiento (mes,ano);
        String resultado = "Batch ejecutado con fecha: " + fechaConsulta;
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrOAS2")
    public ResponseEntity<Map<String, String>> ejecutarBatchOAS2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch OAS2
        String fechaStr = fechaConsulta.getFechaConsulta();

        // Validar que la longitud de la fecha sea correcta
        if (fechaStr.length() != 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Debe ser 'MMYYYY'"));
        }

        Integer mes = Integer.parseInt(fechaStr.substring(0, 2));
        Integer ano = Integer.parseInt(fechaStr.substring(2, 6));

        atvffoasService.procesarActualizacion (mes,ano);


        String resultado = "Batch ejecutado con fecha: " + fechaConsulta;
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCEP2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCEP2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch OAS2
        String fechaStr = fechaConsulta.getFechaConsulta();

        // Validar que la longitud de la fecha sea correcta
        if (fechaStr.length() != 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Debe ser 'MMYYYY'"));
        }

        Integer mes = Integer.parseInt(fechaStr.substring(0, 2));
        Integer ano = Integer.parseInt(fechaStr.substring(2, 6));

        atvffcepService.procesarConsultaEspecifica (mes,ano);


        String resultado = "Batch ejecutado con fecha: " + fechaConsulta;
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCPS2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCPS2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CPS2
        String fechaStr = fechaConsulta.getFechaConsulta();

        // Validar que la longitud de la fecha sea correcta
        if (fechaStr.length() != 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Debe ser 'MMYYYY'"));
        }

        Integer mes = Integer.parseInt(fechaStr.substring(0, 2));
        Integer ano = Integer.parseInt(fechaStr.substring(2, 6));

        atvrcps2Service.procesarActualizacion (mes,ano);

        String resultado = "Batch ejecutado con fecha: " + fechaConsulta;
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCRI2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCRI2(@RequestBody FechaConsultaDTO fechaConsulta) {
        // Lógica para ejecutar el batch CRI2
        String fechaStr = fechaConsulta.getFechaConsulta();

        // Validar que la longitud de la fecha sea correcta
        if (fechaStr.length() != 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Debe ser 'MMYYYY'"));
        }

        Integer mes = Integer.parseInt(fechaStr.substring(0, 2));
        Integer ano = Integer.parseInt(fechaStr.substring(2, 6));

        atvrcri2Service.generarArchivoRangosInconsistentes(mes,ano);


        String resultado = "Batch ejecutado con fecha: " + fechaConsulta;
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/atvrCRD2")
    public ResponseEntity<Map<String, String>> ejecutarBatchCRD2(@RequestBody FechaConsultaDTO fechaConsulta) {

        String fechaStr = fechaConsulta.getFechaConsulta();

        // Validar que la longitud de la fecha sea correcta
        if (fechaStr.length() != 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Debe ser 'MMYYYY'"));
        }

        Integer mes = Integer.parseInt(fechaStr.substring(0, 2));
        Integer ano = Integer.parseInt(fechaStr.substring(2, 6));

        atvrcrdService.generarArqueosDescuadrados (mes,ano);


        String resultado = "Batch ejecutado con fecha: " + fechaConsulta;
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);

        return ResponseEntity.ok(response);
    }
}
