package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffesal1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Atvffesal1Repository extends JpaRepository<Atvffesal1, Long> {
    /**
     * Método para buscar un registro por código de oficina.
     */
    Optional<Atvffesal1> findBySaofco(Long saofco);
}