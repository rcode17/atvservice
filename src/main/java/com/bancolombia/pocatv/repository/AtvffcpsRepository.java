package com.bancolombia.pocatv.repository;


import com.bancolombia.pocatv.dto.atvffcpsfechaResponseDTO;
import com.bancolombia.pocatv.model.Atvffcps;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AtvffcpsRepository extends JpaRepository<Atvffcps, Integer> {


    @Query(value ="SELECT f.cscodsuc, f.csnomsuc, f.cscumpli, f.cscalid FROM public.atvffcps f WHERE f.cscopr=:cscopr AND f.cscodo =:cscodo", nativeQuery = true)
    List<atvffcpsfechaResponseDTO> findDescriptionsByYearAndMonth(@Param("cscopr") String cscopr, @Param("cscodo") String cscodo);
}