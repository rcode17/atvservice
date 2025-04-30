package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.repository.AtvffsalRepository;
import com.bancolombia.pocatv.service.AtvffsalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtvffsalServiceImpl implements AtvffsalService {
    
    private final AtvffsalRepository atvffsalRepository;
    
    @Autowired
    public AtvffsalServiceImpl(AtvffsalRepository atvffsalRepository) {
        this.atvffsalRepository = atvffsalRepository;
    }
    
    @Override
    @Transactional
    public int updateSaofcoWithSaofic() {
        try {
            int updatedRecords = atvffsalRepository.updateSaofcoWithSaofic();
            return updatedRecords;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar los registros: " + e.getMessage());
        }
    }
}