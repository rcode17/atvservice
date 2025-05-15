package com.bancolombia.pocatv.service.impl;


import com.bancolombia.pocatv.dto.EstadisticaRegionDTO;
import com.bancolombia.pocatv.dto.ParametrosConsultaDTO;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvriaaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtvriaaServiceImpl implements AtvriaaService {

	@Autowired
	private AtvffiaaRepository atvffiaaRepository;

	@Autowired
	private AtvffarqRepository atvffarqRepository;

	@Autowired
	private AtvffFreRepository atvfffreRepository;

	@Autowired
	private AtvffmdRepository atvffmdRepository;

	@Autowired
	private AtvffPdoRepository atvffpdoRepository;

	@Autowired
	private XbknamRepository xbknamRepository;

	@Autowired
	private XmgregRepository xmgregRepository;

	@Override
	@Transactional
	public List<EstadisticaRegionDTO> generarEstadisticasPorRegion(Integer ano , String fechaStr) {
		//Integer ano = parametros.getAno();
		//String fechaStr = parametros.getFecha();

		// Limpiar estadísticas previas
		limpiarEstadisticas(ano);

		// Obtener fecha actual si no se proporciona
		LocalDate fecha;
		if (fechaStr != null && !fechaStr.isEmpty()) {
			fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);
		} else {
			fecha = LocalDate.now();
		}

		int year = fecha.getYear();
		int month = fecha.getMonthValue();

		// Obtener todas las regiones
		List<Xmgreg> regiones = xmgregRepository.findAll();

		// Resultados a devolver
		List<EstadisticaRegionDTO> resultados = new ArrayList<>();

		// Procesar cada región
		for (Xmgreg region : regiones) {
			EstadisticaRegionDTO estadistica = procesarRegion(region, ano, month, year);
			if (estadistica != null) {
				resultados.add(estadistica);
				guardarEstadistica(estadistica, ano);
			}
		}

		return resultados;
	}

	private EstadisticaRegionDTO procesarRegion(Xmgreg region, Integer ano, int mesActual, int yearActual) {
		EstadisticaRegionDTO estadistica = new EstadisticaRegionDTO();
		estadistica.setNombre(region.getXmnmr());
		estadistica.setRegion(region.getXmcdmr().toString());

		// Obtener sucursales de la región
		List<Xbknam> sucursales = xbknamRepository.findByXncdmr(region.getXmcdmr());

		// Inicializar valores mensuales
		estadistica.setEnero(0);
		estadistica.setFeb(0);
		estadistica.setMarzo(0);
		estadistica.setAbril(0);
		estadistica.setMayo(0);
		estadistica.setJunio(0);
		estadistica.setJulio(0);
		estadistica.setAgosto(0);
		estadistica.setSep(0);
		estadistica.setOctubre(0);
		estadistica.setNov(0);
		estadistica.setDic(0);

		// Determinar hasta qué mes procesar
		int mesLimite = (yearActual == ano) ? mesActual : 12;

		// Calcular estadísticas para cada mes
		int totalMeses = 0;
		int sumaCalidad = 0;

		for (int mes = 1; mes <= mesLimite; mes++) {
			int calidad = calcularCalidadMes(region, sucursales, ano, mes);

			// Asignar valor al mes correspondiente
			switch (mes) {
				case 1: estadistica.setEnero(calidad); break;
				case 2: estadistica.setFeb(calidad); break;
				case 3: estadistica.setMarzo(calidad); break;
				case 4: estadistica.setAbril(calidad); break;
				case 5: estadistica.setMayo(calidad); break;
				case 6: estadistica.setJunio(calidad); break;
				case 7: estadistica.setJulio(calidad); break;
				case 8: estadistica.setAgosto(calidad); break;
				case 9: estadistica.setSep(calidad); break;
				case 10: estadistica.setOctubre(calidad); break;
				case 11: estadistica.setNov(calidad); break;
				case 12: estadistica.setDic(calidad); break;
			}

			if (calidad > 0) {
				sumaCalidad += calidad;
				totalMeses++;
			}
		}

		// Calcular promedio
		estadistica.setProma(totalMeses > 0 ? sumaCalidad / totalMeses : 0);

		return estadistica;
	}

	private int calcularCalidadMes(Xmgreg region, List<Xbknam> sucursales, Integer ano, int mes) {
		int totalProductos = 0;
		int totalCalidad = 0;

		// Obtener todos los productos-documentos
		List<AtvffPdo> productos = atvffpdoRepository.findAll();

		for (AtvffPdo producto : productos) {
			String copr = producto.getXpcopr();
			String codo = producto.getXpcodo();

			// Obtener frecuencia de arqueo
			AtvffFre frecuencia = atvfffreRepository.findById_FxCoprAndId_FxCodo(copr, codo);
			if (frecuencia == null) continue;

			int diasFrecuencia = frecuencia.getFxDifr();
			String tipoFrecuencia = frecuencia.getFxFrar();

			// Verificar si aplica para el mes según frecuencia
			if (!aplicaFrecuencia(tipoFrecuencia, mes)) continue;

			int arqueosCuadrados = 0;
			int arqueosEsperados = 0;

			// Procesar cada sucursal de la región
			for (Xbknam sucursal : sucursales) {
				// Obtener arqueos de la sucursal para el producto-documento
				List<Atvffarq> arqueos = atvffarqRepository.findBySucursalAndCoprAndCodo(
						sucursal.getXnnmky(), copr, codo);

				// Filtrar por año y mes
				List<Atvffarq> arqueosMes = arqueos.stream()
						.filter(a -> {
							try {
								// Asumiendo que aqfear está en formato ISO (yyyy-MM-dd)
								LocalDate fecha = LocalDate.parse(a.getAqfear());
								return fecha.getYear() == ano && fecha.getMonthValue() == mes;
							} catch (Exception e) {
								// Manejar posibles errores de formato de fecha
								return false;
							}
						})
						.collect(Collectors.toList());

				// Contar arqueos cuadrados
				long cuadrados = arqueosMes.stream()
						.filter(a -> "C".equals(a.getAqres()))
						.count();

				arqueosEsperados += diasFrecuencia;
				arqueosCuadrados += cuadrados;
			}

			// Calcular calidad para este producto
			if (arqueosEsperados > 0) {
				int calidadProducto = (int) ((arqueosCuadrados * 100) / arqueosEsperados);
				totalCalidad += calidadProducto;
				totalProductos++;
			}
		}

		// Calcular promedio de calidad para todos los productos
		return totalProductos > 0 ? totalCalidad / totalProductos : 0;
	}

	private boolean aplicaFrecuencia(String tipoFrecuencia, int mes) {
		switch (tipoFrecuencia) {
			case "M": // Mensual
				return true;
			case "T": // Trimestral
				return mes == 3 || mes == 6 || mes == 9 || mes == 12;
			case "S": // Semestral
				return mes == 6 || mes == 12;
			case "A": // Anual
				return mes == 12;
			default:
				return false;
		}
	}

	private void guardarEstadistica(EstadisticaRegionDTO estadistica, Integer ano) {
		Atvffiaa atvffiaa = new Atvffiaa();
		atvffiaa.setIaano(ano);
		atvffiaa.setIanombre(estadistica.getNombre());
		atvffiaa.setIaregion(estadistica.getRegion());
		atvffiaa.setIaenero(estadistica.getEnero());
		atvffiaa.setIafeb(estadistica.getFeb());
		atvffiaa.setIamartzo(estadistica.getMarzo());
		atvffiaa.setIaabril(estadistica.getAbril());
		atvffiaa.setIamay(estadistica.getMayo());
		atvffiaa.setIajunio(estadistica.getJunio());
		atvffiaa.setIajulio(estadistica.getJulio());
		atvffiaa.setIaagosto(estadistica.getAgosto());
		atvffiaa.setIasep(estadistica.getSep());
		atvffiaa.setIaoctubre(estadistica.getOctubre());
		atvffiaa.setIanov(estadistica.getNov());
		atvffiaa.setIadic(estadistica.getDic());
		atvffiaa.setIaproma(estadistica.getProma());

		atvffiaaRepository.save(atvffiaa);
	}

	@Override
	@Transactional
	public void limpiarEstadisticas(Integer ano) {
		List<Atvffiaa> estadisticas = atvffiaaRepository.findByIaano(ano);
		atvffiaaRepository.deleteAll(estadisticas);
	}

}