package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffPdsId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AtvffPdsService {
	Page<AtvffPds> findAll(Pageable pageable);
	Optional<AtvffPds> findById(AtvffPdsId id);
	AtvffPds save(AtvffPds atvffpds);
	void deleteById(AtvffPdsId id);
	
	List<AtvffPds> findByIdXsCosu(Integer xscosu);
}
