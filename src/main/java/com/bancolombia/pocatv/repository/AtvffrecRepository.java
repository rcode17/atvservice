package com.bancolombia.pocatv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffrec;

@Repository
public interface AtvffrecRepository extends JpaRepository<Atvffrec, String> {
	 Page<Atvffrec> findByRcfere(String rcfere, Pageable pageable);
}
