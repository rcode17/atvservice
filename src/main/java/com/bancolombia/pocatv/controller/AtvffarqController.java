package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.ArqueoDescuadradoDTO;
import com.bancolombia.pocatv.dto.IncumplimientoDTO;
import com.bancolombia.pocatv.dto.ArqueoResumenDTO;
import com.bancolombia.pocatv.dto.ArqueoTotalesDTO;
import com.bancolombia.pocatv.dto.ArqueoDTO;
import com.bancolombia.pocatv.dto.ResponseDTO;
import com.bancolombia.pocatv.dto.ResponseIncumplimientoDTO;
import com.bancolombia.pocatv.model.Atvffcrd;
import com.bancolombia.pocatv.service.AtvffarqService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/arqueos")
public class AtvffarqController {

    @Autowired
    private AtvffarqService atvffarqService;

    /**
     * Endpoint para consultar arqueos descuadrados
     * @param usuario Identificador del usuario
     * @param mes Mes de consulta
     * @param anno Año de consulta
     * @param dom Dominio del usuario
     * @param codArea Código de área opcional para filtrar
     * @return Lista de arqueos descuadrados
     */
	@GetMapping("/descuadrados")
	public List<ArqueoDescuadradoDTO> consultarArqueosDescuadrados(@RequestParam String usuario,
			@RequestParam Integer mes, @RequestParam Integer anno, @RequestParam String dom,
			@RequestParam(required = false) Integer codArea) {

		List<ArqueoDescuadradoDTO> arqueos = atvffarqService.consultarArqueosDescuadrados(usuario, mes, anno, dom,
				codArea);

		return arqueos;

	}

    /**
     * Endpoint para gestionar un arqueo
     * @param datos Datos necesarios para la gestión
     * @return Respuesta de éxito o error
     */
    @PostMapping("/gestionar")
    public ResponseEntity<ResponseDTO> gestionarArqueo(@RequestBody Map<String, Object> datos) {
    
            Integer codsuc = Integer.valueOf(datos.get("codsuc").toString());
            String codpro = (String) datos.get("codpro");
            String coddoc = (String) datos.get("coddoc");
            String fecha = (String) datos.get("fecha");
            Double diferencia = Double.valueOf(datos.get("diferencia").toString());
            String acceso = (String) datos.get("acceso");
            
            atvffarqService.gestionarArqueo(codsuc, codpro, coddoc, fecha, diferencia, acceso);
          return ResponseEntity.ok(new ResponseDTO(true,"Arqueo Gestionado"));
    
    }

    /**
     * Endpoint para justificar un arqueo
     * @param datos Datos necesarios para la justificación
     * @return Respuesta de éxito o error
     */
    @PostMapping("/justificar")
    public ResponseEntity<ResponseDTO> justificarArqueo(@RequestBody Map<String, Object> datos) {
        try {
            Integer codsuc = Integer.valueOf(datos.get("codsuc").toString());
            String codpro = (String) datos.get("codpro");
            String coddoc = (String) datos.get("coddoc");
            String fecha = (String) datos.get("fecha");
            Double diferencia = Double.valueOf(datos.get("diferencia").toString());
            String acceso = (String) datos.get("acceso");
            
            atvffarqService.justificarArqueo(codsuc, codpro, coddoc, fecha, diferencia, acceso);
            
            return ResponseEntity.ok(new ResponseDTO(true,"Arqueo Justificado"));
        } catch (IllegalArgumentException e) {
        	return ResponseEntity.ok(new ResponseDTO(true," Error Arqueo Justificado"));
        } catch (Exception e) {
        	return ResponseEntity.ok(new ResponseDTO(true," Error Arqueo Justificado"));
        }
    }

