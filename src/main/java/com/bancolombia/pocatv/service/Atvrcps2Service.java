package com.bancolombia.pocatv.service;


import java.util.List;

public interface Atvrcps2Service {

    void procesarActualizacion(Integer mes, Integer ano);

    List<Object> consultarResultados(Integer mes, Integer ano);
}