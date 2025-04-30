package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.Atvrcri2Service;
import com.bancolombia.pocatv.utils.RangoUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Atvrcri2ServiceImpl implements Atvrcri2Service {

	@Autowired
	private AtvffarqRepository atvffarqRepository;

	@Autowired
	private AtvffcriRepository atvffcriRepository;

	@Autowired
	private AtvffPdsRepository atvffpdsRepository;

	@Override
	@Transactional
	public List<AtvffCri> generarArchivoRangosInconsistentes(Integer mes, Integer anno) {
		// Validar parámetros
		if (mes == null || mes < 1 || mes > 12) {
			throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
		}

		if (anno == null || anno < 2000) {
			throw new IllegalArgumentException("El año debe ser mayor o igual a 2000");
		}

		// Obtener registros de arqueos para el mes y año especificados
		List<Atvffarq> arqueos = atvffarqRepository.findByYearAndMonth(anno, mes);

		if (arqueos.isEmpty()) {
			throw new IllegalArgumentException("No hay registros de arqueos para el mes " + mes + " y año " + anno);
		}

		// Lista para almacenar los criterios generados
		List<AtvffCri> criteriosGenerados = new ArrayList<>();

		// Procesar cada arqueo
		for (Atvffarq arqueo : arqueos) {
			// Verificar si el resultado es RC o RD
			if ("RC".equals(arqueo.getAqres()) || "RD".equals(arqueo.getAqres())) {
				// Calcular la diferencia de rangos
				int diferencia = calcularDiferenciaRangos(arqueo);

				// Si hay diferencia, crear un registro de criterio
				if (diferencia > 0) {
					// Verificar si existe el registro en la sucursal/producto/documento
					Optional<AtvffPds> pdsOpt = atvffpdsRepository.findByXscosuAndXscoprAndXscodo(
							arqueo.getAqcdsu(), arqueo.getAqcopr(), arqueo.getAqcodo());

					if (pdsOpt.isPresent()) {
						// Crear nuevo registro de criterio
						AtvffCri criterio = new AtvffCri();

						// Crear y configurar el ID compuesto
						AtvffCriId id = new AtvffCriId();
						id.setCrano(anno);
						id.setCrmes(mes);
						id.setCrcodsuc(arqueo.getAqcdsu());
						id.setCrcopr(arqueo.getAqcopr());
						id.setCrcodo(arqueo.getAqcodo());

						// Convertir String a LocalDate para el ID
						LocalDate fechaArqueo = convertirStringALocalDate(arqueo.getAqfear());
						id.setCrfear(fechaArqueo);

						criterio.setId(id);
						criterio.setCrnomsuc(arqueo.getAqsuc());
						criterio.setCrdifer(new BigDecimal(diferencia));
						criterio.setCrcedan(arqueo.getAqcedan());
						criterio.setCrres(arqueo.getAqres());
						criterio.setCrdif(arqueo.getAqdif());

						// Guardar el criterio
						AtvffCri savedCriterio = atvffcriRepository.save(criterio);
						criteriosGenerados.add(savedCriterio);
					}
				}
			}
		}

		return criteriosGenerados;
	}

	private LocalDate convertirStringALocalDate(String fechaString) {
		if (fechaString == null || fechaString.trim().isEmpty()) {
			return LocalDate.now(); // Valor predeterminado si la fecha es nula o vacía
		}

		try {
			// Asumiendo que la fecha está en formato ISO (YYYY-MM-DD)
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
			return LocalDate.parse(fechaString, formatter);
		} catch (Exception e) {
			// Si hay un error al parsear la fecha, devolver la fecha actual
			System.err.println("Error al convertir la fecha: " + fechaString + ". Error: " + e.getMessage());
			return LocalDate.now();
		}
	}

	@Override
	public List<AtvffCri> consultarCriterios(Integer mes, Integer anno) {
		return atvffcriRepository.findByCranoAndCrmes(anno, mes);
	}

	@Override
	public List<AtvffCri> consultarCriteriosPorSucursal(Integer mes, Integer anno, Integer codsuc) {
		return atvffcriRepository.findByCranoAndCrmesAndCrcodsuc(anno, mes, codsuc);
	}

	/**
	 * Calcula la diferencia de rangos para un arqueo
	 * @param arqueo Arqueo a analizar
	 * @return Número de rangos con información
	 */
	private int calcularDiferenciaRangos(Atvffarq arqueo) {
		int diferencia = 0;

		// Verificar cada campo de rango
		if (RangoUtil.tieneValor(arqueo.getAqrain1())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain2())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain3())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain4())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain5())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain6())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain7())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain8())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain9())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain10())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain11())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain12())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain13())) diferencia++;
		if (RangoUtil.tieneValor(arqueo.getAqrain14())) diferencia++;

		return diferencia;
	}

}