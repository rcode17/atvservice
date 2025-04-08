package com.bancolombia.pocatv.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.ConsultaSucProdDetalleRequestDTO;
import com.bancolombia.pocatv.dto.ConsultaSucProdDetalleResponseDTO;
import com.bancolombia.pocatv.dto.atvffcepfechaResponseDTO;
import com.bancolombia.pocatv.model.AtvffCri;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.service.AtvffCriService;
import com.bancolombia.pocatv.service.AtvffarqService;
import com.bancolombia.pocatv.service.AtvffcepService;
import com.bancolombia.pocatv.service.ConsultaSucProdService;


@RestController
@RequestMapping("/api/search")
public class AtvffcepController {

	@Autowired
	private AtvffarqService atvffarqService;

	@Autowired
	private AtvffcepService service;

	@Autowired
	private ConsultaSucProdService consultaService;
	
    @Autowired
    private AtvffCriService atvffCriService;
	  

	    @GetMapping("/atvffcep")
	    public List<atvffcepfechaResponseDTO> findByYearAndMonth(@RequestParam Integer ano, @RequestParam Integer mes) {
	        return service.findDescriptionsByYearAndMonth(ano, mes);
	    }
	    
	    @PostMapping("/atvrcpd")
	    public ConsultaSucProdDetalleResponseDTO realizarConsulta(@RequestBody ConsultaSucProdDetalleRequestDTO request) {
	        return consultaService.procesarConsulta(request);
	    }
	    
		@GetMapping("/atvffarq")
		public ResponseEntity<Atvffarq> getByFields(@RequestParam Integer aqcdsu, @RequestParam String aqcopr,
				@RequestParam String aqcodo, @RequestParam String aqfear) {

			
			Optional<Atvffarq> result = atvffarqService.findByFields(aqcdsu, aqcopr, aqcodo, aqfear);
			return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
		}
		
		
		@GetMapping("/atvffcri")
		public ResponseEntity<List<Atvffarq>> buscarRangosinconsistentes(@RequestParam Integer aqcdsu, @RequestParam String aqcopr,
				@RequestParam String aqcodo, @RequestParam String aqfear,@RequestParam String aqcedan) {
	
			List<Atvffarq> result = atvffarqService.buscarRangosInconsistentes(aqcdsu, aqcopr, aqcodo, aqfear, aqcedan);
			return ResponseEntity.ok(result);
		}
		
	


}
