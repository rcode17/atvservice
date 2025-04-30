package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.model.Atvlfarq;

public interface AtvrcascaService {
    void procesarCargaInformacion();
    List<Atvlfarq> consultarTodos();
    Atvlfarq consultarPorClave(String saofco, String satpro, String satdoc, String safech);
    Atvlfarq guardar(Atvlfarq atvlfarq);
    void eliminar(String saofco, String satpro, String satdoc, String safech);
}
