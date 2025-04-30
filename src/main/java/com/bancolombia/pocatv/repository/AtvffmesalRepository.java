package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffmesal;
import com.bancolombia.pocatv.model.AtvffmesalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtvffmesalRepository extends JpaRepository<Atvffmesal, AtvffmesalId> {
    Atvffmesal findBySdeoficAndSdtdoc(String sdeofic, String sdtdoc);
}