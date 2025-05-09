package com.bancolombia.pocatv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.model.Atvffran;
import com.bancolombia.pocatv.service.AtvffranService;

@RestController
@RequestMapping("/api/atvffran/periodicidad-anormal")
public class AtvffranController {

    private final AtvffranService atvffranService;

    @Autowired
    public AtvffranController(AtvffranService atvffranService) {
        this.atvffranService = atvffranService;
    }

    @GetMapping
    public ResponseEntity<List<Atvffran>> getAllPeriodicidades() {
        return ResponseEntity.ok(atvffranService.findAll());
    }

    @GetMapping("/{rnfrec}/{rncant}")
    public ResponseEntity<Atvffran> getPeriodicidadById(
            @PathVariable String rnfrec,
            @PathVariable Integer rncant) {
        return ResponseEntity.ok(atvffranService.findById(rnfrec, rncant));
    }

    @PostMapping
    public ResponseEntity<Atvffran> createPeriodicidad(@RequestBody Atvffran atvffran) {
        try {
            Atvffran savedAtvffran = atvffranService.save(atvffran);
            return new ResponseEntity<>(savedAtvffran, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @DeleteMapping("/{rnfrec}/{rncant}")
    public ResponseEntity<Void> deletePeriodicidad(
            @PathVariable String rnfrec,
            @PathVariable Integer rncant) {
        atvffranService.deleteById(rnfrec, rncant);
        return ResponseEntity.noContent().build();
    }

}
