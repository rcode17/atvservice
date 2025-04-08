package com.bancolombia.pocatv.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.UsuarioResponseDTO;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.service.XbknamService;

@RestController
@RequestMapping("/api/xbknam")
public class XbknamController {

    @Autowired
    private XbknamService xbknamService;

    @GetMapping
    public ResponseEntity<Page<Xbknam>> getAllAreas(
            @RequestParam(required = false) String xnname,
            Pageable pageable) {
        Page<Xbknam> areasPage = xbknamService.getAllXbknam(xnname, pageable);
        return ResponseEntity.ok(areasPage);
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Xbknam> getXbknamById(@PathVariable BigDecimal id) {
        return xbknamService.getXbknamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
