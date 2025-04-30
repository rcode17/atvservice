package com.bancolombia.pocatv.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffchsal;
import com.bancolombia.pocatv.model.AtvffchsalId;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffchsalRepository extends JpaRepository<Atvffchsal, AtvffchsalId> {
    List<Atvffchsal> findBySdeofic(String sdeofic);
    List<Atvffchsal> findBySdtdoc(String sdtdoc);
    
    Optional<Atvffchsal> findBySdeofcoAndSdtproAndSdtdocAndSdfech(
            String sdeofco, String sdtpro, String sdtdoc, String sdfech);
    
    List<Atvffchsal> findAll();
}