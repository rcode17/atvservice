package com.bancolombia.pocatv.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffchars;

@Repository
public interface AtvffcharsRepository extends JpaRepository<Atvffchars, String> {
}
