package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.Dominio;
import com.bancolombia.pocatv.service.DominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/dominio")
public class DominioController {

    @Autowired
    private DominioService dominioService;

    @GetMapping
    public Page<Dominio> getAllDominios(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return dominioService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dominio> getDominioById(@PathVariable Integer id) {
        Optional<Dominio> dominio = dominioService.findById(id);
        return dominio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Dominio createDominio(@RequestBody Dominio dominio) {
        return dominioService.save(dominio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dominio> updateDominio(@PathVariable Integer id, @RequestBody Dominio dominio) {
        if (!dominioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dominio.setId(id);
        return ResponseEntity.ok(dominioService.save(dominio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDominio(@PathVariable Integer id) {
        if (!dominioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dominioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
