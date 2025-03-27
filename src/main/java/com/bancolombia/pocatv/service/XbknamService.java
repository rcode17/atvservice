package com.bancolombia.pocatv.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.bancolombia.pocatv.model.Xbknam;

public interface XbknamService {
    List<Xbknam> getAllXbknam();
    Optional<Xbknam> getXbknamById(BigDecimal id);
}