    /**
     * Endpoint para quitar la justificación de un arqueo
     * @param datos Datos necesarios para quitar la justificación
     * @return Respuesta de éxito o error
     */
    @PostMapping("/quitar-justificacion")
    public ResponseEntity<ResponseDTO> quitarJustificacion(@RequestBody Map<String, Object> datos) {
        try {
            Integer codsuc = Integer.valueOf(datos.get("codsuc").toString());
            String codpro = (String) datos.get("codpro");
            String coddoc = (String) datos.get("coddoc");
            String fecha = (String) datos.get("fecha");
            Double diferencia = Double.valueOf(datos.get("diferencia").toString());
            String acceso = (String) datos.get("acceso");
            
            atvffarqService.quitarJustificacion(codsuc, codpro, coddoc, fecha, diferencia, acceso);
            
            return ResponseEntity.ok(new ResponseDTO(true,"Se retira Arqueo Justificado"));
        } catch (IllegalArgumentException e) {
        	return ResponseEntity.ok(new ResponseDTO(true," Error Arqueo Justificado"));
        } catch (Exception e) {
        	return ResponseEntity.ok(new ResponseDTO(true," Error Arqueo Justificado"));
        }
    }

	@GetMapping("/procesar")
	public ResponseEntity<List<Atvffcrd>> procesarArqueosDescuadrados() {

		List<Atvffcrd> rangos = atvffarqService.procesarArqueosDescuadrados();

		return ResponseEntity.ok(rangos);

	}
    
	@GetMapping("/procesar/{fechaInicio}")
	public ResponseEntity<List<Atvffcrd>> procesarArqueosDescuadradosDesdeFecha(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {

		List<Atvffcrd> rangos = atvffarqService.procesarArqueosDescuadradosDesdeFecha(fechaInicio);
		return ResponseEntity.ok(rangos);

	}
    
    
	/**
     * Endpoint para obtener los arqueos por sucursal
     * @param usuario Usuario que realiza la consulta
     * @param ano Año de consulta
     * @param mes Mes de consulta
     * @param sucursal Código de sucursal
     * @return Lista de arqueos con sus métricas
     */
	 @GetMapping("/usuarioArea")
	 public ResponseEntity<Page<ArqueoDTO>> obtenerArqueos(
	            @RequestParam String usuario,
	            @RequestParam Integer ano,
	            @RequestParam Integer mes,
	            @RequestParam Integer sucursal,
	            @PageableDefault(size = 20, page = 0) Pageable pageable) {
	        
		 Page<ArqueoDTO> arqueos = atvffarqService.obtenerArqueosPorSucursal(usuario, ano, mes, sucursal, pageable);
	        return ResponseEntity.ok(arqueos);
	 }
	 
	 /**
	     * Endpoint para obtener los totales de arqueos
	     * @param sucursal Código de sucursal
	     * @param ano Año de consulta
	     * @param mes Mes de consulta
	     * @return Totales calculados
	     */
	    @GetMapping("/totales")
	    public ResponseEntity<ArqueoTotalesDTO> obtenerTotales(
	    		@RequestParam String usuario,
	            @RequestParam Integer sucursal,
	            @RequestParam Integer ano,
	            @RequestParam Integer mes) {
	        
	    	ArqueoTotalesDTO totales = atvffarqService.obtenerTotales(usuario, sucursal, ano, mes);
	        return ResponseEntity.ok(totales);
	    }

	    /**
	     * Endpoint para procesar un arqueo seleccionado
	     * @param datos Datos del arqueo a procesar
	     * @return Resumen del arqueo procesado
	     */
	    @PostMapping("/procesar")
	    public ResponseEntity<ArqueoResumenDTO> procesarArqueo(@RequestBody Map<String, Object> datos) {
	        String usuario = (String) datos.get("usuario");
	        String pro = (String) datos.get("pro");
	        String doc = (String) datos.get("doc");
	        String des = (String) datos.get("des");
	        Integer sucursal = Integer.valueOf(datos.get("sucursal").toString());
	        String nomSuc = (String) datos.get("nomSuc");
	        Integer ano = Integer.valueOf(datos.get("ano").toString());
	        Integer mes = Integer.valueOf(datos.get("mes").toString());
	        
	        ArqueoResumenDTO resumen = atvffarqService.procesarArqueo(
	                usuario, pro, doc, des, sucursal, nomSuc, ano, mes);
	        
	        return ResponseEntity.ok(resumen);
	    }
    
}