package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bancolombia.pocatv.model.Atvlfarq;
import com.bancolombia.pocatv.model.Atvffmesal;
import com.bancolombia.pocatv.repository.AtvlfarqRepository;
import com.bancolombia.pocatv.repository.AtvffmesalRepository;

import com.bancolombia.pocatv.service.AtvrvalService;

import jakarta.transaction.Transactional;


@Service
public class AtvrvalServiceImpl implements AtvrvalService {

    @Autowired
    private AtvfftemRepository atvfftemRepository;

    @Autowired
    private AtvffPdoRepository atvffpdoRepository;

    @Autowired
    private AtvffPdsRepository atvffpdsRepository;

    @Autowired
    private XbknamRepository xbknamRepository;

    @Autowired
    private AtvffarqRepository atvffarqRepository;

    @Autowired
    private AtvffrecRepository atvffrecRepository;

    @Autowired
    private AtvffmesalRepository atvffmesalRepository;

    @Autowired
    private AtvlfarqRepository atvlfarqRepository;

    @Override
    public List<Atvfftem> findAll() {
        return atvfftemRepository.findAll();

    }

    @Override
    @Transactional
    public void procesarArqueos() {
        List<Atvfftem> registros = atvfftemRepository.findAll();

        for (Atvfftem registro : registros) {
            // Verificar si existe en XBKNAML0
            boolean existeEnXbknaml0 = xbknamRepository.existsById(convertStringoToDecimal(registro.getTmcdsu().toString()));

            if (existeEnXbknaml0) {
                // Escribir en archivo de rechazos con motivo "CDSU"
                escribirRechazo(registro, "CDSU");
                continue;
            }

            // Verificar si existe en ATVFFPDS
            AtvffPdsId pdsId = new AtvffPdsId(registro.getTmcdsu(), registro.getTmcopr(), registro.getTmcodo());
            boolean existeEnAtvffpds = atvffpdsRepository.existsById(pdsId);

            if (existeEnAtvffpds) {
                // Escribir en archivo de rechazos con motivo "PDS"
                escribirRechazo(registro, "PDS");
                continue;
            }

            // Verificar si existe en ATVFFPDO
            AtvffPdoId pdoId = new AtvffPdoId(registro.getTmcopr(), registro.getTmcodo());
            AtvffPdo productoDocs = atvffpdoRepository.findById(pdoId).orElse(null);

            if (productoDocs != null) {
                // Escribir en archivo de rechazos con motivo "PRDO"
                escribirRechazo(registro, "PRDO");
                continue;
            }

            // Verificar condiciones para escribir en archivo de arqueos
            if ((productoDocs != null && "1".equals(productoDocs.getXpstdo()) &&
                    (registro.getTmres() == null || registro.getTmres().isEmpty())) ||
                    (productoDocs == null && (registro.getTmres() != null && !registro.getTmres().isEmpty()) &&
                            (registro.getTmres().equals("C") || registro.getTmres().equals("D") ||
                                    registro.getTmres().equals("RC") || registro.getTmres().equals("RD")))) {

                escribirArqueo(registro);
            } else {
                // Escribir en archivo de rechazos con motivo "RES"
                escribirRechazo(registro, "RES");
            }
        }
    }
    private BigDecimal convertStringoToDecimal(String valorString ) {

        // Conversión segura a BigDecimal
        BigDecimal valorDecimal;
        try {
            valorDecimal = (valorString != null && !valorString.isEmpty())
                    ? new BigDecimal(valorString)
                    : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            // Manejo de error si el String no puede convertirse a BigDecimal
            valorDecimal = BigDecimal.ZERO;
            // Opcionalmente, registrar el error
            System.err.println("Error al convertir a BigDecimal: " + e.getMessage());
        }
        return valorDecimal;
    }

