package com.bancolombia.pocatv.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.AtvffUser;

@Repository
public interface AtvffUserRepository extends JpaRepository<AtvffUser, String>, JpaSpecificationExecutor<AtvffUser> {
	 AtvffUser findByXuUser(String xuUser);
	 // Buscar usuario que tenga acceso a un producto específico
	    @Query("SELECT u FROM AtvffUser u JOIN u.xuCopr p WHERE u.xuUser = :xuUser AND p.xpcopr = :xuCopr")
	    Optional<AtvffUser> findUserWithProductAccess(@Param("xuUser") String xuUser, @Param("xuCopr") String xuCopr);

}