package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancolombia.pocatv.model.Atvffcomen;
import com.bancolombia.pocatv.repository.AtvffcomenRepository;
import com.bancolombia.pocatv.service.AtvffcomenService;

import java.util.List;

@Service
public class AtvffcomenServiceImpl implements AtvffcomenService {

    private final AtvffcomenRepository atvffcomenRepository;

    @Autowired
    public AtvffcomenServiceImpl(AtvffcomenRepository atvffcomenRepository) {
        this.atvffcomenRepository = atvffcomenRepository;
    }

    @Override
    public List<Atvffcomen> findAllComentarios() {
        return atvffcomenRepository.findAll();
    }

    @Override
    public Atvffcomen findComentarioById(Integer id) {
        return atvffcomenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró comentario con ID: " + id));
    }

    @Override
    @Transactional
    public Atvffcomen saveComentario(Atvffcomen comentario) {
        if (comentario.getComjus() == null || comentario.getComjus().trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }
        
        // Generar consecutivo automáticamente
        if (comentario.getComcon() == null) {
            Integer maxConsecutivo = atvffcomenRepository.findMaxConsecutivo();
            comentario.setComcon(maxConsecutivo + 1);
        }
        
        return atvffcomenRepository.save(comentario);
    }

    @Override
    @Transactional
    public Atvffcomen updateComentario(Integer id, Atvffcomen comentario) {
        Atvffcomen existingComentario = findComentarioById(id);
        
        if (comentario.getComjus() == null || comentario.getComjus().trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }
        
        existingComentario.setComjus(comentario.getComjus());
        return atvffcomenRepository.save(existingComentario);
    }

    @Override
    @Transactional
    public void deleteComentario(Integer id) {
        Atvffcomen comentario = findComentarioById(id);
        atvffcomenRepository.delete(comentario);
    }
}
