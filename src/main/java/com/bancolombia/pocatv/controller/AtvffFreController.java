package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.AtvffFreResponseDto;
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import com.bancolombia.pocatv.service.AtvffFreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/atvfffre")
public class AtvffFreController {
	@Autowired
    private AtvffFreService service;
	
	@GetMapping
    public Page<AtvffFre> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

	@GetMapping("/{fxCopr}/{fxCodo}")
	public ResponseEntity<AtvffFre> getById(@PathVariable String fxCopr, @PathVariable String fxCodo) {
		AtvffFreId id = new AtvffFreId();
        id.setFxCopr(fxCopr);
        id.setFxCodo(fxCodo);
        Optional<AtvffFre> atvfffre = service.findById(id);
        return atvfffre.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping
	public AtvffFre create(@RequestBody AtvffFre atvfffre) {
		return service.save(atvfffre);
	}
	
	@PutMapping("/{fxCopr}/{fxCodo}")
	public ResponseEntity<AtvffFre> update(@PathVariable String fxCopr, @PathVariable String fxCodo, @RequestBody AtvffFre atvfffre){
		AtvffFreId id = new AtvffFreId();
		id.setFxCopr(fxCopr);
        id.setFxCodo(fxCodo);
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        atvfffre.setId(id);
		return ResponseEntity.ok(service.save(atvfffre));
	}
	
	@DeleteMapping("/{fxCopr}/{fxCodo}")
    public ResponseEntity<Void> delete(@PathVariable String fxCopr, @PathVariable String fxCodo) {
		AtvffFreId id = new AtvffFreId();
		id.setFxCopr(fxCopr);
        id.setFxCodo(fxCodo);
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/product")
    public Page<AtvffFreResponseDto> getAtvffResults(Pageable pageable) {
        return service.getAllAtvffResults(pageable);
    }
}
