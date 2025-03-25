package com.bancolombia.pocatv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.model.Cargo;
import com.bancolombia.pocatv.service.CargoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<Cargo> getAllCargos() {
        return cargoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Long id) {
        Optional<Cargo> cargo = cargoService.findById(id);
        return cargo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cargo createCargo(@RequestBody Cargo cargo) {
        return cargoService.save(cargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Long id, @RequestBody Cargo cargoDetails) {
        Optional<Cargo> cargo = cargoService.findById(id);
        if (cargo.isPresent()) {
            Cargo updatedCargo = cargo.get();
            updatedCargo.setCodigo(cargoDetails.getCodigo());
            updatedCargo.setNombre(cargoDetails.getNombre());
            updatedCargo.setDescripcion(cargoDetails.getDescripcion());
            return ResponseEntity.ok(cargoService.save(updatedCargo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        cargoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    
}