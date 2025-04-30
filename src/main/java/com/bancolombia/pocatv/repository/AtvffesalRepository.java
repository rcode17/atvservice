package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffesal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AtvffesalRepository extends JpaRepository<Atvffesal, Long> {
    /**
     * Método para obtener todos los registros de la tabla atvffesal.
     */
    List<Atvffesal> findAll();

    /**
     * Método para buscar registros por código de oficina.
     */
    List<Atvffesal> findBySaofco(Long saofco);
}
