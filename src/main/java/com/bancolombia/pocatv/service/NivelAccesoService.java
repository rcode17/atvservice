package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.NivelAcceso;
import java.util.List;
import java.util.Optional;

public interface NivelAccesoService {
	List<NivelAcceso> findAll();

	Optional<NivelAcceso> findById(Long id);

	NivelAcceso save(NivelAcceso nivelAcceso);

	void deleteById(Long id);
}