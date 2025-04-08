package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bancolombia.pocatv.dto.AtvffFreResponseDto;

import java.util.List;
import java.util.Optional;

public interface AtvffFreService {
	Page<AtvffFre> getAll(Pageable pageable);
    Optional<AtvffFre> findById(AtvffFreId id);
    AtvffFre save(AtvffFre atvfffre);
    void deleteById(AtvffFreId id);
    Page<AtvffFreResponseDto> getAllAtvffResults(Pageable pageable);
}