package com.bancolombia.pocatv.service.impl;

import java.sql.Date;
import java.util.List;

import com.bancolombia.pocatv.utils.ClrpfmTablesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.model.Atvffrec;


import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.repository.AtvffrecRepository;

import com.bancolombia.pocatv.service.AtvrRepService;

import jakarta.transaction.Transactional;


@Service
public class AtvrRepServiceImpl implements AtvrRepService {

	@Autowired
    private AtvffrecRepository atvffrecRepository;

    @Autowired
    private AtvfftemRepository atvfftemRepository;

	@Autowired
	private ClrpfmTablesUtils clrpfmTablesUtils;

    
    @Override
    @Transactional
    public int procesarRechazos(String usuario, String mesInicio, String diaInicio, String anoInicio, 
            String mesFin, String diaFin, String anoFin) {
		clrpfmTablesUtils.clearAtvfftem();

		String fechaInicioStr = String.format("%s-%s-%s", anoInicio, mesInicio, diaInicio);
		String fechaFinStr = String.format("%s-%s-%s", anoFin, mesFin, diaFin);


		Date fechaInicio = Date.valueOf(fechaInicioStr);
		Date fechaFin = Date.valueOf(fechaFinStr);

// Obtener registros en el rango de fechas
		List<Atvffrec> rechazos = atvffrecRepository.findByRcfearBetweenOrderByRcfear(fechaInicio, fechaFin);
		int registrosProcesados = 0;
		
		for (Atvffrec rechazo : rechazos) {
			// Crear registro temporal
			Atvfftem temporal = new Atvfftem();
		
			// Mapear todos los campos del rechazo al temporal
			//temporal.setTmfear(rechazo.getRcfere());
			temporal.setTmsuc(rechazo.getRcsuc());
			temporal.setTmcdsu(rechazo.getRccdsu());
			temporal.setTmcdsuf(rechazo.getRccdsuf());
			
			// Guardar en archivo temporal
			atvfftemRepository.save(temporal);
			
			// Eliminar registro original
			atvffrecRepository.delete(rechazo);
			registrosProcesados++;
		}
		
		return registrosProcesados;
	}

	    
}