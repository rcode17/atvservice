package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.service.AtvrmpersService;
import org.springframework.stereotype.Service;


@Service
public class AtvrmpersServiceImpl implements AtvrmpersService {

    public String ejecutarBatchATVOFREIN(String fechaConsulta) {
        // Aquí implementa la lógica para ejecutar el batch OAS2
        return "Batch ATVOFREIN ejecutado con fecha: " + fechaConsulta;
    }

    public String ejecutarBatchATVRSAA2(String fechaConsulta) {
        // Aquí implementa la lógica para ejecutar el batch CEP2
        return "Batch ATVRSAA2 ejecutado con fecha: " + fechaConsulta;
    }

    public String ejecutarBatchATVRSIA2(String fechaConsulta) {
        // Aquí implementa la lógica para ejecutar el batch CPS2
        return "Batch ATVRSIA2 ejecutado con fecha: " + fechaConsulta;
    }

    public String ejecutarBatchATVRIAP2(String fechaConsulta) {
        // Aquí implementa la lógica para ejecutar el batch CRI2
        return "Batch ATVRIAP2 ejecutado con fecha: " + fechaConsulta;
    }

    public String ejecutarBatchATVREGA2(String fechaConsulta) {
        // Aquí implementa la lógica para ejecutar el batch CRD2
        return "Batch ATVREGA2 ejecutado con fecha: " + fechaConsulta;
    }

}
