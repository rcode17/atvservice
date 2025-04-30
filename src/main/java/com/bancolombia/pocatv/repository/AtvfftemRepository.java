package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvfftem;
import java.util.Date;


@Repository
public interface AtvfftemRepository extends JpaRepository<Atvfftem, Long> {
    // Método para encontrar registros por sucursal
    List<Atvfftem> findByTmsuc(String tmsuc);
    
    // Método para encontrar registros por fecha
    List<Atvfftem> findByTmfear(String tmfear);
    
    // Método para contar registros por estado
    long countByTmres(String tmres);
}