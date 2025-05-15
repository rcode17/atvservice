package com.bancolombia.pocatv.service;

import java.util.List;

public interface AtvffdsunService {
    void procesarActualizacion(Integer mes, Integer ano, String usuario);
    List<Object> consultarDatos(Integer mes, Integer ano);
}
