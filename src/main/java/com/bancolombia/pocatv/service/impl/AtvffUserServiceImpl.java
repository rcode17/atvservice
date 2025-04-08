package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.UsuarioProductoDTO;
import com.bancolombia.pocatv.dto.UsuarioRequestDTO;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.specification.AtvffUserSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AtvffUserServiceImpl implements AtvffUserService {

    @Autowired
    private AtvffUserRepository atvffUserRepository;
    
    @Autowired
    private AtvffPdoRepository atvffPdoRepository;
    
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
                    .orElseThrow(() -> new IllegalArgumentException("Área no encontrada: " + areaId));
                user.addArea(area);
            });
        }

        return atvffUserRepository.save(user);
	}

	@Override
	public Set<Xbknam> obtenerAreasUsuario(String userId) {
		AtvffUser user = atvffUserRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + userId));
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
	
	@Override
    public AtvffUser addAreasUser(String xuUser, List<BigDecimal> areaIds) {
    
        AtvffUser user = atvffUserRepository.findById(xuUser)
                            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + xuUser));

        // Limpiar las áreas actuales del usuario.
        // Esto es opcional, dependiendo de si deseas reemplazar todas las áreas o solo agregar nuevas.
        //user.getXuArea().clear();
       
        if (areaIds != null) {
            areaIds.forEach(areaId -> {
                
                Xbknam area = areaRepository.findById(areaId)
                        .orElseThrow(() -> new IllegalArgumentException("Área no encontrada: " + areaId));

                user.addArea(area);
            });
        }

        return atvffUserRepository.save(user);
    }
	
	@Override
	public AtvffUser deleteAreasUser(String xuUser, List<BigDecimal> areaIds) {
	    
	    AtvffUser user = atvffUserRepository.findById(xuUser)
	                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + xuUser));
	    
	    if (areaIds != null) {
	        areaIds.forEach(areaId -> {
	            
	            Xbknam area = areaRepository.findById(areaId)
	                    .orElseThrow(() -> new IllegalArgumentException("Área no encontrada: " + areaId));
	            
	            user.getXuArea().remove(area);
	        });
	    }
	    
	    return atvffUserRepository.save(user);
	}


	@Override
	public AtvffUser addProductoUser(String xuUser, List<UsuarioProductoDTO> productosRequest) {
		
        AtvffUser user = atvffUserRepository.findById(xuUser)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + xuUser));

        for (UsuarioProductoDTO pr : productosRequest) {
            AtvffPdo producto = atvffPdoRepository.findById(new AtvffPdoId(pr.getXpcopr(), pr.getXpcodo()))
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con clave: " 
                                    + pr.getXpcopr() + ", " + pr.getXpcodo()));
            user.addProducto(producto);
        }
        return atvffUserRepository.save(user);
	}


	@Override
	public AtvffUser deleteProductoUser(String xuUser, List<UsuarioProductoDTO> productosRequest) {
		
        AtvffUser user = atvffUserRepository.findById(xuUser)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + xuUser));

        for (UsuarioProductoDTO pr : productosRequest) {
            AtvffPdo producto = atvffPdoRepository.findById(new AtvffPdoId(pr.getXpcopr(), pr.getXpcodo()))
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con clave: " 
                                    + pr.getXpcopr() + ", " + pr.getXpcodo()));
            user.removeProducto(producto);
        }
        return atvffUserRepository.save(user);
    }


	@Override
	public Set<AtvffPdo> getProductosByUser(String xuUser) {
		 AtvffUser user = atvffUserRepository.findById(xuUser)
	                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + xuUser));
	        return user.getXuCopr();
	}

}