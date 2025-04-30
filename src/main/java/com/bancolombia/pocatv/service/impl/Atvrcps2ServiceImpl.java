package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.Atvrcps2Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Atvrcps2ServiceImpl implements Atvrcps2Service {

	@Autowired
	private AtvffcpsRepository atvffcpsRepository;

	@Autowired
	private AtvffPdoRepository atvffpdoRepository;

	@Autowired
	private AtvffFreRepository atvfffreRepository;

	@Autowired
	private AtvffmdRepository atvffmdRepository;

	@Autowired
	private AtvffarqRepository atvffarqRepository;

	@Autowired
	private XbknamRepository xbknamRepository;

	@Override
	@Transactional
	public void procesarActualizacion(Integer mes, Integer ano) {
		// Eliminar registros existentes para el mes y año
		eliminarRegistros(mes, ano);

		// Procesar todos los productos
		recorrerArchivos(mes, ano);
	}

	@Override
	public List<Object> consultarResultados(Integer mes, Integer ano) {
		List<Atvffcps> resultados = atvffcpsRepository.findByCsmesAndCsano(mes, ano);
		return resultados.stream()
				.map(r -> Map.of(
						"sucursal", r.getCsnomsuc(),
						"codigoSucursal", r.getCscodsuc(),
						"producto", r.getCscopr(),
						"documento", r.getCscodo(),
						"cumplimiento", r.getCscumpli(),
						"calidad", r.getCscalid()
				))
				.collect(Collectors.toList());
	}

	private void eliminarRegistros(Integer mes, Integer ano) {
		atvffcpsRepository.deleteByCsmesAndCsano(mes, ano);
	}

	private void recorrerArchivos(Integer mes, Integer ano) {
		// Obtener todos los productos
		List<AtvffPdo> productos = atvffpdoRepository.findAll();

		for (AtvffPdo producto : productos) {
			String codigoProducto = producto.getXpcopr();
			String codigoDocumento = producto.getXpcodo();

			// Buscar en archivo lógico de productos
			buscarProductoPorSucursal(codigoProducto, codigoDocumento, mes, ano);
		}
	}

	private void buscarProductoPorSucursal(String codigoProducto, String codigoDocumento, Integer mes, Integer ano) {
		// Aquí implementaríamos la lógica para buscar productos por sucursal
		// Simulando la lógica del RPG original

		// Obtener frecuencia del producto
		Optional<AtvffFre> frecuenciaOpt = atvfffreRepository.findByFxCoprAndFxCodo(codigoProducto, codigoDocumento);

		if (frecuenciaOpt.isPresent()) {
			AtvffFre frecuencia = frecuenciaOpt.get();
			String tipoFrecuencia = frecuencia.getFxFrar();
			Integer arqueosProgramados = frecuencia.getFxDifr();

			// Calcular mes inicial y final según tipo de frecuencia
			int mesInicial = mes;
			int mesFinal = mes;

			switch (tipoFrecuencia) {
				case "T": // Trimestral
					if (mes >= 1 && mes <= 3) {
						mesInicial = 1;
						mesFinal = 3;
					} else if (mes >= 4 && mes <= 6) {
						mesInicial = 4;
						mesFinal = 6;
					} else if (mes >= 7 && mes <= 9) {
						mesInicial = 7;
						mesFinal = 9;
					} else {
						mesInicial = 10;
						mesFinal = 12;
					}
					break;
				case "S": // Semestral
					if (mes >= 1 && mes <= 6) {
						mesInicial = 1;
						mesFinal = 6;
					} else {
						mesInicial = 7;
						mesFinal = 12;
					}
					break;
				case "A": // Anual
					mesInicial = 1;
					mesFinal = 12;
					break;
				default: // Mensual
					break;
			}

			// Obtener información de días de arqueo
			Optional<Atvffmd> mdOpt = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(
					codigoProducto, codigoDocumento, ano, mes.shortValue());

			if (mdOpt.isPresent()) {
				Atvffmd md = mdOpt.get();
				int diaFinal = md.getMddia();
				int rangoDiaInicial = (diaFinal - md.getMdrang1()) + 1;

				// Obtener sucursales con este producto
				// Aquí simularíamos la obtención de sucursales desde ATVLFPRO
				// Para este ejemplo, usaremos una lista fija de sucursales
				List<Integer> sucursales = Arrays.asList(1, 2, 3, 4, 5);

				for (Integer sucursal : sucursales) {
					// Calcular arqueos y porcentajes
					Map<String, Object> resultado = calcularArqueosYPorcentajes(
							sucursal, codigoProducto, codigoDocumento,
							ano, mesInicial, mesFinal,
							rangoDiaInicial, diaFinal, arqueosProgramados);

					// Guardar resultado
					guardarResultado(sucursal, codigoProducto, codigoDocumento, mes, ano,
							(Integer) resultado.get("cumplimiento"),
							(Integer) resultado.get("calidad"));
				}
			}
		}
	}

	private Map<String, Object> calcularArqueosYPorcentajes(
			Integer sucursal, String codigoProducto, String codigoDocumento,
			Integer ano, Integer mesInicial, Integer mesFinal,
			Integer rangoDiaInicial, Integer diaFinal, Integer arqueosProgramados) {

		// Fechas para el rango de búsqueda
		LocalDate fechaInicio = LocalDate.of(ano, mesInicial, 1);
		LocalDate fechaFin = YearMonth.of(ano, mesFinal).atEndOfMonth();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fechaInicioStr = fechaInicio.format(formatter);
		String fechaFinStr = fechaFin.format(formatter);

		List<Atvffarq> arqueos = atvffarqRepository.findByAqcoprAndAqcodoAndAqfearBetween(
				codigoProducto, codigoDocumento, fechaInicioStr, fechaFinStr);

		// Filtrar arqueos por sucursal
		List<Atvffarq> arqueosSucursal = arqueos.stream()
				.filter(a -> a.getAqcdsu().equals(sucursal))
				.collect(Collectors.toList());

		// Contar arqueos ejecutados
		int arqueosEjecutados = arqueosSucursal.size();

		// Contar arqueos cuadrados (con resultado "C" o justificación "S")
		long arqueosCuadrados = arqueosSucursal.stream()
				.filter(a -> "C".equals(a.getAqres()) || "S".equals(a.getAqjust()))
				.count();

		// Calcular porcentajes
		int porcentajeCumplimiento = arqueosProgramados > 0
				? (int) Math.round((double) arqueosEjecutados / arqueosProgramados * 100)
				: 0;

		int porcentajeCalidad = arqueosEjecutados > 0
				? (int) Math.round((double) arqueosCuadrados / arqueosEjecutados * 100)
				: 0;

		return Map.of(
				"cumplimiento", porcentajeCumplimiento,
				"calidad", porcentajeCalidad
		);
	}

	private void guardarResultado(
			Integer sucursal, String codigoProducto, String codigoDocumento,
			Integer mes, Integer ano, Integer cumplimiento, Integer calidad) {

		// Buscar nombre de sucursal
		Optional<Xbknam> sucursalOpt = xbknamRepository.findByXnnmky(sucursal);
		String nombreSucursal = sucursalOpt.map(Xbknam::getXnname).orElse("");

		// Crear y guardar entidad
		Atvffcps resultado = new Atvffcps();
		resultado.setCsano(ano);
		resultado.setCsmes(mes);
		resultado.setCscopr(codigoProducto);
		resultado.setCscodo(codigoDocumento);
		resultado.setCscodsuc(sucursal);
		resultado.setCsnomsuc(nombreSucursal);
		resultado.setCscumpli(cumplimiento);
		resultado.setCscalid(calidad);

		atvffcpsRepository.save(resultado);
	}
}