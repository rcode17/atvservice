package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffPdsId;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import com.bancolombia.pocatv.service.AtvffPdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtvffPdsServiceImpl implements AtvffPdsService{
	
	@Autowired
    private AtvffPdsRepository repository;
	
	@Override
    public Page<AtvffPds> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
	
	@Override
    public Optional<AtvffPds> findById(AtvffPdsId id) {
        return repository.findById(id);
    }

    @Override
    public AtvffPds save(AtvffPds atvffpds) {
        return repository.save(atvffpds);
    }

    @Override
    public void deleteById(AtvffPdsId id) {
        repository.deleteById(id);
    }

	@Override
	public List<AtvffPds> findByIdXsCosu(Integer xscosu) {
		return repository.findByIdXscosu(xscosu);
	}
	
}
