package com.bancolombia.pocatv.service.impl;


import com.bancolombia.pocatv.dto.ResultadoProcesamientoDTO;
import com.bancolombia.pocatv.model.Atvffmearq;
import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.repository.AtvffmearqRepository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.service.AtvffmearqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio para gestionar operaciones relacionadas con los arqueos
 */
@Service
public class AtvffmearqServiceImpl implements AtvffmearqService {

	private static final Logger logger = LoggerFactory.getLogger(AtvffmearqServiceImpl.class);
    private static final int BATCH_SIZE = 1000; // Tamaño del lote para procesamiento
    
    private final AtvffmearqRepository atvffmearqRepository;
    private final AtvfftemRepository atvfftemRepository;

    @Autowired
    public AtvffmearqServiceImpl(AtvffmearqRepository atvffmearqRepository, AtvfftemRepository atvfftemRepository) {
        this.atvffmearqRepository = atvffmearqRepository;
        this.atvfftemRepository = atvfftemRepository;
    }

    @Override
    @Transactional
    public ResultadoProcesamientoDTO procesarArqueos() {
        logger.info("Iniciando procesamiento de arqueos");
        
        ResultadoProcesamientoDTO resultado = new ResultadoProcesamientoDTO();
        
        // Contar total de registros
        long totalRegistros = atvffmearqRepository.count();
        if (totalRegistros == 0) {
            throw new IllegalArgumentException("No hay registros de arqueos para procesar");
        }
        
        resultado.setTotalRegistros((int) totalRegistros);
        logger.info("Total de registros a procesar: {}", totalRegistros);
        
        // Procesar en lotes para evitar problemas de memoria
        int pageNumber = 0;
        boolean hasMorePages = true;
        
        while (hasMorePages) {
            // Obtener un lote de registros
            Page<Atvffmearq> pageArqueos = atvffmearqRepository.findAll(PageRequest.of(pageNumber, BATCH_SIZE));
            List<Atvffmearq> arqueos = pageArqueos.getContent();
            
            if (arqueos.isEmpty()) {
                hasMorePages = false;
                continue;
            }
            
            logger.info("Procesando lote {} con {} registros", pageNumber + 1, arqueos.size());
            
            // Lista para almacenar registros procesados antes de guardarlos en lote
            List<Atvfftem> registrosProcesados = new ArrayList<>();
            
            // Procesar cada arqueo en el lote actual
            for (Atvffmearq arqueo : arqueos) {
                try {
                    Atvfftem temporal = procesarArqueo(arqueo);
                    registrosProcesados.add(temporal);
                    resultado.incrementarExitosos();
                } catch (Exception e) {
                    resultado.incrementarFallidos();
                    String mensajeError = "Error al procesar arqueo con ID [" + 
                                         arqueo.getRqcdsu() + ", " + 
                                         arqueo.getRqcopr() + ", " + 
                                         arqueo.getRqcodo() + ", " + 
                                         arqueo.getRqfear() + "]: " + e.getMessage();
                    logger.error(mensajeError, e);
                    resultado.agregarError(mensajeError);
                }
            }
            
            // Guardar los registros procesados en lote
            try {
                atvfftemRepository.saveAll(registrosProcesados);
                logger.info("Guardados {} registros del lote {}", registrosProcesados.size(), pageNumber + 1);
            } catch (Exception e) {
                logger.error("Error al guardar lote de registros: {}", e.getMessage(), e);
                // Intentar guardar uno por uno para identificar registros problemáticos
                for (Atvfftem temporal : registrosProcesados) {
                    try {
                        atvfftemRepository.save(temporal);
                        // No incrementamos exitosos porque ya lo hicimos antes
                    } catch (Exception ex) {
                        resultado.incrementarFallidos();
                        resultado.decrementarExitosos(); // Corregir el contador
                        String mensajeError = "Error al guardar registro temporal: " + ex.getMessage();
                        logger.error(mensajeError, ex);
                        resultado.agregarError(mensajeError);
                    }
                }
            }
            
            pageNumber++;
            hasMorePages = pageArqueos.hasNext();
        }
        
        logger.info("Procesamiento de arqueos completado. {}", resultado);
        return resultado;
    }
    
