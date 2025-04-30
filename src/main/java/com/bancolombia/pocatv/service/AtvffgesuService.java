package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.ComentarioDTO;
import com.bancolombia.pocatv.dto.RespuestaDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AtvffgesuService {

    List<ComentarioDTO> obtenerComentarios(Integer codsuc, String codpro, String coddoc, String aqfear, BigDecimal difere);

    RespuestaDTO guardarComentario(ComentarioDTO comentarioDTO);

    List<ComentarioDTO> obtenerComentariosPredefinidos();

    RespuestaDTO limpiarComentariosTemporales(Integer codsuc, String codpro, String coddoc, Integer fechaArqueo);
}