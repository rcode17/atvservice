package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Atvffcharq;
import com.bancolombia.pocatv.model.Atvffchsal;
import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.repository.AtvffcharqRepository;
import com.bancolombia.pocatv.repository.AtvffchsalRepository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.service.AtvrcarajdService;

import jakarta.transaction.Transactional;

@Service
public class AtvrcarajdServiceImpl implements AtvrcarajdService {

	private final AtvffcharqRepository atvffcharqRepository;
	private final AtvfftemRepository atvfftemRepository;

	private final AtvffchsalRepository atvffchsalRepository;

	@Autowired
	public AtvrcarajdServiceImpl(AtvffcharqRepository atvffcharqRepository, AtvfftemRepository atvfftemRepository,
			AtvffchsalRepository atvffchsalRepository) {
		this.atvffcharqRepository = atvffcharqRepository;
		this.atvfftemRepository = atvfftemRepository;
		this.atvffchsalRepository = atvffchsalRepository;
	}

	@Override
	@Transactional
	public int procesarAtvrcarajd() {
		List<Atvffcharq> arqueos = atvffcharqRepository.findAll();
		int contador = 0;

		for (Atvffcharq arqueo : arqueos) {
			try {
				Atvfftem temArqueo = new Atvfftem();

				// Convertir fecha de formato numérico a String en formato ISO
				if (arqueo.getFechaArqueo() != null) {
					String fechaStr = String.format("%08d", arqueo.getFechaArqueo());
					LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.BASIC_ISO_DATE);
					// Convertir LocalDate a String antes de asignarlo
					temArqueo.setTmfear(fecha.format(DateTimeFormatter.ISO_DATE));
				} else {
					temArqueo.setTmfear(LocalDate.now().format(DateTimeFormatter.ISO_DATE)); // Valor por defecto
				}

				// Asignar valores desde arqueo a temArqueo
				temArqueo.setTmsuc(arqueo.getSucursal());
				temArqueo.setTmcdsu(arqueo.getCodigoSucursal());
				temArqueo.setTmcdsuf(arqueo.getCodigoSucursal()); // Mismo valor que tmcdsu
				temArqueo.setTmprcu(arqueo.getResponsableCustodia());

				// Estos campos no están en tu modelo simplificado, así que usamos valores por
				// defecto
				temArqueo.setTmcedcn("");
				temArqueo.setTmpear("");
				temArqueo.setTmcedan("");
				temArqueo.setTmsubg("");
				temArqueo.setTmcesbn("");
				temArqueo.setTmcopr(""); // Código de producto
				temArqueo.setTmcodo(""); // Código de documento

				// Validar valores desbordados para RQSFAR
				if (arqueo.getSaldoFisicoArqueo() != null) {
					BigDecimal sfar = BigDecimal.valueOf(arqueo.getSaldoFisicoArqueo());
					if (sfar.compareTo(new BigDecimal("9999999999999")) > 0) {
						temArqueo.setTmsfar(BigDecimal.ZERO);
						temArqueo.setTmdif(BigDecimal.ZERO);
					} else {
						temArqueo.setTmsfar(sfar);

						// Como no tenemos el campo rqsig en el modelo simplificado,
						// asumimos que la diferencia ya tiene el signo correcto
						if (arqueo.getDiferencia() != null) {
							temArqueo.setTmdif(BigDecimal.valueOf(arqueo.getDiferencia()));
						} else {
							temArqueo.setTmdif(BigDecimal.ZERO);
						}
					}
				} else {
					temArqueo.setTmsfar(BigDecimal.ZERO);
					temArqueo.setTmdif(BigDecimal.ZERO);
				}

				// Resultado no está en el modelo simplificado
				temArqueo.setTmres("");

				temArqueo.setTmobs(arqueo.getObservaciones());
				temArqueo.setTmobso(arqueo.getRqobso());

				// Asignar valores de rangos
				temArqueo.setTmrain1(arqueo.getRqrain1());
				temArqueo.setTmrafn1(arqueo.getRqrafn1());
				temArqueo.setTmcocu1(arqueo.getRqcocu1());

				temArqueo.setTmrain2(arqueo.getRqrain2());
				temArqueo.setTmrafn2(arqueo.getRqrafn2());
				temArqueo.setTmcocu2(arqueo.getRqcocu2());

				temArqueo.setTmrain3(arqueo.getRqrain3());
				temArqueo.setTmrafn3(arqueo.getRqrafn3());
				temArqueo.setTmcocu3(arqueo.getRqcocu3());

				temArqueo.setTmrain4(arqueo.getRqrain4());
				temArqueo.setTmrafn4(arqueo.getRqrafn4());
				temArqueo.setTmcocu4(arqueo.getRqcocu4());

				temArqueo.setTmrain5(arqueo.getRqrain5());
				temArqueo.setTmrafn5(arqueo.getRqrafn5());
				temArqueo.setTmcocu5(arqueo.getRqcocu5());

				temArqueo.setTmrain6(arqueo.getRqrain6());
				temArqueo.setTmrafn6(arqueo.getRqrafn6());
				temArqueo.setTmcocu6(arqueo.getRqcocu6());

				temArqueo.setTmrain7(arqueo.getRqrain7());
				temArqueo.setTmrafn7(arqueo.getRqrafn7());
				temArqueo.setTmcocu7(arqueo.getRqcocu7());

				temArqueo.setTmrain8(arqueo.getRqrain8());
				temArqueo.setTmrafn8(arqueo.getRqrafn8());
				temArqueo.setTmcocu8(arqueo.getRqcocu8());

				temArqueo.setTmrain9(arqueo.getRqrain9());
				temArqueo.setTmrafn9(arqueo.getRqrafn9());
				temArqueo.setTmcocu9(arqueo.getRqcocu9());

				temArqueo.setTmrain10(arqueo.getRqrain10());
				temArqueo.setTmrafn10(arqueo.getRqrafn10());
				temArqueo.setTmcocu10(arqueo.getRqcocu10());

				temArqueo.setTmrain11(arqueo.getRqrain11());
				temArqueo.setTmrafn11(arqueo.getRqrafn11());
				temArqueo.setTmcocu11(arqueo.getRqcocu11());

				temArqueo.setTmrain12(arqueo.getRqrain12());
				temArqueo.setTmrafn12(arqueo.getRqrafn12());
				temArqueo.setTmcocu12(arqueo.getRqcocu12());

				temArqueo.setTmrain13(arqueo.getRqrain13());
				temArqueo.setTmrafn13(arqueo.getRqrafn13());
				temArqueo.setTmcocu13(arqueo.getRqcocu13());

				// Inicializar campos adicionales a cero
				temArqueo.setTmsfeb(BigDecimal.ZERO);
				temArqueo.setTmdeb(BigDecimal.ZERO);
				temArqueo.setTmsfev(BigDecimal.ZERO);
				temArqueo.setTmdev(BigDecimal.ZERO);
				temArqueo.setTmsfet(BigDecimal.ZERO);
				temArqueo.setTmhora(0);
				temArqueo.setTmtrans("0144"); // En el código original se asignaba '0144'

				// Guardar el registro temporal
				atvfftemRepository.save(temArqueo);
				contador++;
			} catch (Exception e) {
				// Manejar la excepción de manera adecuada
				System.err.println("Error al procesar el arqueo: " + e.getMessage());
				// Opcionalmente, puedes lanzar una excepción personalizada
				// throw new RuntimeException("Error al procesar el arqueo", e);
			}
		}

