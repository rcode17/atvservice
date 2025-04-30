package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvfffil;
import com.bancolombia.pocatv.model.AtvffilId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtvffilRepository extends JpaRepository<Atvfffil, AtvffilId> {
}