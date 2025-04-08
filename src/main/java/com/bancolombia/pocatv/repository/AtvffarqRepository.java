package com.bancolombia.pocatv.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.AtvffarqId;


@Repository
public interface AtvffarqRepository extends JpaRepository<Atvffarq, AtvffarqId> {
    // Método para consultar un registro por los campos aqcdsu, aqcopr, aqcodo y aqfear
    Optional<Atvffarq> findByAqcdsuAndAqcoprAndAqcodoAndAqfear(
            Integer aqcdsu, 
            String aqcopr, 
            String aqcodo, 
            String aqfear);
    
    
    List<Atvffarq> findByAqcdsuAndAqcoprAndAqcodoAndAqfearAndAqcedan(
            Integer aqcdsu, 
            String aqcopr, 
            String aqcodo, 
            String aqfear, 
            String aqcedan);
   


   }
