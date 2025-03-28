package com.bancolombia.pocatv.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bancolombia.pocatv.dto.UsuarioRequestDTO;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;

public interface AtvffUserService {
	
	Page<AtvffUser> getAllUsers(String xuUser,Pageable pageable);
  
    Optional<AtvffUser> findById(String xuUser);
    
    AtvffUser registrarUsuario(UsuarioRequestDTO usuarioRequest);
    
    Set<Xbknam> obtenerAreasUsuario(String userId);
    
    void deleteById(String xuUser);
    
    AtvffUser findByXuuser(String xuuser, String password);
    
}