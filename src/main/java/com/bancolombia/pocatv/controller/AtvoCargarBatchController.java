package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.service.MainBatchAtvoCargarService;

@RestController
@RequestMapping("/api/batch/atvocargar")
public class AtvoCargarBatchController {
	@Autowired
    private MainBatchAtvoCargarService mainBatchService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeBatch() {
        mainBatchService.executeBatch();
        return ResponseEntity.ok("Proceso batch ejecutado con éxito");
    }

}
