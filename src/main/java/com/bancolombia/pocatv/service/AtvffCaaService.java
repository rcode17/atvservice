package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.Atvffcaa;
import java.util.List;

public interface AtvffCaaService {
    List<Atvffcaa> generarReporteCumplimiento(Integer ano, String fecha);
    List<Atvffcaa> obtenerReportePorAno(Integer ano);
    void eliminarReportePorAno(Integer ano);
}