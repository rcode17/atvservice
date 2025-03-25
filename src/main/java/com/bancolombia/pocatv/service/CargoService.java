package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.Cargo;

import java.util.List;
import java.util.Optional;

public interface CargoService {
    List<Cargo> findAll();
    Optional<Cargo> findById(Long id);
    Cargo save(Cargo cargo);
    void deleteById(Long id);
}
