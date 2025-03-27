package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.model.Atvffcps;
import com.bancolombia.pocatv.service.AtvffcpsService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class AtvffcpsController {

    @Autowired
    private AtvffcpsService service;

    // Otros métodos...

    @GetMapping("/search")
    public List<Atvffcps> findByYearAndMonth(@RequestParam Integer year, @RequestParam Integer month) {
        return service.findByYearAndMonth(year, month);
    }
}