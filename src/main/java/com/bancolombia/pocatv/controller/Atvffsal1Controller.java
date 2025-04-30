package com.bancolombia.pocatv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.model.Atvffsal1;
import com.bancolombia.pocatv.model.Atvffsal1Id;
import com.bancolombia.pocatv.service.Atvffsal1Service;

import java.util.List;

@RestController
@RequestMapping("/api/atvffsal1")
public class Atvffsal1Controller {

    @Autowired
    private Atvffsal1Service atvffsal1Service;

    @GetMapping
    public ResponseEntity<List<Atvffsal1>> obtenerTodosLosRegistros() {
        return ResponseEntity.ok(atvffsal1Service.obtenerTodos());
    }

    @GetMapping("/{safech}/{satpro}")
    public ResponseEntity<Atvffsal1> obtenerRegistroPorId(
            @PathVariable Long safech,
            @PathVariable String satpro) {
        try {
            Atvffsal1Id id = new Atvffsal1Id(safech, satpro);
            return ResponseEntity.ok(atvffsal1Service.obtenerPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Atvffsal1>> buscarRegistrosPorProductoYDocumento(
            @RequestParam String satpro, 
            @RequestParam String satdoc) {
        try {
            return ResponseEntity.ok(atvffsal1Service.buscarPorProductoYDocumento(satpro, satdoc));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Atvffsal1> crearRegistro(@RequestBody Atvffsal1 atvffsal) {
        return new ResponseEntity<>(atvffsal1Service.guardar(atvffsal), HttpStatus.CREATED);
    }

    @PutMapping("/{safech}/{satpro}")
    public ResponseEntity<Atvffsal1> actualizarRegistro(
            @PathVariable Long safech,
            @PathVariable String satpro,
            @RequestBody Atvffsal1 atvffsal) {
        try {
            Atvffsal1Id id = new Atvffsal1Id(safech, satpro);
            atvffsal1Service.obtenerPorId(id); // Verificar si existe
            
            // Asegurar que los campos de ID coincidan
            atvffsal.setSafech(safech);
            atvffsal.setSatpro(satpro);
            
            return ResponseEntity.ok(atvffsal1Service.guardar(atvffsal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{safech}/{satpro}")
    public ResponseEntity<Void> eliminarRegistro(
            @PathVariable Long safech,
            @PathVariable String satpro) {
        try {
            Atvffsal1Id id = new Atvffsal1Id(safech, satpro);
            atvffsal1Service.obtenerPorId(id); // Verificar si existe
            atvffsal1Service.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/actualizar-documento")
    public ResponseEntity<Void> actualizarDocumento() {
        atvffsal1Service.actualizarDocumento();
        return ResponseEntity.ok().build();
    }
}