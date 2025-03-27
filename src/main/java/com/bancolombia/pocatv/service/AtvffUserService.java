package com.bancolombia.pocatv.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bancolombia.pocatv.dto.UsuarioRequestDTO;
import com.bancolombia.pocatv.dto.UsuarioResponseDTO;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;

public interface AtvffUserService {
	
    List<UsuarioResponseDTO> findAll();
    
    Optional<AtvffUser> findById(String xuUser);
    
    AtvffUser registrarUsuario(UsuarioRequestDTO usuarioRequest);
    
    Set<Xbknam> obtenerAreasUsuario(String userId);
    
    void deleteById(String xuUser);
    
    AtvffUser findByXuuser(String xuuser, String password);
    
}