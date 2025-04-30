package com.bancolombia.pocatv.controller;


import com.bancolombia.pocatv.dto.Conciliacion2DTO;
import com.bancolombia.pocatv.dto.ConciliacionRequest;
import com.bancolombia.pocatv.dto.ResponseDTO;
import com.bancolombia.pocatv.model.Atvffcad;
import com.bancolombia.pocatv.repository.AtvffcadRepository;
import com.bancolombia.pocatv.service.AtvffcadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/conciliacion/auto")
public class AtvffcadController {

    @Autowired
    private AtvffcadService atvffcadService;
    
    @Autowired
    private AtvffcadRepository atvffcadRepository;
    
    /**
     * Endpoint para generar la conciliación automática
     * @param request Objeto con los parámetros día, mes y año
     * @return Respuesta con mensaje de éxito o error
     */
    @PostMapping("/generar")
    public ResponseEntity<?> generarConciliacion(@RequestBody ConciliacionRequest request) {
        try {
            atvffcadService.generarConciliacion(request);
            return ResponseEntity.ok(new ResponseDTO(true,"Conciliación generada correctamente"));
           
        } catch (IllegalArgumentException e) {

            return ResponseEntity.ok(new ResponseDTO(true,e.getMessage()));
        } catch (Exception e) {
        	 return ResponseEntity.ok(new ResponseDTO(true,e.getMessage()));
        }
    }
    
    /**
     * Endpoint para obtener todas las conciliaciones
     * @return Lista de conciliaciones
     */
    @GetMapping
    public ResponseEntity<List<Atvffcad>> obtenerConciliaciones() {
        List<Atvffcad> conciliaciones = atvffcadRepository.findAll();
        return ResponseEntity.ok(conciliaciones);
    }
    
    /**
     * Endpoint para obtener conciliaciones por fecha
     * @param ano Año de la conciliación
     * @param mes Mes de la conciliación
     * @param dia Día de la conciliación
     * @return Lista de conciliaciones que coinciden con la fecha
     */
    @GetMapping("/fecha/{ano}/{mes}/{dia}")
    public ResponseEntity<List<Atvffcad>> obtenerConciliacionesPorFecha(
            @PathVariable Integer ano,
            @PathVariable Integer mes,
            @PathVariable Integer dia) {
        
        List<Atvffcad> conciliaciones = atvffcadRepository.findAll().stream()
                .filter(c -> c.getCaano().equals(ano) && 
                             c.getCames().equals(mes) && 
                             c.getCadia().equals(dia))
                .toList();
        
        return ResponseEntity.ok(conciliaciones);
    }
    
    /**
     * Endpoint para obtener conciliaciones por producto y documento
     * @param codigoProducto Código del producto
     * @param codigoDocumento Código del documento
     * @return Lista de conciliaciones que coinciden con el producto y documento
     */
    @GetMapping("/producto/{codigoProducto}/documento/{codigoDocumento}")
    public ResponseEntity<List<Atvffcad>> obtenerConciliacionesPorProductoYDocumento(
            @PathVariable String codigoProducto,
            @PathVariable String codigoDocumento) {
        
        List<Atvffcad> conciliaciones = atvffcadRepository.findAll().stream()
                .filter(c -> c.getCacopr().equals(codigoProducto) && 
                             c.getCacodo().equals(codigoDocumento))
                .toList();
        
        return ResponseEntity.ok(conciliaciones);
    }
    
    @GetMapping("/consultar")
    public ResponseEntity<?> consultarConciliaciones(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("usuario") String usuario) {
        
  
        	
            List<Conciliacion2DTO> conciliaciones = atvffcadService.consultarConciliacionesPorFecha(
                fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth(), usuario);
            return ResponseEntity.ok(conciliaciones);

    }
}