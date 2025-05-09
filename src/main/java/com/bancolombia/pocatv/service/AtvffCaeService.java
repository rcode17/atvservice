package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffCae;
import java.util.List;

public interface AtvffCaeService {
    List<AtvffCae> generarReporteCumplimiento(Integer mes, Integer ano);
    void limpiarDatosAnteriores(Integer mes, Integer ano);
}