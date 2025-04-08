package com.bancolombia.pocatv.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Atvffoas;
import com.bancolombia.pocatv.model.AtvffoasId;
import com.bancolombia.pocatv.repository.AtvffoasRepository;
import com.bancolombia.pocatv.service.AtvffPdsService;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.service.AtvffoasService;

@Service
public class AtvffoasServiceImpl implements AtvffoasService {

    @Autowired
    private AtvffoasRepository atvffoasRepository;
    
    @Autowired
    private AtvffUserService userService;
    
    @Autowired
    private AtvffPdsService pdsService;

    @Override
    public Page<Atvffoas> findAll(Pageable pageable) {
        return atvffoasRepository.findAll(pageable);
    }

    @Override
    public Page<Atvffoas> findByAnoMes(Integer oaano, Integer oames, Pageable pageable) {
        return atvffoasRepository.findByIdOaanoAndIdOames(oaano, oames, pageable);
    }

    @Override
    public Page<Atvffoas> getRegistrosArchivoProducto(String xuUser, Integer oaano, Integer oames, Integer oaxnnmky, Pageable pageable) {
        
    	Optional<AtvffUser> usuario = userService.findById(xuUser);
    	
    	
    	pdsService.findByIdXsCosu(oaxnnmky);
    	
    	if (!usuario.isPresent()) {
            usuario.get().getXuDom();
            usuario.get().getXuArea();
           
        }
    	
    	// Si se proporciona el valor de oaxnnmky, se consulta por año, mes y oaxnnmky
        if (oaxnnmky != null) {
            return atvffoasRepository.findByIdOaanoAndIdOamesAndIdOaxnnmky(oaano, oames, oaxnnmky, pageable);
        } else {
            // En caso contrario se devuelve solo la lista filtrada por año y mes
            return atvffoasRepository.findByIdOaanoAndIdOames(oaano, oames, pageable);
        }
    }
    

    @Override
    public Page<Atvffoas> buscarPorUsuarioAnoMes(String usuario, Integer ano, Integer mes, Pageable pageable) {
        return atvffoasRepository.findPorUsuarioAnoMes(usuario, ano, mes, pageable);
    }
    
    


}