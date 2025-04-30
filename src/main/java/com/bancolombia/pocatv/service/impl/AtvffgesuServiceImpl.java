package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.ComentarioDTO;
import com.bancolombia.pocatv.dto.RespuestaDTO;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffgesuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AtvffgesuServiceImpl implements AtvffgesuService {

    @Autowired
    private AtvffgesuRepository atvffgesuRepository;

    @Autowired
    private AtvfftgesuRepository atvfftgesuRepository;

    @Autowired
    private AtvffcomenRepository atvffcomenRepository;

    @Override
    public List<ComentarioDTO> obtenerComentarios(Integer codsuc, String codpro, String coddoc, String aqfear, BigDecimal difere) {
        if (codsuc == null || codpro == null || coddoc == null || aqfear == null) {
            throw new IllegalArgumentException("Los parámetros de búsqueda no pueden ser nulos");
        }

        Integer fechaArqueo = Integer.parseInt(aqfear.replace("/", ""));

        List<Atvfftgesu> comentarios = atvfftgesuRepository.findComentariosByParams(codsuc, codpro, coddoc, fechaArqueo);

        if (comentarios.isEmpty()) {
            // Cargar comentarios temporales desde ATVFFGESU
            limpiarComentariosTemporales(codsuc, codpro, coddoc, fechaArqueo);
            cargarComentariosTemporales(codsuc, codpro, coddoc, fechaArqueo, difere);
            comentarios = atvfftgesuRepository.findComentariosByParams(codsuc, codpro, coddoc, fechaArqueo);
        }

        return mapearComentariosADTO(comentarios);
    }

    @Override
    @Transactional
    public RespuestaDTO guardarComentario(ComentarioDTO comentarioDTO) {
        try {
            Atvfftgesu rtgesu = new Atvfftgesu();

            // Obtener el siguiente código de comentario
            Integer codigoComentario = 1;
            Integer conteo = atvfftgesuRepository.countByGsucurAndGproduAndGdocumAndGfearq(
                    comentarioDTO.getSucursal(),
                    comentarioDTO.getProducto(),
                    comentarioDTO.getDocumento(),
                    comentarioDTO.getFechaArqueo()
            );

            if (conteo > 0) {
                codigoComentario = conteo + 1;
            }

            // Configurar datos del comentario
            rtgesu.setGcodco(codigoComentario);
            rtgesu.setGusuar(comentarioDTO.getUsuario());
            rtgesu.setGfecom(obtenerFechaActual());
            rtgesu.setGhoras(obtenerHoraActual());
            rtgesu.setGsucur(comentarioDTO.getSucursal());
            rtgesu.setGprodu(comentarioDTO.getProducto());
            rtgesu.setGdocum(comentarioDTO.getDocumento());
            rtgesu.setGfearq(comentarioDTO.getFechaArqueo());
            rtgesu.setGdifer(comentarioDTO.getDiferencia());

            // Concatenar los 4 campos de comentario en uno solo
            StringBuilder comentarioCompleto = new StringBuilder();
            if (comentarioDTO.getComentario1() != null) comentarioCompleto.append(comentarioDTO.getComentario1());
            if (comentarioDTO.getComentario2() != null) comentarioCompleto.append(comentarioDTO.getComentario2());
            if (comentarioDTO.getComentario3() != null) comentarioCompleto.append(comentarioDTO.getComentario3());
            if (comentarioDTO.getComentario4() != null) comentarioCompleto.append(comentarioDTO.getComentario4());

            rtgesu.setGcomen(comentarioCompleto.toString());

            atvfftgesuRepository.save(rtgesu);

            return new RespuestaDTO("Comentario ingresado correctamente", true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar el comentario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPredefinidos() {
        List<Atvffcomen> comentariosPredefinidos = atvffcomenRepository.findAll();

        return comentariosPredefinidos.stream()
                .map(this::mapearComentarioPredefinidoADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RespuestaDTO limpiarComentariosTemporales(Integer codsuc, String codpro, String coddoc, Integer fechaArqueo) {
        try {
            atvfftgesuRepository.deleteAllByGsucurAndGproduAndGdocumAndGfearq(codsuc, codpro, coddoc, fechaArqueo);
            return new RespuestaDTO("Comentarios temporales limpiados correctamente", true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al limpiar comentarios temporales: " + e.getMessage(), e);
        }
    }

    private void cargarComentariosTemporales(Integer codsuc, String codpro, String coddoc, Integer fechaArqueo, BigDecimal difere) {
        // Esta función simula la carga de comentarios desde ATVFFGESU a ATVFFTGESU
        // En el sistema original, esto se hace en la subrutina SUB_LLENA_TEM
    }

    private List<ComentarioDTO> mapearComentariosADTO(List<Atvfftgesu> comentarios) {
        List<ComentarioDTO> comentariosDTO = new ArrayList<>();

        for (Atvfftgesu comentario : comentarios) {
            ComentarioDTO dto = new ComentarioDTO();
            dto.setUsuario(comentario.getGusuar());
            dto.setFecha(comentario.getGfecom());
            dto.setHora(comentario.getGhoras());
            dto.setCodigo(comentario.getGcodco());

            // Dividir el comentario en 4 partes (como en el sistema original)
            String comentarioCompleto = comentario.getGcomen();
            if (comentarioCompleto != null) {
                int longitud = comentarioCompleto.length();
                dto.setComentario1(comentarioCompleto.substring(0, Math.min(51, longitud)));
                if (longitud > 51) dto.setComentario2(comentarioCompleto.substring(51, Math.min(114, longitud)));
                if (longitud > 114) dto.setComentario3(comentarioCompleto.substring(114, Math.min(177, longitud)));
                if (longitud > 177) dto.setComentario4(comentarioCompleto.substring(177, Math.min(240, longitud)));
            }

            dto.setSucursal(comentario.getGsucur());
            dto.setProducto(comentario.getGprodu());
            dto.setDocumento(comentario.getGdocum());
            dto.setFechaArqueo(comentario.getGfearq());
            dto.setDiferencia(comentario.getGdifer());

            comentariosDTO.add(dto);
        }

        return comentariosDTO;
    }

    private ComentarioDTO mapearComentarioPredefinidoADTO(Atvffcomen comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setCodigo(comentario.getComcon());

        // Dividir el comentario en 4 partes (como en el sistema original)
        String comentarioCompleto = comentario.getComjus();
        if (comentarioCompleto != null) {
            int longitud = comentarioCompleto.length();
            dto.setComentario1(comentarioCompleto.substring(0, Math.min(51, longitud)));
            if (longitud > 51) dto.setComentario2(comentarioCompleto.substring(51, Math.min(114, longitud)));
            if (longitud > 114) dto.setComentario3(comentarioCompleto.substring(114, Math.min(177, longitud)));
            if (longitud > 177) dto.setComentario4(comentarioCompleto.substring(177, Math.min(240, longitud)));
        }

        return dto;
    }

    private Integer obtenerFechaActual() {
        LocalDate fechaActual = LocalDate.now();
        return Integer.parseInt(fechaActual.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    private Integer obtenerHoraActual() {
        LocalTime horaActual = LocalTime.now();
        return Integer.parseInt(horaActual.format(DateTimeFormatter.ofPattern("HHmmss")));
    }
}