		return contador;
	}

	@Override
	@Transactional
	public void procesarCargaJDE() {
		// Esta función replica la lógica del programa RPG original
		List<Atvffchsal> registros = atvffchsalRepository.findAll();

		for (Atvffchsal registro : registros) {
			// Buscar si existe un registro correspondiente en la vista lógica
			Optional<Atvffchsal> registroExistente = atvffchsalRepository.findBySdeofcoAndSdtproAndSdtdocAndSdfech(registro.getSdeofco(),
					registro.getSdtpro(), registro.getSdtdoc(), registro.getSdfech());

			if (registroExistente.isPresent()) {
				// Actualizar el registro existente
				Atvffchsal regActualizar = registroExistente.get();
				regActualizar.setSddisp(registro.getSddisp());
				regActualizar.setSdeofic(registro.getSdeofic());
				atvffchsalRepository.save(regActualizar);
			} else {
				// Crear un nuevo registro
				Atvffchsal nuevoRegistro = new Atvffchsal();
				nuevoRegistro.setSdeofco(registro.getSdeofco());
				nuevoRegistro.setSdtpro(registro.getSdtpro());
				nuevoRegistro.setSdtdoc(registro.getSdtdoc());
				nuevoRegistro.setSdfech(registro.getSdfech());
				nuevoRegistro.setSddisp(registro.getSddisp());
				nuevoRegistro.setSdeofic(registro.getSdeofic());
				atvffchsalRepository.save(nuevoRegistro);
			}
		}
	}

}
