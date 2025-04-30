package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffPdsId;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtvffPdsRepository extends JpaRepository<AtvffPds, AtvffPdsId> {
	
	Page<AtvffPds> findByXscoprAndXscodo(String xsCopr, String xsCodo, Pageable pageable);
	//List<AtvffPds> findByIdXscosu(Integer xscosu);

	@Query("SELECT p FROM AtvffPds p WHERE p.xscosu = :xscosu")
	List<AtvffPds> findByIdXscosu(@Param("xscosu") Integer xscosu);


	AtvffPds findByXscoprAndXscodo(String xscopr, String xscodo);
	
    Optional<AtvffPds> findByXscosuAndXscoprAndXscodo(
            Integer xscosu, 
            String xscopr, 
            String xscodo
        );
    
   
 
    
    

}
