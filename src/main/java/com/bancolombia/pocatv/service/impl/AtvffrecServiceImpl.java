package com.bancolombia.pocatv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.AtvffrecResponseDTO;
import com.bancolombia.pocatv.model.Atvffrec;
import com.bancolombia.pocatv.repository.AtvffrecRepository;
import com.bancolombia.pocatv.service.AtvffrecService;

@Service
public class AtvffrecServiceImpl implements AtvffrecService {

    @Autowired
    private AtvffrecRepository repository;

    @Override
    public Page<AtvffrecResponseDTO> findByFechaRechazo(String fecha, Pageable pageable) {
        Page<Atvffrec> pageEntities = repository.findByRcfere(fecha, pageable);
        
        // Mapear cada entidad a un DTO
        return pageEntities.map(entity -> {
        	AtvffrecResponseDTO dto = new AtvffrecResponseDTO();
            dto.setRcfere(entity.getRcfere());
            dto.setRcfear(entity.getRcfear());
            dto.setRcsuc(entity.getRcsuc());
            dto.setRccdsu(entity.getRccdsu());
            dto.setRccdsuf(entity.getRccdsuf());
            dto.setRccedcn(entity.getRccedcn());
            dto.setRcrech(entity.getRcrech());
            return dto;
        });
    }
}
