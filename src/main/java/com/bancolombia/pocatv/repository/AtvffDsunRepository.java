package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffDsun;
import com.bancolombia.pocatv.model.AtvffDsunId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffDsunRepository extends JpaRepository<AtvffDsun, AtvffDsunId>, JpaSpecificationExecutor<AtvffDsun> {
    
    @Query("SELECT d FROM AtvffDsun d WHERE CONCAT(d.dnano, FUNCTION('LPAD', CAST(d.dnmes AS string), 2, '0')) >= :fechaInicio AND CONCAT(d.dnano, FUNCTION('LPAD', CAST(d.dnmes AS string), 2, '0')) <= :fechaFin ORDER BY d.dnano, d.dnmes, d.dnxnnmky")
    List<AtvffDsun> findByFechaRange(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
    
    List<AtvffDsun> findByDnanoAndDnmes(Integer ano, Integer mes);
    
    List<AtvffDsun> findByDnxnnmky(Integer sucursal);
    
    Optional<AtvffDsun> findById(AtvffDsunId id);
    
    List<AtvffDsun> findAll();
    
    // Buscar por año, mes y sucursal utilizando una consulta personalizada
    @Query("SELECT d FROM AtvffDsun d WHERE d.dnano = :dnano AND d.dnmes = :dnmes AND d.dnxnnmky = :dnxnnmky")
    AtvffDsun findByDnanoAndDnmesAndDnxnnmky(@Param("dnano") Integer dnano, @Param("dnmes") Integer dnmes, @Param("dnxnnmky") Integer dnxnnmky);
    
    // Eliminar por clave compuesta completa
    void deleteById(AtvffDsunId id);

    // Eliminar registros utilizando dnano y dnmes
    @Modifying
    @Transactional
    @Query("DELETE FROM AtvffDsun d WHERE d.dnano = :dnano AND d.dnmes = :dnmes")
    void deleteByDnanoAndDnmes(@Param("dnano") Integer dnano, @Param("dnmes") Integer dnmes);
}