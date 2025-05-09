package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.ResultadoProcesamientoDTO;
import com.bancolombia.pocatv.model.Atvfftitva;
import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.repository.AtvfftitvaRepository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.service.AtvfftitvaService;
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

@Service
public class AtvfftitvaServiceImpl implements AtvfftitvaService {
	private static final Logger logger = LoggerFactory.getLogger(AtvfftitvaServiceImpl.class);
    private static final int BATCH_SIZE = 1000; // Tamaño del lote para procesamiento
    
    private final AtvfftitvaRepository atvfftitvaRepository;
    private final AtvfftemRepository atvfftemRepository;

    @Autowired
    public AtvfftitvaServiceImpl(AtvfftitvaRepository atvfftitvaRepository, AtvfftemRepository atvfftemRepository) {
        this.atvfftitvaRepository = atvfftitvaRepository;
        this.atvfftemRepository = atvfftemRepository;
    }

    @Override
    @Transactional
    public ResultadoProcesamientoDTO procesarInformacionArqueo() {
        logger.info("Iniciando procesamiento de información de arqueo");
        
        ResultadoProcesamientoDTO resultado = new ResultadoProcesamientoDTO();
        
        // Contar total de registros
        long totalRegistros = atvfftitvaRepository.count();
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
            Page<Atvfftitva> pageArqueos = atvfftitvaRepository.findAll(PageRequest.of(pageNumber, BATCH_SIZE));
            List<Atvfftitva> arqueos = pageArqueos.getContent();
            
            if (arqueos.isEmpty()) {
                hasMorePages = false;
                continue;
            }
            
            logger.info("Procesando lote {} con {} registros", pageNumber + 1, arqueos.size());
            
            // Lista para almacenar registros procesados antes de guardarlos en lote
            List<Atvfftem> registrosProcesados = new ArrayList<>();
            
            // Procesar cada arqueo en el lote actual
            for (Atvfftitva arqueo : arqueos) {
                try {
                    Atvfftem temporal = procesarRegistroArqueo(arqueo);
                    registrosProcesados.add(temporal);
                    resultado.incrementarExitosos();
                } catch (Exception e) {
                    resultado.incrementarFallidos();
                    String mensajeError = "Error al procesar arqueo con ID [" + 
                                         (arqueo.getRqcdsu() != null ? arqueo.getRqcdsu() : "null") + ", " + 
                                         (arqueo.getRqcopr() != null ? arqueo.getRqcopr() : "null") + ", " + 
                                         (arqueo.getRqcodo() != null ? arqueo.getRqcodo() : "null") + ", " + 
                                         (arqueo.getRqfear() != null ? arqueo.getRqfear() : "null") + "]: " + e.getMessage();
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
        
        logger.info("Procesamiento de información de arqueo completado. {}", resultado);
        return resultado;
    }
    
    /**
     * Procesa un único registro de arqueo según la lógica del programa RPG ATVRCARATS
     */
    private Atvfftem procesarRegistroArqueo(Atvfftitva arqueo) {
        Atvfftem temporal = new Atvfftem();
        
        // Convertir fecha
        try {
            if (arqueo.getRqfear() != null) {
                String fechaStr = arqueo.getRqfear().toString();
                if (fechaStr.length() == 8) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    LocalDate fecha = LocalDate.parse(fechaStr, formatter);
                    temporal.setTmfear(fecha);
                } else {
                    throw new IllegalArgumentException("Formato de fecha inválido: " + arqueo.getRqfear());
                }
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error al parsear la fecha: " + arqueo.getRqfear(), e);
        }
        
        // Mapear campos según la lógica del programa RPG ATVRCARATS
        temporal.setTmsuc(arqueo.getRqsuc());
        temporal.setTmcdsu(arqueo.getRqcdsu());
        temporal.setTmcdsuf(arqueo.getRqcdsu());
        temporal.setTmprcu(arqueo.getRqprcu());
        temporal.setTmcedcn(arqueo.getRqcedcn());
        temporal.setTmpear(arqueo.getRqpear());
        temporal.setTmcedan(arqueo.getRqcedan());
        temporal.setTmsubg(arqueo.getRqsubg());
        temporal.setTmcesbn(arqueo.getRqcesbn());
        
        // Formatear códigos con ceros a la izquierda como en el programa RPG
        if (arqueo.getRqcopr() != null) {
            String codigoProducto = "000" + arqueo.getRqcopr();
            temporal.setTmcopr(codigoProducto);
        }
        
        if (arqueo.getRqcodo() != null) {
            String codigoDocumento = "00" + arqueo.getRqcodo();
            temporal.setTmcodo(codigoDocumento);
        }
        
        // Mapear valores numéricos
        temporal.setTmsfar(arqueo.getRqsfar());
        temporal.setTmdif(arqueo.getRqdif());
        temporal.setTmres(arqueo.getRqres());
        temporal.setTmobs(arqueo.getRqobs());
        temporal.setTmobso(arqueo.getRqobso());
        temporal.setTmrain1(arqueo.getRqrain1());
        
        // Inicializar campos con valores vacíos
        temporal.setTmrafn1("");
        temporal.setTmcocu1("");
        temporal.setTmrain2("");
        temporal.setTmrafn2("");
        temporal.setTmcocu2("");
        temporal.setTmrain3("");
        temporal.setTmrafn3("");
        temporal.setTmcocu3("");
        temporal.setTmrain4("");
        temporal.setTmrafn4("");
        temporal.setTmcocu4("");
        temporal.setTmrain5("");
        temporal.setTmrafn5("");
        temporal.setTmcocu5("");
        temporal.setTmrain6("");
        temporal.setTmrafn6("");
        temporal.setTmcocu6("");
        temporal.setTmrain7("");
        temporal.setTmrafn7("");
        temporal.setTmcocu7("");
        temporal.setTmrain8("");
        temporal.setTmrafn8("");
        temporal.setTmcocu8("");
        temporal.setTmrain9("");
        temporal.setTmrafn9("");
        temporal.setTmcocu9("");
        temporal.setTmrain10("");
        temporal.setTmrafn10("");
        temporal.setTmcocu10("");
        temporal.setTmrain11("");
        temporal.setTmrafn11("");
        temporal.setTmcocu11("");
        temporal.setTmrain12("");
        temporal.setTmrafn12("");
        temporal.setTmcocu12("");
        temporal.setTmrain13("");
        temporal.setTmrafn13("");
        temporal.setTmcocu13("");
        
        // Inicializar saldos con cero
        temporal.setTmsfeb(BigDecimal.ZERO);
        temporal.setTmdeb(BigDecimal.ZERO);
        temporal.setTmsfev(BigDecimal.ZERO);
        temporal.setTmdev(BigDecimal.ZERO);
        
        // Mapear saldo total
        temporal.setTmsfet(arqueo.getRqsfet());
        
        // Inicializar hora con cero
        temporal.setTmhora(0);
        
        // Establecer transacción por defecto
        temporal.setTmtrans("0144");
        
        return temporal;
    }
}
