package com.bancolombia.pocatv.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.service.XbknamService;

@RestController
@RequestMapping("/api/xbknam")
public class XbknamController {

    @Autowired
    private XbknamService xbknamService;

    // Obtener todos los registros
    @GetMapping
    public List<Xbknam> getAllXbknam() {
        return xbknamService.getAllXbknam();
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Xbknam> getXbknamById(@PathVariable BigDecimal id) {
        return xbknamService.getXbknamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
