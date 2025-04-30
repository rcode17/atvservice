package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffcrd;
import com.bancolombia.pocatv.model.AtvffcrdId;


@Repository
public interface AtvffcrdRepository extends JpaRepository<Atvffcrd, AtvffcrdId> {
    
    /**
     * Busca arqueos descuadrados por año y mes
     * @param anno Año del arqueo
     * @param mes Mes del arqueo
     * @return Lista de arqueos descuadrados
     */
    @Query("SELECT c FROM Atvffcrd c WHERE c.id.cdano = :anno AND c.id.cdmes = :mes")
    List<Atvffcrd> findByAnnoAndMes(@Param("anno") Integer anno, @Param("mes") Integer mes);
    
    /**
     * Busca arqueos descuadrados por año, mes y código de sucursal
     * @param anno Año del arqueo
     * @param mes Mes del arqueo
     * @param codsuc Código de sucursal
     * @return Lista de arqueos descuadrados
     */
    @Query("SELECT c FROM Atvffcrd c WHERE c.id.cdano = :anno AND c.id.cdmes = :mes AND c.id.cdcodsuc >= :codsuc")
    List<Atvffcrd> findByAnnoAndMesAndCodsucGreaterThanEqual(@Param("anno") Integer anno, @Param("mes") Integer mes, @Param("codsuc") Integer codsuc);
    
    /**
     * Busca arqueos descuadrados por año y mes, ordenados por código de sucursal
     * @param anno Año del arqueo
     * @param mes Mes del arqueo
     * @return Lista de arqueos descuadrados ordenados
     */
    @Query("SELECT c FROM Atvffcrd c WHERE c.id.cdano = :anno AND c.id.cdmes = :mes ORDER BY c.id.cdcodsuc")
    List<Atvffcrd> findByAnnoAndMesOrderByCodsuc(@Param("anno") Integer anno, @Param("mes") Integer mes);
}
