package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.XbknamService;
import com.bancolombia.pocatv.specification.AtvffUserSpecification;
import com.bancolombia.pocatv.specification.KbknamSpecification;

@Service
public class XbknamServiceImpl implements XbknamService {

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

}