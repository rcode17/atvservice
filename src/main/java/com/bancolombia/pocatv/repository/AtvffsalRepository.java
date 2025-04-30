package com.bancolombia.pocatv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffsal;
import com.bancolombia.pocatv.model.AtvffsalId;

import java.util.List;

@Repository
public interface AtvffsalRepository extends JpaRepository<Atvffsal, AtvffsalId> {
    List<Atvffsal> findBySatproAndSatdocAndSafech(String satpro, String satdoc, Long fecha);
    
    
    @Modifying
    @Query("UPDATE Atvffsal a SET a.saofco = a.saofic")
    int updateSaofcoWithSaofic();
    
}