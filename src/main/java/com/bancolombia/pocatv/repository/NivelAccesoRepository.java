package com.bancolombia.pocatv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.NivelAcceso;

@Repository
public interface NivelAccesoRepository extends JpaRepository<NivelAcceso, Long> {
}