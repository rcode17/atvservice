package com.bancolombia.pocatv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.AtvffCri;
import com.bancolombia.pocatv.model.AtvffCriId;

@Repository
public interface AtvffcriRepository extends JpaRepository<AtvffCri, AtvffCriId> {

    // Este método ya está correcto porque usa la notación "id.propiedad"
    List<AtvffCri> findByIdCranoAndIdCrmesAndIdCrcodsucAndIdCrcoprAndIdCrcodo(
            Integer crano,
            Integer crmes,
            Integer crcodsuc,
            String crcopr,
            String crcodo);

    // Estos métodos necesitan ser modificados con consultas JPQL explícitas
    @Query("SELECT c FROM AtvffCri c WHERE c.id.crano = :crano AND c.id.crmes = :crmes")
    List<AtvffCri> findByCranoAndCrmes(
            @Param("crano") Integer crano,
            @Param("crmes") Integer crmes);

    @Query("SELECT c FROM AtvffCri c WHERE c.id.crano = :crano AND c.id.crmes = :crmes AND c.id.crcodsuc = :crcodsuc")
    List<AtvffCri> findByCranoAndCrmesAndCrcodsuc(
            @Param("crano") Integer crano,
            @Param("crmes") Integer crmes,
            @Param("crcodsuc") Integer crcodsuc);
    // Corregir el método para usar la clave primaria compuesta
    Optional<AtvffCri> findByIdCranoAndIdCrmesAndCrconAndIdCrcodsucAndIdCrcoprAndIdCrcodo(
            Integer crano, Integer crmes, Integer crcon, Integer crcodsuc, String crcopr, String crcodo
    );
}