    private void escribirRechazo(Atvfftem registro, String motivoRechazo) {
        Atvffrec rechazo = new Atvffrec();

        // Generar fecha de rechazo (formato AAAAMMDD)
        String fechaRechazo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        rechazo.setRcfere(fechaRechazo);

        // Copiar datos del registro temporal al rechazo
        String fechaString = registro.getTmfear().toString();
        Date fechaDate = null;

        if (fechaString != null && !fechaString.isEmpty()) {
            try {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); // Ajusta el formato según tu String
                fechaDate = formato.parse(fechaString);
            } catch (ParseException e) {
                // Manejo del error de parseo
                System.err.println("Error al convertir la fecha: " + e.getMessage());
            }
        }
        rechazo.setRcfear(fechaDate);
        rechazo.setRcsuc(registro.getTmsuc());
        rechazo.setRccdsu(registro.getTmcdsu());
        rechazo.setRccdsuf(registro.getTmcdsuf());
        rechazo.setRcprcu(registro.getTmprcu());
        rechazo.setRccedcn(registro.getTmcedcn());
        rechazo.setRcpear(registro.getTmpear());
        rechazo.setRccedan(registro.getTmcedan());
        rechazo.setRcsubg(registro.getTmsubg());
        rechazo.setRccesbn(registro.getTmcesbn());
        rechazo.setRccopr(registro.getTmcopr());
        rechazo.setRccodo(registro.getTmcodo());
        rechazo.setRcsfar(registro.getTmsfar());
        rechazo.setRcdif(registro.getTmdif());
        rechazo.setRcres(registro.getTmres());
        rechazo.setRcobsn(registro.getTmobs());
        rechazo.setRcobsno(registro.getTmobso());
        rechazo.setRcsfeb(registro.getTmsfeb());
        rechazo.setRcdeb(registro.getTmdeb());
        rechazo.setRcsfev(registro.getTmsfev());
        rechazo.setRcdev(registro.getTmdev());
        rechazo.setRcsfet(registro.getTmsfet());
        rechazo.setRchora(registro.getTmhora());

        // Copiar rangos y códigos de cuenta
        rechazo.setRcrain1(registro.getTmrain1());
        rechazo.setRcrafn1(registro.getTmrafn1());
        rechazo.setRccocu1(registro.getTmcocu1());
        // ... (continuar con los demás rangos)

        // Establecer motivo de rechazo
        rechazo.setRcrech(motivoRechazo);

        // Guardar el rechazo
        atvffrecRepository.save(rechazo);
    }

    private void escribirArqueo(Atvfftem registro) {
        Atvffarq arqueo = new Atvffarq();

        // Copiar datos del registro temporal al arqueo
        arqueo.setAqfear(registro.getTmfear().toString());
        arqueo.setAqsuc(registro.getTmsuc());
        arqueo.setAqcdsu(registro.getTmcdsu());
        arqueo.setAqcdsuf(registro.getTmcdsuf());
        arqueo.setAqprcu(registro.getTmprcu());
        arqueo.setAqcedcn(registro.getTmcedcn());
        arqueo.setAqpear(registro.getTmpear());
        arqueo.setAqcedan(registro.getTmcedan());
        arqueo.setAqsubg(registro.getTmsubg());
        arqueo.setAqcesbn(registro.getTmcesbn());
        arqueo.setAqcopr(registro.getTmcopr());
        arqueo.setAqcodo(registro.getTmcodo());
        arqueo.setAqsfar(registro.getTmsfar());
        arqueo.setAqdif(registro.getTmdif());
        arqueo.setAqres(registro.getTmres());
        arqueo.setAqobsn(registro.getTmobs());
        arqueo.setAqobsno(registro.getTmobso());
        arqueo.setAqsfeb(registro.getTmsfeb());
        arqueo.setAqdeb(registro.getTmdeb());
        arqueo.setAqsfev(registro.getTmsfev());
        arqueo.setAqdev(registro.getTmdev());
        arqueo.setAqsfet(registro.getTmsfet());
        arqueo.setAqhora(registro.getTmhora());

        // Copiar rangos y códigos de cuenta
        arqueo.setAqrain1(registro.getTmrain1());
        arqueo.setAqrafn1(registro.getTmrafn1());
        arqueo.setAqcocu1(registro.getTmcocu1());
        // ... (continuar con los demás rangos)

        // Guardar el arqueo
        atvffarqRepository.save(arqueo);
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