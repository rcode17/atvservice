package com.bancolombia.pocatv.service.impl;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		//clrpfmTablesUtils.clearAtvfftem();
    	
    	 // Formateador para fechas en formato ddMMyyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		String fechaInicioStr = String.format("%s-%s-%s", anoInicio, mesInicio, diaInicio);
		String fechaFinStr = String.format("%s-%s-%s", anoFin, mesFin, diaFin);


		Date fechaInicio = Date.valueOf(fechaInicioStr);
		Date fechaFin = Date.valueOf(fechaFinStr);

// Obtener registros en el rango de fechas
		List<Atvffrec> rechazos = atvffrecRepository.findByRcfearBetweenOrderByRcfear(fechaInicio, fechaFin);
		int registrosProcesados = 0;
		
		if (rechazos.isEmpty()) {
		    throw new IllegalArgumentException("No se encontraron registros en el rango de fechas especificado.");
		}
		
		for (Atvffrec rechazo : rechazos) {
			// Crear registro temporal
			Atvfftem temporal = new Atvfftem();
		
			// Mapear todos los campos del rechazo al temporal
			// Convertir el campo Rcfere (String) a LocalDate usando el formateador
	        temporal.setTmfear(LocalDate.parse(rechazo.getRcfere(), formatter));
			temporal.setTmsuc(rechazo.getRcsuc());
			temporal.setTmcdsu(rechazo.getRccdsu());
			temporal.setTmcdsuf(rechazo.getRccdsuf());
	        temporal.setTmsuc(rechazo.getRcsuc());   // Sucursal
	        temporal.setTmcdsu(rechazo.getRccdsu()); // Código de sucursal
	        temporal.setTmcdsuf(rechazo.getRccdsuf()); // Código de sucursal final
	        temporal.setTmprcu(rechazo.getRcprcu()); // Precio unitario
	        temporal.setTmcedcn(rechazo.getRccedcn()); // Código de documento
	        temporal.setTmpear(rechazo.getRcpear()); // Periodo del rechazo
	        temporal.setTmcedan(rechazo.getRccedan()); // Código de análisis
	        temporal.setTmsubg(rechazo.getRcsubg()); // Subgrupo
	        temporal.setTmcesbn(rechazo.getRccesbn()); // Código de subgrupo
	        temporal.setTmcopr(rechazo.getRccopr()); // Código de operación
	        temporal.setTmcodo(rechazo.getRccodo()); // Código de documento original
	        temporal.setTmsfar(rechazo.getRcsfar()); // Fecha de archivo
	        temporal.setTmdif(rechazo.getRcdif());   // Diferencia
	        temporal.setTmres(rechazo.getRcres());   // Resultado
	        temporal.setTmobs(rechazo.getRcobsn());  // Observación
	        temporal.setTmobso(rechazo.getRcobsno()); // Observación adicional
	        temporal.setTmsfeb(rechazo.getRcsfeb()); // Fecha de balance
	        temporal.setTmdeb(rechazo.getRcdeb());   // Débito
	        temporal.setTmsfev(rechazo.getRcsfev()); // Fecha de vencimiento
	        temporal.setTmdev(rechazo.getRcdev());   // Débito vencido
	        temporal.setTmsfet(rechazo.getRcsfet()); // Fecha de transacción
	        temporal.setTmtrans(rechazo.getRctrans()); // Transacción
	        temporal.setTmhora(rechazo.getRchora()); // Hora de transacción

	        // Mapear los campos de las 13 posibles combinaciones de RAIN, RAFN y COCU
	        temporal.setTmrain1(rechazo.getRcrain1());
	        temporal.setTmrafn1(rechazo.getRcrafn1());
	        temporal.setTmcocu1(rechazo.getRccocu1());
	        temporal.setTmrain2(rechazo.getRcrain2());
	        temporal.setTmrafn2(rechazo.getRcrafn2());
	        temporal.setTmcocu2(rechazo.getRccocu2());
	        temporal.setTmrain3(rechazo.getRcrain3());
	        temporal.setTmrafn3(rechazo.getRcrafn3());
	        temporal.setTmcocu3(rechazo.getRccocu3());
	        temporal.setTmrain4(rechazo.getRcrain4());
	        temporal.setTmrafn4(rechazo.getRcrafn4());
	        temporal.setTmcocu4(rechazo.getRccocu4());
	        temporal.setTmrain5(rechazo.getRcrain5());
	        temporal.setTmrafn5(rechazo.getRcrafn5());
	        temporal.setTmcocu5(rechazo.getRccocu5());
	        temporal.setTmrain6(rechazo.getRcrain6());
	        temporal.setTmrafn6(rechazo.getRcrafn6());
	        temporal.setTmcocu6(rechazo.getRccocu6());
	        temporal.setTmrain7(rechazo.getRcrain7());
	        temporal.setTmrafn7(rechazo.getRcrafn7());
	        temporal.setTmcocu7(rechazo.getRccocu7());
	        temporal.setTmrain8(rechazo.getRcrain8());
	        temporal.setTmrafn8(rechazo.getRcrafn8());
	        temporal.setTmcocu8(rechazo.getRccocu8());
	        temporal.setTmrain9(rechazo.getRcrain9());
	        temporal.setTmrafn9(rechazo.getRcrafn9());
	        temporal.setTmcocu9(rechazo.getRccocu9());
	        temporal.setTmrain10(rechazo.getRcrain10());
	        temporal.setTmrafn10(rechazo.getRcrafn10());
	        temporal.setTmcocu10(rechazo.getRccocu10());
	        temporal.setTmrain11(rechazo.getRcrain11());
	        temporal.setTmrafn11(rechazo.getRcrafn11());
	        temporal.setTmcocu11(rechazo.getRccocu11());
	        temporal.setTmrain12(rechazo.getRcrain12());
	        temporal.setTmrafn12(rechazo.getRcrafn12());
	        temporal.setTmcocu12(rechazo.getRccocu12());
	        temporal.setTmrain13(rechazo.getRcrain13());
	        temporal.setTmrafn13(rechazo.getRcrafn13());
	        temporal.setTmcocu13(rechazo.getRccocu13());
	        
			
			// Guardar en archivo temporal
			atvfftemRepository.save(temporal);
			
			// Eliminar registro original
			atvffrecRepository.delete(rechazo);
			registrosProcesados++;
		}
		
		return registrosProcesados;
	}
	
	    
}