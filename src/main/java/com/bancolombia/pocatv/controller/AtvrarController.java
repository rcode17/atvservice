package com.bancolombia.pocatv.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.service.AtvrvalService;
import com.bancolombia.pocatv.service.AtvrRepService;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@RestController
@RequestMapping("/api/atvrar")
public class AtvrarController {
	
	@Autowired
    private AtvrvalService atvrvalService;
	@Autowired
	private AtvrRepService atvrRepService;
	
	public AtvrarController(AtvrvalService atvrvalService, AtvrRepService atvrRepService) {
        this.atvrvalService = atvrvalService;
        this.atvrRepService = atvrRepService;
    }

    @PostMapping("/validarFechas")
    public ResponseEntity<String> validarFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        String mensaje = "Proceso Ejecutado. Puede Salir";
       
        String resultInicio = validarFecha(fechaInicio);
        if (!resultInicio.equals("Valido")) {
            return ResponseEntity.badRequest().body(resultInicio);
        }
        
        String resultFin = validarFecha(fechaFin);
        if (!resultFin.equals("Valido")) {
            return ResponseEntity.badRequest().body(resultFin);
        }       

        return ResponseEntity.ok(mensaje);
    }

    private String validarFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
            if (fechaLocal.isAfter(LocalDate.now())) {
                return "FECHA FUTURA";
            }
        } catch (DateTimeParseException e) {
            return "FECHA INVALIDA";
        }

        return "Valido";
    }
    
    @GetMapping("/atvrval")
    public ResponseEntity<String>  procesarRegistros() {
        try {
            atvrvalService.processRecords();
            return  ResponseEntity.ok( "Proceso de registros completado");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/atvrrep")
    public  ResponseEntity<String> procesarRegistros(
            @RequestParam String usuario,
            @RequestParam String mesInicio,
            @RequestParam String diaInicio,
            @RequestParam String anoInicio,
            @RequestParam String mesFin,
            @RequestParam String diaFin,
            @RequestParam String anoFin) {
        try {
            int registrosProcesados = atvrRepService.procesarRechazos(
                    usuario, mesInicio, diaInicio, anoInicio, mesFin, diaFin, anoFin);
            return ResponseEntity.ok("Proceso completado. Registros procesados: " + registrosProcesados);
            }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en los parámetros: " + e.getMessage());
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }
}