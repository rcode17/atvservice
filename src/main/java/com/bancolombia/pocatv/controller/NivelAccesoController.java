package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.model.NivelAcceso;
import com.bancolombia.pocatv.service.NivelAccesoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/niveles-acceso")
public class NivelAccesoController {

    @Autowired
    private NivelAccesoService nivelAccesoService;

    @GetMapping
    public List<NivelAcceso> getAllNivelesAcceso() {
        return nivelAccesoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelAcceso> getNivelAccesoById(@PathVariable Long id) {
        Optional<NivelAcceso> nivelAcceso = nivelAccesoService.findById(id);
        return nivelAcceso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public NivelAcceso createNivelAcceso(@RequestBody NivelAcceso nivelAcceso) {
        return nivelAccesoService.save(nivelAcceso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NivelAcceso> updateNivelAcceso(@PathVariable Long id, @RequestBody NivelAcceso nivelAccesoDetails) {
        Optional<NivelAcceso> nivelAcceso = nivelAccesoService.findById(id);
        if (nivelAcceso.isPresent()) {
            NivelAcceso updatedNivelAcceso = nivelAcceso.get();
            updatedNivelAcceso.setCodigo(nivelAccesoDetails.getCodigo());
            updatedNivelAcceso.setNombre(nivelAccesoDetails.getNombre());
            updatedNivelAcceso.setDescripcion(nivelAccesoDetails.getDescripcion());
            return ResponseEntity.ok(nivelAccesoService.save(updatedNivelAcceso));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNivelAcceso(@PathVariable Long id) {
        nivelAccesoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}