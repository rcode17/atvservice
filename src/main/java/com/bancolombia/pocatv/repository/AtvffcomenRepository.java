package com.bancolombia.pocatv.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffcomen;

@Repository
public interface AtvffcomenRepository extends JpaRepository<Atvffcomen, Integer> {
    
    @Query("SELECT COALESCE(MAX(a.comcon), 0) FROM Atvffcomen a")
    Integer findMaxConsecutivo();
}