package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.AtvffarqId;
import com.bancolombia.pocatv.model.Atvfffrein;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface AtvfffreinRepository extends JpaRepository<Atvfffrein, String> {

    // Métodos personalizados para consultas específicas
    List<Atvfffrein> findByAreaContaining(String area);

    List<Atvfffrein> findByMessubfAndAnosubf(Integer mes, Integer ano);
    
    List<Atvfffrein> findByAnosubfAndMessubf(Integer ano, Integer mes);
    
    List<Atvfffrein> findByResp(String responsable);
    
    void deleteByResp(String responsable);
    
    Optional<Atvfffrein> findById(String id);
    
    List<Atvfffrein> findAll();
}