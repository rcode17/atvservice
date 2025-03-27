package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Atvffcps;
import com.bancolombia.pocatv.repository.AtvffcpsRepository;
import com.bancolombia.pocatv.service.AtvffcpsService;

import java.util.List;

@Service
public class AtvffcpsServiceImpl implements AtvffcpsService {

    @Autowired
    private AtvffcpsRepository repository;

    // Otros métodos...

    @Override
    public List<Atvffcps> findByYearAndMonth(Integer year, Integer month) {
        return repository.findByYearAndMonth(year, month);
    }
}