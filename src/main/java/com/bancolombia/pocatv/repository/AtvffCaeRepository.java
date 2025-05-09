package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffCae;
import com.bancolombia.pocatv.model.AtvffCaeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AtvffCaeRepository extends JpaRepository<AtvffCae, AtvffCaeId> {
    
    List<AtvffCae> findByCamesAndCaano(Integer cames, Integer caano);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM AtvffCae a WHERE a.cames = :mes AND a.caano = :ano")
    void deleteByCamesAndCaano(@Param("mes") Integer mes, @Param("ano") Integer ano);
}