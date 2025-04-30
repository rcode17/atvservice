package com.bancolombia.pocatv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.AtvffcasalId;
import com.bancolombia.pocatv.model.Atvffcasal;


@Repository
public interface AtvffcasalRepository extends JpaRepository<Atvffcasal, AtvffcasalId> {
    // Métodos personalizados si son necesarios
}