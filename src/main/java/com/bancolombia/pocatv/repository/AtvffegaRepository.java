package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffega;
import com.bancolombia.pocatv.model.AtvffegaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtvffegaRepository extends JpaRepository<Atvffega, AtvffegaId> {
    
    @Query("SELECT a FROM Atvffega a WHERE a.egmesin = :mes AND a.eganoin = :anio " +
           "ORDER BY a.egsucursal")
    List<Atvffega> findByMesAndAnio(@Param("mes") Integer mes, @Param("anio") Integer anio);
    
    @Query(value = "SELECT eg.* FROM atvffega eg INNER JOIN atvffuser_xbknam ux ON ux.xnnmky = eg.egsucursal WHERE ux.xuuser = :usuario and eg.egmesin = :mes AND eg.eganoin = :anio", nativeQuery = true)
    List<Atvffega> findByMesAnioAndUsuario(
            @Param("mes") Integer mes, 
            @Param("anio") Integer anio, 
            @Param("usuario") String usuario);

    void deleteByEgmesinAndEganoin(Integer egmesin, Integer eganoin);
}
