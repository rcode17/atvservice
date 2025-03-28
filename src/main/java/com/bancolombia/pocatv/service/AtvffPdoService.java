package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AtvffPdoService {
	Page<AtvffPdo> getAll(Pageable pageable);
	Optional<AtvffPdo> findById(AtvffPdoId id);
    AtvffPdo save(AtvffPdo atvffpdo);
    void deleteById(AtvffPdoId id);
}
