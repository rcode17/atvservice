package com.bancolombia.pocatv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.IncumplimientoDTO;
import com.bancolombia.pocatv.dto.ResponseIncumplimientoDTO;
import com.bancolombia.pocatv.service.AtvffarqService;



@RestController
@RequestMapping("/api/incumplimientos") 
public class AtvraifController {
	

    @Autowired
    private  AtvffarqService atvffarqService;
    
    public AtvraifController(AtvffarqService atvffarqService) {
        this.atvffarqService = atvffarqService;
      
    }
	
    @GetMapping("/ultimodiahabil")
    public ResponseEntity<ResponseIncumplimientoDTO<List<IncumplimientoDTO>>> obtenerIncumplimientosUltimoDiaHabil(
            @RequestParam String usuario) {
        try {
            // Llamar al método del servicio
            ResponseIncumplimientoDTO<List<IncumplimientoDTO>> response = atvffarqService.obtenerIncumplimientosUltimoDiaHabil(usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejo de errores
            ResponseIncumplimientoDTO<List<IncumplimientoDTO>> errorResponse = ResponseIncumplimientoDTO.error("01", "Error al obtener incumplimientos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    
    @GetMapping("/verificar-incumplimiento")
    public ResponseEntity<ResponseIncumplimientoDTO<Boolean>> verificarIncumplimientoSucursal(
            @RequestParam Integer codigoSucursal,
            @RequestParam String codigoProducto,
            @RequestParam String codigoDocumento,
            @RequestParam String usuario) {
        try {
            // Llamar al método del servicio
            ResponseIncumplimientoDTO<Boolean> response = atvffarqService.verificarIncumplimientoSucursal(codigoSucursal, codigoProducto, codigoDocumento, usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejo de errores
            ResponseIncumplimientoDTO<Boolean> errorResponse = ResponseIncumplimientoDTO.error("03", "Error al verificar incumplimiento de sucursal: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    

    @GetMapping("/por-mes-ano")
    public ResponseEntity<ResponseIncumplimientoDTO<List<IncumplimientoDTO>>> obtenerIncumplimientosPorMesAno(
            @RequestParam Integer mes,
            @RequestParam Integer ano,
            @RequestParam String usuario) {
        try {
            // Llamar al método del servicio
            ResponseIncumplimientoDTO<List<IncumplimientoDTO>> response = 
            		atvffarqService.obtenerIncumplimientosPorMesAno(mes, ano, usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejo de errores
            ResponseIncumplimientoDTO<List<IncumplimientoDTO>> errorResponse = 
                ResponseIncumplimientoDTO.error("02", "Error al obtener incumplimientos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}


