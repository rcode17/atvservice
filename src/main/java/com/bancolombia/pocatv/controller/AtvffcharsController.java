package com.bancolombia.pocatv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.model.Atvffchsal;
import com.bancolombia.pocatv.service.AtvffcharsService;

@RestController
@RequestMapping("/api/atvffchars")
public class AtvffcharsController {


    @Autowired
    private AtvffcharsService atvffcharsService;
    
    @PostMapping("/procesar")
    public ResponseEntity<List<Atvffchsal>> procesarArqueos() {
        List<Atvffchsal> arqueos = atvffcharsService.procesarArqueos();
        return new ResponseEntity<>(arqueos, HttpStatus.OK);
    }
}
