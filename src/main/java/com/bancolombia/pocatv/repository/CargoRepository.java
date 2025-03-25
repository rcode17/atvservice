package com.bancolombia.pocatv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}