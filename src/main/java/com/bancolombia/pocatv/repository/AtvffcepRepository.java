package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.dto.atvffcepfechaResponseDTO;

import com.bancolombia.pocatv.model.Atvffcep;

import jakarta.transaction.Transactional;

@Repository
public interface AtvffcepRepository extends JpaRepository<Atvffcep, Integer> {
	

    @Query(value ="SELECT f.cpcopr, f.cpcodo, p.xpdsdo, f.cpcumpli, f.cpcalid FROM public.atvffcep f JOIN public.atvffpdo p ON f.cpcopr = p.xpcopr AND f.cpcodo = p.xpcodo WHERE f.cpano=:cpano AND f.cpmes =:cpmes", nativeQuery = true)
    List<atvffcepfechaResponseDTO> findDescriptionsByYearAndMonth(@Param("cpano") Integer cpano, @Param("cpmes") Integer cpmes);
    
    
    
    List<Atvffcep> findByCpmesAndCpano(Integer mes, Integer ano);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Atvffcep c WHERE c.cpmes = :mes AND c.cpano = :ano")
    void deleteByCpmesAndCpano(@Param("mes") Integer mes, @Param("ano") Integer ano);
}
   

