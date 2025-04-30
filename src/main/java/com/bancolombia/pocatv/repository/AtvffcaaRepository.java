package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffcaa;
import com.bancolombia.pocatv.model.Xmgreg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AtvffcaaRepository extends JpaRepository<Atvffcaa, Long> {

    /**
     * Busca todos los registros para un año específico
     */
    List<Atvffcaa> findByAaanos(Integer aaanos);

    /**
     * Busca registros por año y región
     */
    List<Atvffcaa> findByAaanosAndAaregion(Integer aaanos, String aaregion);

    /**
     * Busca un registro específico por año y región
     */
    Optional<Atvffcaa> findFirstByAaanosAndAaregion(Integer aaanos, String aaregion);

    /**
     * Elimina todos los registros para un año específico
     */
    void deleteByAaanos(Integer aaanos);

    /**
     * Obtiene el promedio anual para todas las regiones en un año específico
     */
    @Query("SELECT AVG(a.aaproma) FROM Atvffcaa a WHERE a.aaanos = :ano")
    Double findAveragePromaByAaanos(@Param("ano") Integer ano);

    /**
     * Obtiene las regiones con mejor cumplimiento para un año específico
     */
    @Query("SELECT a FROM Atvffcaa a WHERE a.aaanos = :ano ORDER BY a.aaproma DESC")
    List<Atvffcaa> findTopRegionsByAaanos(@Param("ano") Integer ano);

    /**
     * Verifica si existen datos para un año específico
     */
    boolean existsByAaanos(Integer aaanos);
}