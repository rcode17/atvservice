package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.UsuarioRequestDTO;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.specification.AtvffUserSpecification;

import java.util.Optional;
import java.util.Set;

@Service
public class AtvffUserServiceImpl implements AtvffUserService {

    @Autowired
    private AtvffUserRepository atvffUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private  XbknamRepository areaRepository;
   

    @Override
    public Optional<AtvffUser> findById(String xuUser) {
        return atvffUserRepository.findById(xuUser);
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
	public AtvffUser registrarUsuario(UsuarioRequestDTO usuarioRequest) {
		
        AtvffUser user = new AtvffUser();
        user.setXuUser(usuarioRequest.getXuUser());
        user.setXuName(usuarioRequest.getXuName());
        user.setXuCarg(usuarioRequest.getXuCarg());
        //user.setXuArea(usuarioRequest.getXuArea());
        user.setXuAcce(usuarioRequest.getXuAcce());
        user.setXuDom(usuarioRequest.getXuDom());
        user.setXuUsrt(usuarioRequest.getXuUsrt());
        user.setXuPass(usuarioRequest.getXuPass());
       
        if (usuarioRequest.getXuArea() != null) {
        	usuarioRequest.getXuArea().forEach(areaId -> {
               
                Xbknam area = areaRepository.findById(areaId)
                    .orElseThrow(() -> new RuntimeException("Área no encontrada: " + areaId));
                user.addArea(area);
            });
        }

        return atvffUserRepository.save(user);
	}

	@Override
	public Set<Xbknam> obtenerAreasUsuario(String userId) {
		AtvffUser user = atvffUserRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));
	        return user.getXuArea();
	    }

	
	@Override
	public Page<AtvffUser> getAllUsers(String xuUser, Pageable pageable) {
        Specification<AtvffUser> spec = Specification.where(null);

        if (xuUser != null && !xuUser.isEmpty()) {
            spec = spec.and(AtvffUserSpecification.filterByUsername(xuUser));
        }

        return atvffUserRepository.findAll(spec, pageable);
    }
	

}