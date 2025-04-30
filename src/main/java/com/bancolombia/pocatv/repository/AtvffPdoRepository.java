package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AtvffPdoRepository extends JpaRepository<AtvffPdo, AtvffPdoId>,JpaSpecificationExecutor<AtvffPdo>{
	AtvffPdo findByXpcoprAndXpcodo(String xpcopr, String xpcodo);

	Optional<AtvffPdo> findById(AtvffPdoId id);

	List<AtvffPdo> findAll();
	 
	 
}
