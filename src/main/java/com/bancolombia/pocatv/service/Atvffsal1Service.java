package com.bancolombia.pocatv.service;


import java.util.List;

import com.bancolombia.pocatv.model.Atvffsal1;
import com.bancolombia.pocatv.model.Atvffsal1Id;


public interface Atvffsal1Service {
    
    List<Atvffsal1> obtenerTodos();
    
    Atvffsal1 obtenerPorId(Atvffsal1Id id);
    
    List<Atvffsal1> buscarPorProductoYDocumento(String satpro, String satdoc);
    
    Atvffsal1 guardar(Atvffsal1 atvffsal);
    
    void eliminarPorId(Atvffsal1Id id);
    
    void actualizarDocumento();
}