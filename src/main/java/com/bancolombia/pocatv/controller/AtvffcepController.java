package com.bancolombia.pocatv.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.dto.ConsultaEspecificaDTO;
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
		
		 @GetMapping("/{mes}/{ano}")
		    public ResponseEntity<List<ConsultaEspecificaDTO>> consultarEspecificaPorProducto(
		            @PathVariable Integer mes, 
		            @PathVariable Integer ano) {
		        
		        if (mes < 1 || mes > 12) {
		            return ResponseEntity.badRequest().build();
		        }
		        
		        if (ano < 1900 || ano > 2100) {
		            return ResponseEntity.badRequest().build();
		        }
		        
		        List<ConsultaEspecificaDTO> resultados = service.consultarEspecificaPorProducto(mes, ano);
		        return ResponseEntity.ok(resultados);
		    }
		    
		    @PostMapping("/procesar")
		    public ResponseEntity<Map<String, Object>> procesarConsultaEspecifica(
		            @RequestBody Map<String, Integer> params) {
		        
		        Integer mes = params.get("mes");
		        Integer ano = params.get("ano");
		        
		        if (mes == null || ano == null || mes < 1 || mes > 12 || ano < 1900 || ano > 2100) {
		            return ResponseEntity.badRequest().body(Map.of(
		                "success", false,
		                "message", "Parámetros inválidos. El mes debe estar entre 1 y 12, y el año entre 1900 y 2100."
		            ));
		        }
		        
		        boolean resultado = service.procesarConsultaEspecifica(mes, ano);
		        
		        if (resultado) {
		            return ResponseEntity.ok(Map.of(
		                "success", true,
		                "message", "Proceso completado correctamente"
		            ));
		        } else {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
		                "success", false,
		                "message", "Error al procesar la consulta específica"
		            ));
		        }
		    }
		
	


}
