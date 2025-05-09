package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.AtvffIap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.ResumenEstadisticasDTO;
import com.bancolombia.pocatv.service.AtvriapService;

import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/atvriap")
public class AtvriapController {

    @Autowired
    private AtvriapService atvriapService;
    
    @GetMapping("/estadistica-calidad")
    public ResponseEntity<ResumenEstadisticasDTO> obtenerEstadisticasCalidad(
            @RequestParam String user,
            @RequestParam Integer ano) {
       
            ResumenEstadisticasDTO resumen = atvriapService.obtenerEstadisticasCalidad(user, ano);
            return ResponseEntity.ok(resumen);
        
    }
    
    @GetMapping("/generar/{ano}")
    public ResponseEntity<List<AtvffIap>> generarArchivoCalidadInformacion(@PathVariable int ano) {
        List<AtvffIap> resultados = atvriapService.generarArchivoCalidadInformacion(ano);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }
    
    @GetMapping("/{ano}/{codigoProducto}/{codigoDocumento}")
    public ResponseEntity<AtvffIap> obtenerCalidadInformacion(
            @PathVariable int ano,
            @PathVariable int codigoProducto,
            @PathVariable int codigoDocumento) {
        try {
        	AtvffIap resultado = atvriapService.obtenerCalidadInformacion(ano, codigoProducto, codigoDocumento);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<AtvffIap> guardarCalidadInformacion(@RequestBody AtvffIap atvffiap) {
    	AtvffIap resultado = atvriapService.guardarCalidadInformacion(atvffiap);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
    
}