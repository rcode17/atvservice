package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.AreaDocumentoPdoDTO;
import com.bancolombia.pocatv.model.AtvffPdoId;
import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.XbknamService;
import com.bancolombia.pocatv.specification.KbknamSpecification;

@Service
public class XbknamServiceImpl implements XbknamService {
	
	@Autowired
    private AtvffPdoRepository atvffPdoRepository;
    
    @Autowired
    private AtvffPdsRepository atvffPdsRepository;

    @Autowired
    private XbknamRepository xbknamRepository;

    @Override
    public Optional<Xbknam> getXbknamById(BigDecimal id) {
        return xbknamRepository.findById(id);
    }

	@Override
	public Page<Xbknam> getAllXbknam(String xnname, Pageable pageable) {
		Specification<Xbknam> spec = Specification.where(null);

        if (xnname != null && !xnname.isEmpty()) {
            spec = spec.and(KbknamSpecification.filterByName(xnname));
        }

        return xbknamRepository.findAll(spec, pageable);
	}

	@Override
	public Page<AreaDocumentoPdoDTO> getAreaProductoDocumento(String codPro, String codDoc, Pageable pageable) {

	    if (!atvffPdoRepository.existsById(new AtvffPdoId(codPro, codDoc))) {
	        throw new IllegalArgumentException("Producto o Documento no existe");
	    }

	    Page<AtvffPds> pdsPage = atvffPdsRepository.findByXscoprAndXscodo(codPro, codDoc, pageable);

	    return pdsPage.map(pds -> {
	        Integer codigo = pds.getXscosu();
	        Optional<Xbknam> xbknamOpt = xbknamRepository.findById(new BigDecimal(codigo));
	        String nombre = xbknamOpt.map(Xbknam::getXnname).orElse("Nombre no encontrado");
	        return new AreaDocumentoPdoDTO(codigo, nombre);
	    });
	}
	

}