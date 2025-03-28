package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.AtvffFreResponseDto;
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import com.bancolombia.pocatv.repository.AtvffFreRepository;
import com.bancolombia.pocatv.service.AtvffFreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtvffFreServiceImpl implements AtvffFreService{
	@Autowired
    private AtvffFreRepository repository;

    @Override
    public Page<AtvffFre> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<AtvffFre> findById(AtvffFreId id) {
        return repository.findById(id);
    }

    @Override
    public AtvffFre save(AtvffFre atvfffre) {
        return repository.save(atvfffre);
    }

    @Override
    public void deleteById(AtvffFreId id) {
        repository.deleteById(id);
    }
    
    @Override
    public List<AtvffFreResponseDto> getAllAtvffResults() {
        return repository.findAllAtvffResults();
    }

}
