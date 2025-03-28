package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffPdsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtvffPdsRepository extends JpaRepository<AtvffPds, AtvffPdsId> {

}
