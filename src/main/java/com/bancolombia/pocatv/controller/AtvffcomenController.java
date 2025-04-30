package com.bancolombia.pocatv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.model.Atvffcomen;
import com.bancolombia.pocatv.service.AtvffcomenService;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")

public class AtvffcomenController {

    private final AtvffcomenService atvffcomenService;

    @Autowired
    public AtvffcomenController(AtvffcomenService atvffcomenService) {
        this.atvffcomenService = atvffcomenService;
    }

    @GetMapping
    public ResponseEntity<List<Atvffcomen>> getAllComentarios() {
        List<Atvffcomen> comentarios = atvffcomenService.findAllComentarios();
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atvffcomen> getComentarioById(@PathVariable Integer id) {
        try {
            Atvffcomen comentario = atvffcomenService.findComentarioById(id);
            return new ResponseEntity<>(comentario, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Atvffcomen> createComentario(@RequestBody Atvffcomen comentario) {
        try {
            Atvffcomen savedComentario = atvffcomenService.saveComentario(comentario);
            return new ResponseEntity<>(savedComentario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atvffcomen> updateComentario(@PathVariable Integer id, @RequestBody Atvffcomen comentario) {
        try {
            Atvffcomen updatedComentario = atvffcomenService.updateComentario(id, comentario);
            return new ResponseEntity<>(updatedComentario, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Integer id) {
        try {
            atvffcomenService.deleteComentario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
