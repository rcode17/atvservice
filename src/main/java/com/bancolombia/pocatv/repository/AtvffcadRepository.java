package com.bancolombia.pocatv.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffcad;
import com.bancolombia.pocatv.model.AtvffcadId;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffcadRepository extends JpaRepository<Atvffcad, AtvffcadId> {
    Optional<Atvffcad> findByCaanoAndCamesAndCadiaAndCacoprAndCacodo(
            Integer caano, Integer cames, Integer cadia, String cacopr, String cacodo);
    
    List<Atvffcad> findByCaanoAndCamesAndCadia(Integer ano, Integer mes, Integer dia);
    
    @Query("SELECT a FROM Atvffcad a WHERE a.caano = ?1 AND a.cames = ?2 AND a.cadia = ?3 ORDER BY a.cacopr, a.cacodo")
    List<Atvffcad> findConciliacionesByFecha(Integer ano, Integer mes, Integer dia);
}