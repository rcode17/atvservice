package com.bancolombia.pocatv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.AtvffUser;

@Repository
public interface AtvffUserRepository extends JpaRepository<AtvffUser, String>, JpaSpecificationExecutor<AtvffUser> {
	 AtvffUser findByXuUser(String xuUser);
}