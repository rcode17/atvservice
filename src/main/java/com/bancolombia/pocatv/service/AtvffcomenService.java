package com.bancolombia.pocatv.service;


import java.util.List;

import com.bancolombia.pocatv.model.Atvffcomen;

public interface AtvffcomenService {
    
    List<Atvffcomen> findAllComentarios();
    
    Atvffcomen findComentarioById(Integer id);
    
    Atvffcomen saveComentario(Atvffcomen comentario);
    
    Atvffcomen updateComentario(Integer id, Atvffcomen comentario);
    
    void deleteComentario(Integer id);
}
