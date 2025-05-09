package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.model.Atvffran;

public interface AtvffranService {
	List<Atvffran> findAll();
    Atvffran findById(String rnfrec, Integer rncant);
    Atvffran save(Atvffran atvffran);
    void deleteById(String rnfrec, Integer rncant);
    boolean existsById(String rnfrec, Integer rncant);

}
