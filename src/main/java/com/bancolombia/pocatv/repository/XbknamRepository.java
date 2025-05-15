package com.bancolombia.pocatv.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Xbknam;

@Repository
public interface XbknamRepository extends JpaRepository<Xbknam, BigDecimal>,JpaSpecificationExecutor<Xbknam> {
	 Optional<Xbknam> findByXnnmky(Integer xnnmky);
	 List<Xbknam> findByXncdmr(String xncdmr);
	 List<Xbknam> findAll();
	 
	 @Query(value = "SELECT DISTINCT nam.* FROM xbknam nam JOIN atvffpds pds ON nam.xnnmky = pds.xscosu order by nam.xnnmky", nativeQuery = true)
	 List<Xbknam> findDistinctAreasWithActiveProducts();
}
 
