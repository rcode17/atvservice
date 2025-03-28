package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffPdsId;
import com.bancolombia.pocatv.service.AtvffPdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/atvffpds")
public class AtvffPdsController {
	
	@Autowired
    private AtvffPdsService service;
	
	@GetMapping
    public Page<AtvffPds> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }
	
	@GetMapping("/{xsCosu}/{xsCopr}/{xsCodo}")
    public ResponseEntity<AtvffPds> getById(
            @PathVariable Integer xsCosu,
            @PathVariable String xsCopr,
            @PathVariable String xsCodo) {

        AtvffPdsId id = new AtvffPdsId();
        id.setXsCosu(xsCosu);
        id.setXsCopr(xsCopr);
        id.setXsCodo(xsCodo);

        Optional<AtvffPds> atvffpds = service.findById(id);
        return atvffpds.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping
    public AtvffPds create(@RequestBody AtvffPds atvffpds) {
        return service.save(atvffpds);
    }
	
	@PutMapping("/{xsCosu}/{xsCopr}/{xsCodo}")
    public ResponseEntity<AtvffPds> update(
            @PathVariable Integer xsCosu,
            @PathVariable String xsCopr,
            @PathVariable String xsCodo,
            @RequestBody AtvffPds atvffpds) {

        AtvffPdsId id = new AtvffPdsId();
        id.setXsCosu(xsCosu);
        id.setXsCopr(xsCopr);
        id.setXsCodo(xsCodo);

        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        atvffpds.setId(id);
        return ResponseEntity.ok(service.save(atvffpds));
    }
	
	@DeleteMapping("/{xsCosu}/{xsCopr}/{xsCodo}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer xsCosu,
            @PathVariable String xsCopr,
            @PathVariable String xsCodo) {

        AtvffPdsId id = new AtvffPdsId();
        id.setXsCosu(xsCosu);
        id.setXsCopr(xsCopr);
        id.setXsCodo(xsCodo);

        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
