package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.model.Atvlfarq;
import com.bancolombia.pocatv.model.Atvffrec;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.Atvffmesal;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.repository.AtvlfarqRepository;
import com.bancolombia.pocatv.repository.AtvffrecRepository;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffmesalRepository;
import com.bancolombia.pocatv.service.AtvrvalService;

import jakarta.transaction.Transactional;


@Service
public class AtvrvalServiceImpl implements AtvrvalService {

    @Autowired
    private AtvfftemRepository atvfftemRepository;

    @Autowired
    private AtvffrecRepository atvffrecRepository;

    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmesalRepository atvffmesalRepository;
    
    @Autowired
    private AtvlfarqRepository atvlfarqRepository;

    //@Autowired
    //private Bknaml0Repository bknaml0Repository;

    public void processRecords() {
        List<Atvfftem> records = atvfftemRepository.findAll();

        for (Atvfftem record : records) {
            String tmcopr = record.getTmcopr();
            Integer tmcdsu = record.getTmcdsu();
            String tmcodo = record.getTmcodo();
            String tmres = record.getTmres();
            //String xpstd0 = record.getXpstd0();

            // Lógica de procesamiento
            if ("CDSU".equals(tmcdsu)) {
                // Procesar caso CDSU
                Atvffrec rejectRecord = new Atvffrec();
                rejectRecord.setRccopr(tmcopr);
                rejectRecord.setRccodo(tmcodo);
                rejectRecord.setRcrech("CDSU");
                atvffrecRepository.save(rejectRecord);
            } else if ("PDS".equals(tmcdsu)) {
                // Procesar caso PDS
                Atvffrec rejectRecord = new Atvffrec();
                rejectRecord.setRccopr(tmcopr);
                rejectRecord.setRccodo(tmcodo);
                rejectRecord.setRcrech("PDS");
                atvffrecRepository.save(rejectRecord);
            } else if ("PRDO".equals(tmcdsu)) {
                // Procesar caso PRDO
                Atvffrec rejectRecord = new Atvffrec();
                rejectRecord.setRccopr(tmcopr);
                rejectRecord.setRccodo(tmcodo);
                rejectRecord.setRcrech("PRDO");
                atvffrecRepository.save(rejectRecord);
            }else {
                // Manejar otros casos según sea necesario
                Atvffarq arqueoRecord = new Atvffarq();
                arqueoRecord.setAqcopr(tmcopr);
                arqueoRecord.setAqcodo(tmcodo);
                atvffarqRepository.save(arqueoRecord);
            }
        }
    }
    
    

    @Override
    @Transactional
    public void procesarDatosAtvffmesal() {
        List<Atvffmesal> registros = atvffmesalRepository.findAll();
        
        for (Atvffmesal regsal : registros) {
            String saofco = regsal.getSdeofco();
            String satpro = regsal.getSdtpro();
            String satdoc = regsal.getSdtdoc();
            String safech = regsal.getSdfech();
            
            Atvlfarq regsal1 = null;
            try {
                regsal1 = atvlfarqRepository.findBySaofcoAndSatproAndSatdocAndSafech(saofco, satpro, satdoc, safech);
            } catch (Exception e) {
                // No se encontró el registro
            }
            
            if (regsal1 != null) {
                // Convertir Double a BigDecimal
                BigDecimal sadisp = (regsal.getSddisp() != null) ? 
                                    new BigDecimal(regsal.getSddisp().toString()) : 
                                    null;
                regsal1.setSadisp(sadisp);
                
                // Convertir String a Integer si es necesario
                // Si saofic debe ser Integer, podemos convertir el String a Integer
                try {
                    Integer saofic = (regsal.getSdeofic() != null && !regsal.getSdeofic().isEmpty()) ? 
                                     Integer.parseInt(regsal.getSdeofic()) : 
                                     null;
                    regsal1.setSaofic(saofic);
                } catch (NumberFormatException e) {
                    // Manejar el caso donde el String no puede convertirse a Integer
                    throw new IllegalArgumentException("El valor de sdeofic no puede convertirse a Integer: " + regsal.getSdeofic());
                }
                
                atvlfarqRepository.save(regsal1);
            } else {
                // Crear nuevo registro con las conversiones adecuadas
                Atvlfarq nuevoRegsal1 = new Atvlfarq();
                nuevoRegsal1.setSaofco(saofco);
                nuevoRegsal1.setSatpro(satpro);
                nuevoRegsal1.setSatdoc(satdoc);
                nuevoRegsal1.setSafech(safech);
                
                // Convertir Double a BigDecimal
                BigDecimal sadisp = (regsal.getSddisp() != null) ? 
                                    new BigDecimal(regsal.getSddisp().toString()) : 
                                    null;
                nuevoRegsal1.setSadisp(sadisp);
                
                // Convertir String a Integer si es necesario
                try {
                    Integer saofic = (regsal.getSdeofic() != null && !regsal.getSdeofic().isEmpty()) ? 
                                     Integer.parseInt(regsal.getSdeofic()) : 
                                     null;
                    nuevoRegsal1.setSaofic(saofic);
                } catch (NumberFormatException e) {
                    // Manejar el caso donde el String no puede convertirse a Integer
                    throw new IllegalArgumentException("El valor de sdeofic no puede convertirse a Integer: " + regsal.getSdeofic());
                }
                
                atvlfarqRepository.save(nuevoRegsal1);
            }
        }
    }
    
}