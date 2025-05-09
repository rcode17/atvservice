package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.AtvffIap;
import com.bancolombia.pocatv.model.AtvffIap.AtvffIapId;

@Repository
public interface AtvffIapRepository extends JpaRepository<AtvffIap, AtvffIapId> {
    
    @Query("SELECT a FROM AtvffIap a WHERE a.id.ipAnos = :ano ORDER BY a.id.ipCodpro, a.id.ipCoddoc")
    List<AtvffIap> findByAnoOrderByCodproAndCoddoc(@Param("ano") Integer ano);
    
    // Método corregido para buscar por las propiedades de la clave primaria compuesta
    AtvffIap findByIdIpAnosAndIdIpCodproAndIdIpCoddoc(int ipAnos, int ipCodpro, int ipCoddoc);
}
