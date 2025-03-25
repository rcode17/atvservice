package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bancolombia.pocatv.service.AtvffUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AtvffUserServiceImpl implements AtvffUserService {

    @Autowired
    private AtvffUserRepository atvffUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
 
    @Override
    public List<AtvffUser> findAll() {
        return atvffUserRepository.findAll();
    }

    @Override
    public Optional<AtvffUser> findById(String xuUser) {
        return atvffUserRepository.findById(xuUser);
    }

    @Override
    public AtvffUser save(AtvffUser atvffUser) {
        return atvffUserRepository.save(atvffUser);
    }

    @Override
    public void deleteById(String xuUser) {
        atvffUserRepository.deleteById(xuUser);
    }

	@Override
	public AtvffUser findByXuuser(String xuuser, String password) {

		AtvffUser user = atvffUserRepository.findByXuUser(xuuser);

        if (user == null) {
        	 throw new IllegalArgumentException("No se encontraron registros para el usuario: " + xuuser);
        }
        
        String clave=passwordEncoder.encode("atv_2025");
        System.out.println("clave"+clave);

        if (!passwordEncoder.matches(password, user.getXuPass())) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos: " + xuuser);
        }

		return user;
	}

	@Override
	public AtvffUser updateAreaOperativa(String xuUser, BigDecimal xuArea) {
		Optional<AtvffUser> optUsuario = atvffUserRepository.findById(xuUser);
        if (optUsuario.isPresent()) {
        	AtvffUser usuario = optUsuario.get();
            usuario.setXuArea(xuArea);
            return atvffUserRepository.save(usuario);
        }
        return null;
	} 

}