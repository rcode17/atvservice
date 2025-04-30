package com.bancolombia.pocatv.service;

public interface AtvrmpersService {
    String ejecutarBatchATVOFREIN(String fechaConsulta);

    String ejecutarBatchATVRSAA2(String fechaConsulta);

    String ejecutarBatchATVRSIA2(String fechaConsulta);

    String ejecutarBatchATVRIAP2(String fechaConsulta);

    String ejecutarBatchATVREGA2(String fechaConsulta);


}