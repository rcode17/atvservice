package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.ReporteArqueoDTO;

import java.util.List;

public interface Atvrega2Service {

	List<ReporteArqueoDTO> generarReporteArqueos(String zFecha);

	void limpiarDatosTemporales(Integer mes, Integer ano);
}