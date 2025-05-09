package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.EstadisticaRegionDTO;
import com.bancolombia.pocatv.dto.ParametrosConsultaDTO;
import com.bancolombia.pocatv.model.Atvffiaa;
import com.bancolombia.pocatv.service.AtvffiaaService;
import com.bancolombia.pocatv.service.AtvriaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvffiaa")
public class AtvffiaaController {
    @Autowired
    private AtvriaaService atvriaaService;
    
    @Autowired
    private AtvffiaaService atvffiaaService;
    
    
    @PostMapping("/generar")
    public ResponseEntity<List<EstadisticaRegionDTO>> generarEstadisticas(@RequestBody ParametrosConsultaDTO parametros) {
        List<EstadisticaRegionDTO> estadisticas = atvriaaService.generarEstadisticasPorRegion(parametros);
        return ResponseEntity.ok(estadisticas);
    }

    @DeleteMapping("/limpiar/{ano}")
    public ResponseEntity<Void> limpiarEstadisticas(@PathVariable Integer ano) {
        atvriaaService.limpiarEstadisticas(ano);
        return ResponseEntity.ok().build();
    }
    
    
    @GetMapping("estadisticas/{ano}")
    public ResponseEntity<List<Atvffiaa>> obtenerEstadisticasPorAno(@PathVariable Integer ano) {
        List<Atvffiaa> estadisticas = atvffiaaService.obtenerEstadisticasPorAno(ano);
        return ResponseEntity.ok(estadisticas);
    }
    
    @PostMapping("estadisticas/generar/{ano}")
    public ResponseEntity<List<Atvffiaa>> generarEstadisticas(@PathVariable Integer ano) {
        List<Atvffiaa> estadisticas = atvffiaaService.generarEstadisticas(ano);
        return ResponseEntity.ok(estadisticas);
    }
}
