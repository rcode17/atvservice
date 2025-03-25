package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.Dominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DominioService {
    Page<Dominio> findAll(Pageable pageable);
    Optional<Dominio> findById(Integer id);
    Dominio save(Dominio dominio);
    void deleteById(Integer id);
}