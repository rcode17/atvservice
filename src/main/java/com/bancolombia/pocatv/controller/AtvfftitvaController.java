package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ResultadoProcesamientoDTO;
import com.bancolombia.pocatv.service.AtvfftitvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Atvfftitva")
public class AtvfftitvaController {
	 @Autowired
	    private AtvfftitvaService atvfftitvaService;

	    @PostMapping("/procesar")
	    public ResponseEntity<ResultadoProcesamientoDTO> procesarInformacionArqueo() {
	        try {
	            ResultadoProcesamientoDTO resultado = atvfftitvaService.procesarInformacionArqueo();
	            return new ResponseEntity<>(resultado, HttpStatus.OK);
	        } catch (Exception e) {
	            ResultadoProcesamientoDTO errorResultado = new ResultadoProcesamientoDTO();
	            errorResultado.agregarError("Error al procesar información de arqueo: " + e.getMessage());
	            return new ResponseEntity<>(errorResultado, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
