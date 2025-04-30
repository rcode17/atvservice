package com.bancolombia.pocatv.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bancolombia.pocatv.model.Atvffsal1;
import com.bancolombia.pocatv.model.Atvffsal1Id;


public interface Atvffsal1Repository extends JpaRepository<Atvffsal1, Atvffsal1Id> {
	
    List<Atvffsal1> findBySaofcoAndSatproAndSatdocAndSafech(
            Integer saofco, String satpro, String satdoc, Integer safech);
    
    @Query("SELECT SUM(a.sadisp) FROM Atvffsal1 a WHERE a.saofco = :saofco AND a.satpro = :satpro AND a.satdoc = :satdoc AND a.safech = :safech")
    BigDecimal sumSadisp(@Param("saofco") Integer saofco, @Param("satpro") String satpro, @Param("satdoc") String satdoc, @Param("safech") Integer safech);
    
    List<Atvffsal1> findBySatproAndSatdoc(String satpro, String satdoc);

}
