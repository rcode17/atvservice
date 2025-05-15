package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffcrdRepository;
import com.bancolombia.pocatv.service.AtvrcrdService;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AtvrcrdServiceImpl implements AtvrcrdService {

    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffPdsRepository atvffPdsRepository;
    
    @Autowired
    private AtvffcrdRepository atvffcrdRepository;
    
    @Override
    @Transactional
    public int generarArqueosDescuadrados(int mes, int anno) {
        // Formatear mes y año para la consulta
        String mesStr = String.format("%02d", mes);
        String annoStr = String.valueOf(anno);
        
        // Obtener día actual (equivalente a CON en RPG)
        int diaActual = LocalDate.now().getDayOfMonth();
        
        // Buscar arqueos descuadrados para el mes y año especificados
        // Equivalente a la lógica de filtrado en el RPG
        List<Atvffarq> arqueos = atvffarqRepository.findArqueosDescuadradosByAnnoAndMes(annoStr, mesStr);
        
        if (arqueos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron arqueos para el mes " + mes + " y año " + anno);
        }
        
        int registrosProcesados = 0;
        
        for (Atvffarq arqueo : arqueos) {
            // Verificar si el resultado es 'D' (descuadrado)
            if (!"D".equals(arqueo.getAqres())) {
                continue;
            }
            
            // Verificar si el arqueo tiene datos válidos en los campos aqrain
            if (!arqueo.hasDataInAqrain()) {
                continue;
            }
            
            // Buscar información adicional en ATVFFPDS (equivalente a CHAIN ATVFFPDS en RPG)
            Optional<AtvffPds> pdsOptional = atvffPdsRepository.findByXscosuAndXscoprAndXscodo(
                arqueo.getAqcdsu(), 
                arqueo.getAqcopr(), 
                arqueo.getAqcodo()
            );
            
            if (pdsOptional.isEmpty()) {
                continue;
            }
            
            // Convertir fecha de arqueo a LocalDate
            LocalDate fechaArqueo = LocalDate.parse(arqueo.getAqfear(), DateTimeFormatter.ISO_DATE);
            
            // Crear ID para Atvffcrd
            AtvffcrdId crdId = new AtvffcrdId(
                anno,
                mes,
                arqueo.getAqcdsu(),
                arqueo.getAqcopr(),
                arqueo.getAqcodo(),
                fechaArqueo
            );
            
            // Verificar si ya existe un registro con este ID
            List<Atvffcrd> existingRecords = atvffcrdRepository.findByAnnoAndMes(anno, mes);
            boolean existeRegistro = existingRecords.stream()
                .anyMatch(record -> record.getId().equals(crdId));
            
            // Si no existe o si el día actual coincide con el día de control, crear nuevo registro
            if (!existeRegistro || diaActual == pdsOptional.get().getXscosu()) {
                Atvffcrd crd = new Atvffcrd();
                crd.setId(crdId);
                crd.setCdcon(diaActual);
                crd.setCdnomsuc(arqueo.getAqsuc());
                crd.setCddif(arqueo.getAqdif());
                crd.setCdcedan(arqueo.getAqcedan());
                crd.setCdsfar(arqueo.getAqsfar());
                crd.setCdres(arqueo.getAqres());
                
                atvffcrdRepository.save(crd);
                registrosProcesados++;
            }
        }
        
        if (registrosProcesados == 0) {
            throw new IllegalArgumentException("No hay registros con datos válidos en los campos aqrain para los parámetros proporcionados.");
        }
        
        return registrosProcesados;
    }
}