    /**
     * Procesa un único registro de arqueo
     */
    private Atvfftem procesarArqueo(Atvffmearq arqueo) {
        Atvfftem temporal = new Atvfftem();
        
        // Mapear campos según la lógica del programa RPG
        // Campos de la clave primaria
        temporal.setTmcdsu(arqueo.getRqcdsu());
        temporal.setTmcopr(arqueo.getRqcopr());
        temporal.setTmcodo(arqueo.getRqcodo());
        
        // Convertir fecha
        try {
            String fechaIso = String.valueOf(arqueo.getRqfear());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate fecha = LocalDate.parse(fechaIso, formatter);
            temporal.setTmfear(fecha);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido: " + arqueo.getRqfear(), e);
        }
        
        // Resto de campos
        temporal.setTmsuc(arqueo.getRqsuc());
        temporal.setTmcdsuf(arqueo.getRqcdsuf());
        temporal.setTmprcu(arqueo.getRqprcu());
        temporal.setTmcedcn(arqueo.getRqcedcn());
        temporal.setTmpear(arqueo.getRqpear());
        temporal.setTmcedan(arqueo.getRqcedan());
        temporal.setTmsubg(arqueo.getRqsubg());
        temporal.setTmcesbn(arqueo.getRqcesbn());
        temporal.setTmsfar(BigDecimal.valueOf(arqueo.getRqsfar()));
        
        // Manejar la lógica del signo de diferencia
        if ("+".equals(arqueo.getRqsig())) {
            temporal.setTmdif(BigDecimal.valueOf(arqueo.getRqdif()));
        } else {
            temporal.setTmdif(BigDecimal.valueOf(arqueo.getRqdif()).negate());
        }
        
        temporal.setTmres(arqueo.getRqres());
        temporal.setTmobs(arqueo.getRqobs());
        temporal.setTmobso(arqueo.getRqobso());
        
        // Mapear rangos y códigos de cuenta
        temporal.setTmrain1(arqueo.getRqrain1());
        temporal.setTmrafn1(arqueo.getRqrafn1());
        temporal.setTmcocu1(arqueo.getRqcocu1());
        temporal.setTmrain2(arqueo.getRqrain2());
        temporal.setTmrafn2(arqueo.getRqrafn2());
        temporal.setTmcocu2(arqueo.getRqcocu2());
        temporal.setTmrain3(arqueo.getRqrain3());
        temporal.setTmrafn3(arqueo.getRqrafn3());
        temporal.setTmcocu3(arqueo.getRqcocu3());
        temporal.setTmrain4(arqueo.getRqrain4());
        temporal.setTmrafn4(arqueo.getRqrafn4());
        temporal.setTmcocu4(arqueo.getRqcocu4());
        temporal.setTmrain5(arqueo.getRqrain5());
        temporal.setTmrafn5(arqueo.getRqrafn5());
        temporal.setTmcocu5(arqueo.getRqcocu5());
        temporal.setTmrain6(arqueo.getRqrain6());
        temporal.setTmrafn6(arqueo.getRqrafn6());
        temporal.setTmcocu6(arqueo.getRqcocu6());
        temporal.setTmrain7(arqueo.getRqrain7());
        temporal.setTmrafn7(arqueo.getRqrafn7());
        temporal.setTmcocu7(arqueo.getRqcocu7());
        temporal.setTmrain8(arqueo.getRqrain8());
        temporal.setTmrafn8(arqueo.getRqrafn8());
        temporal.setTmcocu8(arqueo.getRqcocu8());
        temporal.setTmrain9(arqueo.getRqrain9());
        temporal.setTmrafn9(arqueo.getRqrafn9());
        temporal.setTmcocu9(arqueo.getRqcocu9());
        temporal.setTmrain10(arqueo.getRqrain10());
        temporal.setTmrafn10(arqueo.getRqrafn10());
        temporal.setTmcocu10(arqueo.getRqcocu10());
        temporal.setTmrain11(arqueo.getRqrain11());
        temporal.setTmrafn11(arqueo.getRqrafn11());
        temporal.setTmcocu11(arqueo.getRqcocu11());
        temporal.setTmrain12(arqueo.getRqrain12());
        temporal.setTmrafn12(arqueo.getRqrafn12());
        temporal.setTmcocu12(arqueo.getRqcocu12());
        temporal.setTmrain13(arqueo.getRqrain13());
        temporal.setTmrafn13(arqueo.getRqrafn13());
        temporal.setTmcocu13(arqueo.getRqcocu13());
        
        // Inicializar campos adicionales en 0 como en el programa RPG
        temporal.setTmsfeb(BigDecimal.ZERO);
        temporal.setTmdeb(BigDecimal.ZERO);
        temporal.setTmsfev(BigDecimal.ZERO);
        temporal.setTmdev(BigDecimal.ZERO);
        temporal.setTmsfet(BigDecimal.ZERO);
        temporal.setTmhora(0);
        temporal.setTmtrans("0144"); // Valor por defecto según el código RPG original
        
        return temporal;
    }
	
}