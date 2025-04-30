package com.bancolombia.pocatv.service;

public interface AtvrRepService {
	int procesarRechazos(String usuario, String mesInicio, String diaInicio, String anoInicio, 
            String mesFin, String diaFin, String anoFin);
}
