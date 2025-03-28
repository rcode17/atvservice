package com.bancolombia.pocatv.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Xbknam;

@Repository
public interface XbknamRepository extends JpaRepository<Xbknam, BigDecimal>,JpaSpecificationExecutor<Xbknam> {

}
 
