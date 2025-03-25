package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Dominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DominioRepository extends JpaRepository<Dominio, Integer> {
}
