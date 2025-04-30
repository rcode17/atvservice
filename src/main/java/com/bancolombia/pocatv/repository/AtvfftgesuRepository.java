package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffarqId;
import com.bancolombia.pocatv.model.Atvffgesu;
import com.bancolombia.pocatv.model.Atvfftgesu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AtvfftgesuRepository extends JpaRepository<Atvfftgesu, AtvffarqId> {
    @Query("SELECT r FROM Atvfftgesu r WHERE r.gsucur = ?1 AND r.gprodu = ?2 AND r.gdocum = ?3 AND r.gfearq = ?4 ORDER BY r.gcodco DESC")
    List<Atvfftgesu> findComentariosByParams(Integer codsuc, String codpro, String coddoc, Integer fechaArqueo);

    void deleteAllByGsucurAndGproduAndGdocumAndGfearq(Integer codsuc, String codpro, String coddoc, Integer fechaArqueo);

    Integer countByGsucurAndGproduAndGdocumAndGfearq(Integer codsuc, String codpro, String coddoc, Integer fechaArqueo);
   }
