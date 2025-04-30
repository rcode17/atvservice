package com.bancolombia.pocatv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvlfarq;
import com.bancolombia.pocatv.model.AtvlfarqId;

@Repository
public interface AtvlfarqRepository extends JpaRepository<Atvlfarq, AtvlfarqId> {
    Atvlfarq findBySaofcoAndSatproAndSatdocAndSafech(
        String saofco, String satpro, String satdoc, String safech);
}