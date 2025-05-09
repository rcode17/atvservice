package com.bancolombia.pocatv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Gidbl;
import com.bancolombia.pocatv.model.Gidbll;
import com.bancolombia.pocatv.model.GidbllId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface GidbllRepository extends JpaRepository<Gidbll, GidbllId> {
    List<Gidbll> findByGxdtdyAndGxnoac(LocalDate gxdtdy, String gxnoac);
    Gidbll findByGxamdtAndGxnoacAndGxdtdy(BigDecimal gxamdt, String gxnoac, LocalDate gxdtdy);
}