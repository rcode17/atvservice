package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.AtvffCri;
import com.bancolombia.pocatv.model.AtvffCriId;



@Repository
public interface AtvffcriRepository extends JpaRepository<AtvffCri, AtvffCriId> {

    List<AtvffCri> findByIdCranoAndIdCrmesAndIdCrcodsucAndIdCrcoprAndIdCrcodo(
            Integer crano,
            Integer crmes,
            Integer crcodsuc,
            String crcopr,
            String crcodo);
    
}
