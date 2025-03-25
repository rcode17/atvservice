package com.bancolombia.pocatv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.NivelAcceso;
import com.bancolombia.pocatv.repository.NivelAccesoRepository;
import com.bancolombia.pocatv.service.NivelAccesoService;

import java.util.List;
import java.util.Optional;

@Service
public class NivelAccesoServiceImpl implements NivelAccesoService {

    @Autowired
    private NivelAccesoRepository nivelAccesoRepository;

    @Override
    public List<NivelAcceso> findAll() {
        return nivelAccesoRepository.findAll();
    }

    @Override
    public Optional<NivelAcceso> findById(Long id) {
        return nivelAccesoRepository.findById(id);
    }

    @Override
    public NivelAcceso save(NivelAcceso nivelAcceso) {
        return nivelAccesoRepository.save(nivelAcceso);
    }

    @Override
    public void deleteById(Long id) {
        nivelAccesoRepository.deleteById(id);
    }
}