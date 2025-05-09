package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffTemf1;
import com.bancolombia.pocatv.model.AtvffTemf1Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffTemf1Repository extends JpaRepository<AtvffTemf1, AtvffTemf1Id>, JpaSpecificationExecutor<AtvffTemf1> {
    
    List<AtvffTemf1> findByTfresp1(String responsable);
    
    List<AtvffTemf1> findByTfano1AndTfmes1(Integer ano, Integer mes);
    
    List<AtvffTemf1> findByTfano1AndTfmes1AndTfsuc1(Integer ano, Integer mes, Integer sucursal);
    
    Optional<AtvffTemf1> findById(AtvffTemf1Id id);
    
    List<AtvffTemf1> findAll();
}