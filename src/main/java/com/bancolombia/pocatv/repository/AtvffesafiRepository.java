package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffesafi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AtvffesafiRepository extends JpaRepository<Atvffesafi, Long> {
    /**
     * Método para buscar un registro por código de oficina.
     */
    Optional<Atvffesafi> findBySaofco(Long saofco);
}
