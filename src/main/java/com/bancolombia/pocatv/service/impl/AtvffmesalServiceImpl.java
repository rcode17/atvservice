package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Atvffmesal;
import com.bancolombia.pocatv.repository.AtvffmesalRepository;
import com.bancolombia.pocatv.service.AtvffmesalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtvffmesalServiceImpl implements AtvffmesalService {

    @Autowired
    private AtvffmesalRepository atvffmesalRepository;

    @Override
    public List<Atvffmesal> findAll() {
        return atvffmesalRepository.findAll();
    }

    @Override
    public Atvffmesal findById(String sdeofic, String sdtdoc) {
        Atvffmesal atvffmesal = atvffmesalRepository.findBySdeoficAndSdtdoc(sdeofic, sdtdoc);
        if (atvffmesal == null) {
            throw new IllegalArgumentException("No se encontró registro con oficina física " + sdeofic + " y documento " + sdtdoc);
        }
        return atvffmesal;
    }

    @Override
    public Atvffmesal save(Atvffmesal atvffmesal) {
        return atvffmesalRepository.save(atvffmesal);
    }

    @Override
    public void delete(String sdeofic, String sdtdoc) {
        Atvffmesal atvffmesal = findById(sdeofic, sdtdoc);
        atvffmesalRepository.delete(atvffmesal);
    }
}