package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Dominio;
import com.bancolombia.pocatv.repository.DominioRepository;
import com.bancolombia.pocatv.service.DominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DominioServiceImpl implements DominioService {

    @Autowired
    private DominioRepository dominioRepository;

    @Override
    public Page<Dominio> findAll(Pageable pageable) {
        return dominioRepository.findAll(pageable);
    }

    @Override
    public Optional<Dominio> findById(Integer id) {
        return dominioRepository.findById(id);
    }

    @Override
    public Dominio save(Dominio dominio) {
        return dominioRepository.save(dominio);
    }

    @Override
    public void deleteById(Integer id) {
        dominioRepository.deleteById(id);
    }
}
