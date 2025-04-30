package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.model.Xmgreg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface XmgregRepository extends JpaRepository<Xmgreg, Long> {
    // Busca por el código de región (xmcdmr) que es un campo, no el ID
    Optional<Xmgreg> findByXmcdmr(String xmcdmr);

    List<Xmgreg> findByXmnmrContaining(String nombreParcial);

    @Query("SELECT x FROM Xmgreg x ORDER BY x.xmcdmr")
    List<Xmgreg> findAllOrderByXmcdmr();

    boolean existsByXmcdmr(String xmcdmr);
}
