package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffTemfr;
import com.bancolombia.pocatv.model.AtvffTemfrId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffTemfrRepository extends JpaRepository<AtvffTemfr, AtvffTemfrId>, JpaSpecificationExecutor<AtvffTemfr> {
    
    List<AtvffTemfr> findByTfresp(String responsable);
    
    Optional<AtvffTemfr> findByTfanoAndTfmesAndTfresp(Integer ano, Integer mes, String responsable);
    
    void deleteByTfresp(String responsable);
    
    Optional<AtvffTemfr> findById(AtvffTemfrId id);
    
    List<AtvffTemfr> findAll();
}