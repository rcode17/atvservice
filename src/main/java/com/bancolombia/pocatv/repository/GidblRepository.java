package com.bancolombia.pocatv.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Gidbl;

@Repository
public interface GidblRepository extends JpaRepository<Gidbl, Long> {
    
	 @Query(value ="SELECT g FROM Gidbl g WHERE g.gxnobr = :gxnobr AND g.gxnoac = :gxnoac AND g.gxdtdy = :gxdtdy AND g.gxfccd = :gxfccd", nativeQuery = true)
	    List<Gidbl> buscarPorOficinaYCuentaYFechaYEstado(
	            @Param("gxnobr") Integer gxnobr, 
	            @Param("gxnoac") Long gxnoac, 
	            @Param("gxdtdy") LocalDate gxdtdy, 
	            @Param("gxfccd") String gxfccd);
	    
	    @Query(value = "SELECT SUM(GXAMDT) FROM GIDBL WHERE GXNOBR = :gxnobr AND GXNOAC = :gxnoac  AND GXDTDY = :gxdtdy AND GXFAC ='Y'", nativeQuery = true)
	    BigDecimal sumSaldoContablePorOficinaYCuentaYFecha(
	    		String gxnobr, String gxnoac, LocalDate gxdtdy);
}
