package com.bancolombia.pocatv.controller;


import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import com.bancolombia.pocatv.service.AtvffPdoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/atvffpdo")
public class AtvffPdoController {

	 @Autowired
	    private AtvffPdoService service;

	    @GetMapping
	    public Page<AtvffPdo> getAll(Pageable pageable) {
	        return service.getAll(pageable);
	    }

	    @GetMapping("/{xpcopr}/{xpcodo}")
	    public ResponseEntity<AtvffPdo> getById(@PathVariable String xpcopr, @PathVariable String xpcodo) {
	        AtvffPdoId id = new AtvffPdoId();
	        id.setXpCopr(xpcopr);
	        id.setXpCodo(xpcodo);
	        Optional<AtvffPdo> atvffpdo = service.findById(id);
	        return atvffpdo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public AtvffPdo create(@RequestBody AtvffPdo atvffpdo) {
	        return service.save(atvffpdo);
	    }

	    @PutMapping("/{xpcopr}/{xpcodo}")
	    public ResponseEntity<AtvffPdo> update(@PathVariable String xpcopr, @PathVariable String xpcodo, @RequestBody AtvffPdo atvffpdo) {
	        AtvffPdoId id = new AtvffPdoId();
	        id.setXpCopr(xpcopr);
	        id.setXpCodo(xpcodo);
	        if (!service.findById(id).isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        atvffpdo.setId(id);
	        return ResponseEntity.ok(service.save(atvffpdo));
	    }

	    @DeleteMapping("/{xpcopr}/{xpcodo}")
	    public ResponseEntity<Void> delete(@PathVariable String xpcopr, @PathVariable String xpcodo) {
	    	AtvffPdoId id = new AtvffPdoId();
	        id.setXpCopr(xpcopr);
	        id.setXpCodo(xpcodo);
	        if (!service.findById(id).isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        service.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }
	
}
