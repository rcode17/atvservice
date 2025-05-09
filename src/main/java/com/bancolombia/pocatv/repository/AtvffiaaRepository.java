package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffcaa;
import com.bancolombia.pocatv.model.Atvffiaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AtvffiaaRepository extends JpaRepository<Atvffiaa, Integer> {

    List<Atvffiaa> findByIaano(Integer iaano);
    void deleteByIaano(Integer iaano);
    
    @Query("SELECT a FROM Atvffiaa a WHERE a.iaano = :ano AND a.iaregion = :region")
    Atvffiaa findByAnoAndRegion(@Param("ano") Integer ano, @Param("region") String region);
}