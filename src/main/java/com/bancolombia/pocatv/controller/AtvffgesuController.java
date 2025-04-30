package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.ComentarioDTO;
import com.bancolombia.pocatv.dto.RespuestaDTO;
import com.bancolombia.pocatv.service.AtvffapaService;
import com.bancolombia.pocatv.service.AtvffgesuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/Atvffgesu")
public class AtvffgesuController {
    @Autowired
    private AtvffgesuService atvffgesuService;

    @GetMapping("/comentarios")
    public ResponseEntity<?> obtenerComentarios(
            @RequestParam Integer codsuc,
            @RequestParam String codpro,
            @RequestParam String coddoc,
            @RequestParam String aqfear,
            @RequestParam BigDecimal difere) {

        try {
            List<ComentarioDTO> comentarios = atvffgesuService.obtenerComentarios(codsuc, codpro, coddoc, aqfear, difere);
            return ResponseEntity.ok(comentarios);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO("Error al obtener comentarios: " + e.getMessage(), false));
        }
    }

    @PostMapping("/comentarios")
    public ResponseEntity<?> guardarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        try {
            RespuestaDTO respuesta = atvffgesuService.guardarComentario(comentarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO("Error al guardar comentario: " + e.getMessage(), false));
        }
    }

    @GetMapping("/comentarios/predefinidos")
    public ResponseEntity<?> obtenerComentariosPredefinidos() {
        try {
            List<ComentarioDTO> comentarios = atvffgesuService.obtenerComentariosPredefinidos();
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO("Error al obtener comentarios predefinidos: " + e.getMessage(), false));
        }
    }

    @DeleteMapping("/comentarios/temporales")
    public ResponseEntity<?> limpiarComentariosTemporales(
            @RequestParam Integer codsuc,
            @RequestParam String codpro,
            @RequestParam String coddoc,
            @RequestParam Integer fechaArqueo) {

        try {
            RespuestaDTO respuesta = atvffgesuService.limpiarComentariosTemporales(codsuc, codpro, coddoc, fechaArqueo);
            return ResponseEntity.ok(respuesta);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO("Error al limpiar comentarios temporales: " + e.getMessage(), false));
        }
    }
}