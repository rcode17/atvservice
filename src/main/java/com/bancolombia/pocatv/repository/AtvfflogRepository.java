package com.bancolombia.pocatv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.bancolombia.pocatv.model.Atvfflog;

@Repository
public interface AtvfflogRepository  extends JpaRepository<Atvfflog, Long> { 
	

}
