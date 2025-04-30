package com.bancolombia.pocatv.service;


import com.bancolombia.pocatv.model.Atvffcaa;

import java.util.List;
import java.util.Optional;

public interface AtvrcaaService {
	List<Atvffcaa> procesarDatosAnuales(Integer ano);
	List<Atvffcaa> obtenerDatosPorAno(Integer ano);
	void eliminarDatosPorAno(Integer ano);
}
