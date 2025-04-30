package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffapa;
import com.bancolombia.pocatv.model.AtvffapaId;

@Repository
public interface AtvffapaRepository extends JpaRepository<Atvffapa, AtvffapaId> {
    
    List<Atvffapa> findByApano(Integer ano);
    
    List<Atvffapa> findByApanoAndApmes(Integer ano, Integer mes);
    
    List<Atvffapa> findByApanoAndApmesAndApcodsuc(Integer ano, Integer mes, Integer codsuc);
    
    @Query("SELECT a FROM Atvffapa a WHERE a.apcodsuc = :codsuc")
    List<Atvffapa> findByCodsuc(@Param("codsuc") Integer codsuc);
}