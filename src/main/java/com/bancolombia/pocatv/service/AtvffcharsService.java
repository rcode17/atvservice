package com.bancolombia.pocatv.service;



import java.util.List;

import com.bancolombia.pocatv.model.Atvffchsal;

public interface AtvffcharsService {
    List<Atvffchsal> procesarArqueos();
    List<Atvffchsal> obtenerTodosLosArqueos();
    List<Atvffchsal> obtenerArqueosPorOficina(String oficina);
    List<Atvffchsal> obtenerArqueosPorDocumento(String documento);
    Atvffchsal guardarArqueo(Atvffchsal arqueo);
    void eliminarArqueo(String oficina, String documento);
}