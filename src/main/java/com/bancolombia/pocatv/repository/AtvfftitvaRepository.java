package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvfftitva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtvfftitvaRepository extends JpaRepository<Atvfftitva, Long> {
}
