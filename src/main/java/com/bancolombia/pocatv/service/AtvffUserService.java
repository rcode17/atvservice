package com.bancolombia.pocatv.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.bancolombia.pocatv.model.AtvffUser;

public interface AtvffUserService {
	
    List<AtvffUser> findAll();
    
    Optional<AtvffUser> findById(String xuUser);
    
    AtvffUser save(AtvffUser atvffUser);
    
    void deleteById(String xuUser);
    
    AtvffUser findByXuuser(String xuuser, String password);
    
    public AtvffUser updateAreaOperativa(String xuUser, BigDecimal xuArea);
}