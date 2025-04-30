package com.bancolombia.pocatv.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffrec;

@Repository
public interface AtvffrecRepository extends JpaRepository<Atvffrec, String> {
	 Page<Atvffrec> findByRcfere(String rcfere, Pageable pageable);
	 
	 // Método para encontrar registros por código de sucursal
    List<Atvffrec> findByRcsuc(String rcsuc);
    
    // Método para encontrar registros por fecha
    List<Atvffrec> findByRcfear(Date rcfear);
    
    List<Atvffrec> findByRcfearBetweenOrderByRcfear(Date  fechaInicio, Date  fechaFin);
    
    void delete( Atvffrec rechazo);

}
