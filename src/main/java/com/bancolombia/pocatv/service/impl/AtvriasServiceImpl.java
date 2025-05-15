package com.bancolombia.pocatv.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.AtvriasService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AtvriasServiceImpl implements AtvriasService{
	
	 @Autowired
	 private AtvffUserRepository atvffUserRepository;
	 
	 @Autowired
	 private AtvffPdsRepository atvffPdsRepository;
	 
	 @Autowired
	 private XbknamRepository xbknamRepository;

	@Override
	public Page<Xbknam> obtenerAreasPorUsuarioYAno(String usuario, Integer ano, Pageable pageable) {
		
		List<Xbknam> areasResultado = new ArrayList<>();
		Optional<AtvffUser> userAtv = atvffUserRepository.findById(usuario);
		
		if (!userAtv.isPresent()) {
            // Si no existe el usuario, retornamos lista vacía
            throw new IllegalArgumentException("No se encontró el usuario con código: " + usuario);
        }
		
		 AtvffUser usuarioInfo = userAtv.get();
	        
        // Obtenemos el valor de xuDom directamente del usuario
        String xuDom = usuarioInfo.getXuDom();
        
        // Según el valor de xuDom, ejecutamos una consulta u otra
        if ("03".equals(xuDom)) {
            // Si xuDom = 03, traemos todas las áreas con productos activos
        	areasResultado = xbknamRepository.findDistinctAreasWithActiveProducts();
        	
        } else {
            // Si xuDom es diferente de 03, solo traemos las áreas del usuario
        	Set<Xbknam> areas = usuarioInfo.getXuArea();
        	for (Xbknam area : areas) {
                Integer xnnmky = area.getXnnmky(); // Asegúrate de que este método existe en Xbknam
                List<AtvffPds> pdtAct = atvffPdsRepository.findByIdXscosu(xnnmky);

                if (pdtAct.isEmpty()) {
                	//No hay resultados para esa área
                	continue;
                } else {
                    // Realiza la acción si la lista contiene elementos
                	areasResultado.add(area);
                    System.out.println("Se encontraron resultados para xnnmky: " + xnnmky);
                }
            }
        }
        // Convertimos la lista a un Page utilizando el Pageable
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), areasResultado.size());
		return new PageImpl<>(areasResultado.subList(start, end), pageable, areasResultado.size());
	}

}
