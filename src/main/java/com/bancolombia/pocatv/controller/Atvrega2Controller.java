package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ReporteArqueoDTO;
import com.bancolombia.pocatv.service.Atvrega2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvrega2")
public class Atvrega2Controller {

	@Autowired
	private Atvrega2Service atvregaService;

	@GetMapping("/reporte-arqueos_test")
	public ResponseEntity<?> generarReporteArqueostest() {
		try {
			List<ReporteArqueoDTO> reporte = atvregaService.generarReporteArqueos(null);

			if (reporte.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(reporte);
		} catch (IllegalArgumentException e) {
			// Para errores de validación o parámetros incorrectos
			return ResponseEntity.badRequest().body("Error en la solicitud: " + e.getMessage());
		} catch (DataAccessException e) {
			// Para errores relacionados con la base de datos
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al acceder a los datos: " + e.getMessage());
		} catch (Exception e) {
			// Para cualquier otra excepción no manejada
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inesperado: " + e.getMessage());
		}
	}

	@GetMapping("/reporte-arqueos")
	public ResponseEntity<?> generarReporteArqueos(@RequestParam(required = false) String zFecha) {
		try {
			List<ReporteArqueoDTO> reporte = atvregaService.generarReporteArqueos(zFecha);

			if (reporte.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(reporte);
		} catch (IllegalArgumentException e) {
			// Para errores de validación o parámetros incorrectos
			return ResponseEntity.badRequest().body("Error en la solicitud: " + e.getMessage());
		} catch (DataAccessException e) {
			// Para errores relacionados con la base de datos
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al acceder a los datos: " + e.getMessage());
		} catch (Exception e) {
			// Para cualquier otra excepción no manejada
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inesperado: " + e.getMessage());
		}
	}

	@DeleteMapping("/limpiar-temporales/{mes}/{ano}")
	public ResponseEntity<Void> limpiarDatosTemporales(@PathVariable Integer mes, @PathVariable Integer ano) {
		atvregaService.limpiarDatosTemporales(mes, ano);
		return ResponseEntity.ok().build();
	}
}