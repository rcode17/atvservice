package com.bancolombia.pocatv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.dto.ConciliacionDTO;
import com.bancolombia.pocatv.service.ConciliacionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/conciliacion")
public class ConciliacionController {

    @Autowired
    private ConciliacionService conciliacionService;

    /**
     * Endpoint para obtener la conciliación entre saldos de inventario y contables
     * 
     * @param codigoProducto Código del producto (2 caracteres)
     * @param codigoDocumento Código del documento (3 caracteres)
     * @param fecha Fecha de conciliación en formato ISO (YYYY-MM-DD)
     * @return Lista de resultados de conciliación por área operativa
     */
    @GetMapping
    public ResponseEntity<List<ConciliacionDTO> > obtenerConciliacion(
            @RequestParam String codigoProducto,
            @RequestParam String codigoDocumento,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
  
            List<ConciliacionDTO> resultados = conciliacionService.obtenerConciliacion(
                    codigoProducto, codigoDocumento, fecha);
            
            return ResponseEntity.ok(resultados);
  
    } 
}