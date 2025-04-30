package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.model.AtvffCri;
import com.bancolombia.pocatv.service.Atvrcri2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvrcri2")
public class Atvrcri2Controller {

	@Autowired
	private Atvrcri2Service atvrcri2Service;

	/**
	 * Genera el archivo de rangos inconsistentes para un mes y año específicos
	 * @param mes Mes para el cual se generarán los rangos
	 * @param anno Año para el cual se generarán los rangos
	 * @return Lista de registros de criterios generados
	 */
	@PostMapping("/generar-rangos-inconsistentes")
	public ResponseEntity<List<AtvffCri>> generarRangosInconsistentes(
			@RequestParam Integer mes,
			@RequestParam Integer anno) {
		try {
			List<AtvffCri> criterios = atvrcri2Service.generarArchivoRangosInconsistentes(mes, anno);
			return ResponseEntity.ok(criterios);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Consulta los registros de criterios para un mes y año específicos
	 * @param mes Mes a consultar
	 * @param anno Año a consultar
	 * @return Lista de registros de criterios
	 */
	@GetMapping("/criterios")
	public ResponseEntity<List<AtvffCri>> consultarCriterios(
			@RequestParam Integer mes,
			@RequestParam Integer anno) {
		List<AtvffCri> criterios = atvrcri2Service.consultarCriterios(anno, mes);
		return ResponseEntity.ok(criterios);
	}

	/**
	 * Consulta los registros de criterios para un mes, año y sucursal específicos
	 * @param mes Mes a consultar
	 * @param anno Año a consultar
	 * @param codsuc Código de sucursal
	 * @return Lista de registros de criterios
	 */
	@GetMapping("/criterios/sucursal/{codsuc}")
	public ResponseEntity<List<AtvffCri>> consultarCriteriosPorSucursal(
			@RequestParam Integer mes,
			@RequestParam Integer anno,
			@PathVariable Integer codsuc) {
		List<AtvffCri> criterios = atvrcri2Service.consultarCriteriosPorSucursal(anno, mes, codsuc);
		return ResponseEntity.ok(criterios);
	}
}