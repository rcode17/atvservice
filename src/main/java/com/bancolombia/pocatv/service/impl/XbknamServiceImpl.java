package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.XbknamService;

@Service
public class XbknamServiceImpl implements XbknamService {

    @Autowired
    private XbknamRepository xbknamRepository;

    @Override
    public List<Xbknam> getAllXbknam() {
        return xbknamRepository.findAll();
    }

    @Override
    public Optional<Xbknam> getXbknamById(BigDecimal id) {
        return xbknamRepository.findById(id);
    }

}