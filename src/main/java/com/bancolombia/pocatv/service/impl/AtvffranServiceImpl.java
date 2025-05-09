package com.bancolombia.pocatv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Atvffran;
import com.bancolombia.pocatv.model.AtvffranId;
import com.bancolombia.pocatv.repository.AtvffranRepository;
import com.bancolombia.pocatv.service.AtvffranService;

@Service
public class AtvffranServiceImpl implements AtvffranService {

    private final AtvffranRepository atvffranRepository;

    @Autowired
    public AtvffranServiceImpl(AtvffranRepository atvffranRepository) {
        this.atvffranRepository = atvffranRepository;
    }

    @Override
    public List<Atvffran> findAll() {
        return atvffranRepository.findAll();
    }

    @Override
    public Atvffran findById(String rnfrec, Integer rncant) {
        AtvffranId id = new AtvffranId(rnfrec, rncant);
        return atvffranRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el registro con periodicidad " + rnfrec + " y frecuencia " + rncant));
    }

    @Override
    public Atvffran save(Atvffran atvffran) {
        // Validaciones similares a las del RPG original
        if (atvffran.getRnfrec() == null || atvffran.getRnfrec().isEmpty() ||
            atvffran.getRncant() == null || atvffran.getRnrain() == null || atvffran.getRnrafn() == null) {
            throw new IllegalArgumentException("Hay campos obligatorios sin completar");
        }
        
        // Verificar si ya existe
        AtvffranId id = new AtvffranId(atvffran.getRnfrec(), atvffran.getRncant());
        if (atvffranRepository.existsById(id)) {
            throw new IllegalArgumentException("Ya existe un registro con esta periodicidad/frecuencia");
        }
        
        return atvffranRepository.save(atvffran);
    }

    @Override
    public void deleteById(String rnfrec, Integer rncant) {
        AtvffranId id = new AtvffranId(rnfrec, rncant);
        if (!atvffranRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe el registro que intenta eliminar");
        }
        atvffranRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String rnfrec, Integer rncant) {
        AtvffranId id = new AtvffranId(rnfrec, rncant);
        return atvffranRepository.existsById(id);
    }
}
