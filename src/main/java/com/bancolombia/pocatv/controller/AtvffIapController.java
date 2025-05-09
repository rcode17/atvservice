package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.AtvffIap;
import com.bancolombia.pocatv.service.AtvffIapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvffiap")
public class AtvffIapController {

    @Autowired
    private AtvffIapService atvffIapService;

    @GetMapping("/generar")
    public ResponseEntity<List<AtvffIap>> generarArchivoCalidadInformacion(
            @RequestParam Integer ano,
            @RequestParam(required = false) String fecha) {
        try {
            List<AtvffIap> resultados = atvffIapService.generarArchivoCalidadInformacion(ano, fecha);
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<AtvffIap>> obtenerTodos() {
        try {
            List<AtvffIap> registros = atvffIapService.obtenerTodos();
            return new ResponseEntity<>(registros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{ano}/{codpro}/{coddoc}")
    public ResponseEntity<AtvffIap> obtenerPorId(
            @PathVariable Integer ano,
            @PathVariable Integer codpro,
            @PathVariable Integer coddoc) {
        try {
            AtvffIap registro = atvffIapService.obtenerPorId(ano, codpro, coddoc);
            return new ResponseEntity<>(registro, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<AtvffIap> guardar(@RequestBody AtvffIap atvffIap) {
        try {
            AtvffIap registroGuardado = atvffIapService.guardar(atvffIap);
            return new ResponseEntity<>(registroGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{ano}/{codpro}/{coddoc}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer ano,
            @PathVariable Integer codpro,
            @PathVariable Integer coddoc) {
        try {
            atvffIapService.eliminar(ano, codpro, coddoc);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}