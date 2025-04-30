package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;

import java.util.List;

public interface AtvrmpercService {
    String ejecutarBatchCAE2(String fechaConsulta);

    String ejecutarBatchOAS2(String fechaConsulta);

    String ejecutarBatchCEP2(String fechaConsulta);

    String ejecutarBatchCPS2(String fechaConsulta);

    String ejecutarBatchCRI2(String fechaConsulta);

    String ejecutarBatchCRD2(String fechaConsulta);
}