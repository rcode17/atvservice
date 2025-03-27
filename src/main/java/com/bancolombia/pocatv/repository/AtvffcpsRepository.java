package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancolombia.pocatv.model.Atvffcps;

public interface AtvffcpsRepository extends JpaRepository<Atvffcps, Integer> {
	List<Atvffcps> findByYearAndMonth(Integer year, Integer month);
}