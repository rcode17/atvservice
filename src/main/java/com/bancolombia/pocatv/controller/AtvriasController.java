package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.service.AtvriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvrias")
public class AtvriasController {
	
	 @Autowired
	 private AtvriasService atvriasService;
	
	@GetMapping("/areasActivas")
    public Page<Xbknam> validarUsuarioArea(
            @RequestParam String usuario,
            @RequestParam Integer anno, Pageable pageable) {
        return atvriasService.obtenerAreasPorUsuarioYAno(usuario, anno, pageable);
    }
	
}
