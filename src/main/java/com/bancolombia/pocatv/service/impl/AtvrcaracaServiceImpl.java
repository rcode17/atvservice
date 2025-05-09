package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Atvffcaarq;
import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.repository.AtvffcaarqRepository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.service.AtvrcaracaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AtvrcaracaServiceImpl implements AtvrcaracaService {
	private final AtvffcaarqRepository atvffcaarqRepository;
    private final AtvfftemRepository atvfftemRepository;

    @Autowired
    public AtvrcaracaServiceImpl(AtvffcaarqRepository atvffcaarqRepository, AtvfftemRepository atvfftemRepository) {
        this.atvffcaarqRepository = atvffcaarqRepository;
        this.atvfftemRepository = atvfftemRepository;
    }
    
    @Override
    @Transactional
    public int procesarArqueos() {
        // Obtener todos los registros de la tabla Atvffcaarq
        List<Atvffcaarq> arqueos = atvffcaarqRepository.findAll();
        int contador = 0;

        // Procesar cada registro
        for (Atvffcaarq arqueo : arqueos) {
            Atvfftem arqueoTemporal = new Atvfftem();

            // Mapeo de campos entre Atvffcaarq y Atvfftem
            // Formateador para convertir LocalDate a String
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //arqueoTemporal.setTmfear(arqueo.getRqfear().format(formatter));
            arqueoTemporal.setTmfear(arqueo.getRqfear());
            arqueoTemporal.setTmsuc(arqueo.getRqsuc());
            arqueoTemporal.setTmcdsu(arqueo.getRqcdsu());
            arqueoTemporal.setTmcdsuf(arqueo.getRqcdsuf());
            arqueoTemporal.setTmprcu(arqueo.getRqprcu());
            arqueoTemporal.setTmcedcn(arqueo.getRqcedcn());
            arqueoTemporal.setTmpear(arqueo.getRqpear());
            arqueoTemporal.setTmcedan(arqueo.getRqcedan());
            arqueoTemporal.setTmsubg(arqueo.getRqsubg());
            arqueoTemporal.setTmcesbn(arqueo.getRqcesbn());
            arqueoTemporal.setTmcopr(arqueo.getRqcopr());

            // Manejo especial para el código de documento
            String codo = arqueo.getRqcodo();
            if (codo != null && !codo.isEmpty()) {
                arqueoTemporal.setTmcodo(codo);
            }

            // Mapeo de saldos y diferencias
            arqueoTemporal.setTmsfar(arqueo.getRqsfar());
            if ("+".equals(arqueo.getRqsig())) {
                arqueoTemporal.setTmdif(arqueo.getRqdif());
            } else {
                if (arqueo.getRqdif() != null) {
                    arqueoTemporal.setTmdif(arqueo.getRqdif().negate());
                }
            }

            arqueoTemporal.setTmres(arqueo.getRqres());
            arqueoTemporal.setTmobs(arqueo.getRqobs());
            arqueoTemporal.setTmobso(arqueo.getRqobso());

            // Mapeo de rangos y códigos de cuenta
            arqueoTemporal.setTmrain1(arqueo.getRqrain1());
            arqueoTemporal.setTmrafn1(arqueo.getRqrafn1());
            arqueoTemporal.setTmcocu1(arqueo.getRqcocu1());

            arqueoTemporal.setTmrain2(arqueo.getRqrain2());
            arqueoTemporal.setTmrafn2(arqueo.getRqrafn2());
            arqueoTemporal.setTmcocu2(arqueo.getRqcocu2());

            arqueoTemporal.setTmrain3(arqueo.getRqrain3());
            arqueoTemporal.setTmrafn3(arqueo.getRqrafn3());
            arqueoTemporal.setTmcocu3(arqueo.getRqcocu3());

            arqueoTemporal.setTmrain4(arqueo.getRqrain4());
            arqueoTemporal.setTmrafn4(arqueo.getRqrafn4());
            arqueoTemporal.setTmcocu4(arqueo.getRqcocu4());

            arqueoTemporal.setTmrain5(arqueo.getRqrain5());
            arqueoTemporal.setTmrafn5(arqueo.getRqrafn5());
            arqueoTemporal.setTmcocu5(arqueo.getRqcocu5());

            arqueoTemporal.setTmrain6(arqueo.getRqrain6());
            arqueoTemporal.setTmrafn6(arqueo.getRqrafn6());
            arqueoTemporal.setTmcocu6(arqueo.getRqcocu6());

            arqueoTemporal.setTmrain7(arqueo.getRqrain7());
            arqueoTemporal.setTmrafn7(arqueo.getRqrafn7());
            arqueoTemporal.setTmcocu7(arqueo.getRqcocu7());

            arqueoTemporal.setTmrain8(arqueo.getRqrain8());
            arqueoTemporal.setTmrafn8(arqueo.getRqrafn8());
            arqueoTemporal.setTmcocu8(arqueo.getRqcocu8());

            arqueoTemporal.setTmrain9(arqueo.getRqrain9());
            arqueoTemporal.setTmrafn9(arqueo.getRqrafn9());
            arqueoTemporal.setTmcocu9(arqueo.getRqcocu9());

            arqueoTemporal.setTmrain10(arqueo.getRqrain10());
            arqueoTemporal.setTmrafn10(arqueo.getRqrafn10());
            arqueoTemporal.setTmcocu10(arqueo.getRqcocu10());

            arqueoTemporal.setTmrain11(arqueo.getRqrain11());
            arqueoTemporal.setTmrafn11(arqueo.getRqrafn11());
            arqueoTemporal.setTmcocu11(arqueo.getRqcocu11());

            arqueoTemporal.setTmrain12(arqueo.getRqrain12());
            arqueoTemporal.setTmrafn12(arqueo.getRqrafn12());
            arqueoTemporal.setTmcocu12(arqueo.getRqcocu12());

            arqueoTemporal.setTmrain13(arqueo.getRqrain13());
            arqueoTemporal.setTmrafn13(arqueo.getRqrafn13());
            arqueoTemporal.setTmcocu13(arqueo.getRqcocu13());

            // Inicializar campos con valores por defecto
            arqueoTemporal.setTmsfeb(BigDecimal.ZERO);
            arqueoTemporal.setTmdeb(BigDecimal.ZERO);
            arqueoTemporal.setTmsfev(BigDecimal.ZERO);
            arqueoTemporal.setTmdev(BigDecimal.ZERO);
            arqueoTemporal.setTmsfet(BigDecimal.ZERO);
            arqueoTemporal.setTmhora(0);
            arqueoTemporal.setTmtrans("");

            // Guardar el registro temporal en la base de datos
            atvfftemRepository.save(arqueoTemporal);
            contador++;
        }

        return contador;
    }
    
}
