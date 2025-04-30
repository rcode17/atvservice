package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.Atvffmesal;
import java.util.List;

public interface AtvffmesalService {
    List<Atvffmesal> findAll();
    Atvffmesal findById(String sdeofic, String sdtdoc);
    Atvffmesal save(Atvffmesal atvffmesal);
    void delete(String sdeofic, String sdtdoc);
}