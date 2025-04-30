package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.AtvffarqId;
import com.bancolombia.pocatv.model.Atvffgesu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface AtvffgesuRepository extends JpaRepository<Atvffgesu, AtvffarqId> {
    List<Atvffgesu> findByCodsucAndCodproAndCoddocAndAqfear(
            Integer codsuc, String codpro, String coddoc, String aqfear);
   }
