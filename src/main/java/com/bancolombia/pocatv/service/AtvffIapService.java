package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.model.AtvffIap;
import java.util.List;

public interface AtvffIapService {
    List<AtvffIap> generarArchivoCalidadInformacion(Integer ano, String fecha);
    List<AtvffIap> obtenerTodos();
    AtvffIap obtenerPorId(Integer ano, Integer codpro, Integer coddoc);
    AtvffIap guardar(AtvffIap atvffIap);
    void eliminar(Integer ano, Integer codpro, Integer coddoc);
    
    // Añadir este método a la interfaz
    List<AtvffIap> generarPorRangoFechas(Integer ano, String fechaInicio, String fechaFin);
}