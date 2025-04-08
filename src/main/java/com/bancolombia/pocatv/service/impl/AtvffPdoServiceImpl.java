package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.service.AtvffPdoService;
import com.bancolombia.pocatv.specification.AtvffPdoSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtvffPdoServiceImpl implements AtvffPdoService{
	
	 @Autowired
     private AtvffPdoRepository repository;

     @Override
     public Page<AtvffPdo> getAll(String xpDsdo,Pageable pageable) {
    	 Specification<AtvffPdo> spec = Specification.where(null);

         if (xpDsdo != null && !xpDsdo.isEmpty()) {
             spec = spec.and(AtvffPdoSpecification.filterByNameProduct(xpDsdo));
         }
        return repository.findAll(spec, pageable);
     }

     @Override
     public AtvffPdo save(AtvffPdo atvffpdo) {
        return repository.save(atvffpdo);
     }

	 @Override
	 public Optional<AtvffPdo> findById(AtvffPdoId id) {
		 return repository.findById(id);
	 }

	@Override
	public void deleteById(AtvffPdoId id) {
		repository.deleteById(id);
	}

}
