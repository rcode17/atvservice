package com.bancolombia.pocatv.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bancolombia.pocatv.dto.AreaDocumentoPdoDTO;
import com.bancolombia.pocatv.model.Xbknam;

public interface XbknamService {
	Page<Xbknam> getAllXbknam(String xnname, Pageable pageable);
    Optional<Xbknam> getXbknamById(BigDecimal id);
    
    Page<AreaDocumentoPdoDTO> getAreaProductoDocumento(String codPro, String codDoc, Pageable pageable);
}
