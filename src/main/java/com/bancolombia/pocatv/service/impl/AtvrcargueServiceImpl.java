package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Atvffesafi;
import com.bancolombia.pocatv.model.Atvffesal;
import com.bancolombia.pocatv.model.Atvffesal1;
import com.bancolombia.pocatv.model.Atvfffil;
import com.bancolombia.pocatv.model.Atvffsai1;
import com.bancolombia.pocatv.model.Atvffsai2;
import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.repository.AtvffesafiRepository;
import com.bancolombia.pocatv.repository.Atvffesal1Repository;
import com.bancolombia.pocatv.repository.AtvffesalRepository;
import com.bancolombia.pocatv.repository.AtvffilRepository;
import com.bancolombia.pocatv.repository.Atvffsai1Repository;
import com.bancolombia.pocatv.repository.Atvffsai2Repository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.service.AtvrcargueService;
import com.bancolombia.pocatv.utils.DataTransformationUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AtvrcargueServiceImpl implements AtvrcargueService{

    @Autowired
    private AtvffesalRepository atvffesalRepository;

    @Autowired
    private Atvffesal1Repository atvffesal1Repository;

    @Autowired
    private AtvffesafiRepository atvffesafiRepository;
    
    @Autowired
    private Atvffsai1Repository atvffsai1Repository;
    
    @Autowired
    private Atvffsai2Repository atvffsai2Repository;
    
    @Autowired
    private AtvfftemRepository atvfftemRepository;
    
    @Autowired
    private AtvffilRepository atvffilRepository;
    
    @Autowired
    private DataTransformationUtil dataTransformationUtil;

    /**
     * Implementación del método principal para procesar datos.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Override
    @Transactional
    public void procesarDatos() {
        // Obtiene todos los registros a procesar
        List<Atvffesal> registros = atvffesalRepository.findAll();

        // Procesa cada registro
        for (Atvffesal registro : registros) {
            // Determina si es un registro normal o de filial
            if (registro.getSaoftx() == null || registro.getSaoftx().trim().isEmpty()) {
                // Procesa como registro normal
                procesarRegistroNormal(registro);
            } else {
                // Procesa como registro de filial
                procesarRegistroFilial(registro);
            }
        }
    }

    /**
     * Procesa un registro normal (no filial).
     *
     * @param registro El registro a procesar
     */
    private void procesarRegistroNormal(Atvffesal registro) {
        // Busca si ya existe un registro con la misma clave
        Optional<Atvffesal1> registroExistente = atvffesal1Repository.findById(registro.getEsafec());

        if (registroExistente.isEmpty()) {
            // Si no existe, crea un nuevo registro
            Atvffesal1 nuevoRegistro = new Atvffesal1();
            copiarDatosNormales(nuevoRegistro, registro);
            atvffesal1Repository.save(nuevoRegistro);
        } else {
            // Si existe, actualiza el registro
            Atvffesal1 regExistente = registroExistente.get();
            copiarDatosNormales(regExistente, registro);
            atvffesal1Repository.save(regExistente);
        }
    }

    /**
     * Procesa un registro de filial.
     *
     * @param registro El registro a procesar
     */
    private void procesarRegistroFilial(Atvffesal registro) {
        // Busca si ya existe un registro con la misma clave
        Optional<Atvffesafi> registroExistente = atvffesafiRepository.findById(registro.getEsafec());

        if (registroExistente.isEmpty()) {
            // Si no existe, crea un nuevo registro
            Atvffesafi nuevoRegistro = new Atvffesafi();
            copiarDatosFiliales(nuevoRegistro, registro);
            atvffesafiRepository.save(nuevoRegistro);
        } else {
            // Si existe, actualiza el registro
            Atvffesafi regExistente = registroExistente.get();
            copiarDatosFiliales(regExistente, registro);
            atvffesafiRepository.save(regExistente);
        }
    }

    /**
     * Copia los datos del registro de origen al registro normal de destino.
     *
     * @param destino El registro de destino
     * @param origen El registro de origen
     */
    private void copiarDatosNormales(Atvffesal1 destino, Atvffesal origen) {
        destino.setEsafec(origen.getEsafec());
        destino.setEsager(origen.getEsager());
        destino.setEsaced(origen.getEsaced());
        destino.setSaofic(origen.getSaofic());
        destino.setSaofco(origen.getSaofco());
    }

    /**
     * Copia los datos del registro de origen al registro de filial de destino.
     *
     * @param destino El registro de filial de destino
     * @param origen El registro de origen
     */
    private void copiarDatosFiliales(Atvffesafi destino, Atvffesal origen) {
        destino.setEsafec(origen.getEsafec());
        destino.setEsager(origen.getEsager());
        destino.setEsaced(origen.getEsaced());
        destino.setSaofic(origen.getSaofic());
        destino.setSaofco(origen.getSaofco());
    }
    
    
    
    @Override
    @Transactional
    public void procesarCargue() {
        procesarAtvffsai2();
        procesarAtvffsai1();
    }

    @Override
    @Transactional
    public void procesarAtvffsai2() {
        List<Atvffsai2> registros = atvffsai2Repository.findAll();
        
        for (Atvffsai2 registro : registros) {
            // Verificar condiciones como en el código RPG original
            if (registro.getTmsfar().compareTo(new BigDecimal("99999999999")) <= 0 &&
                registro.getTmdif().compareTo(new BigDecimal("99999999999")) <= 0 &&
                registro.getTmsfeb().compareTo(new BigDecimal("99999999999")) <= 0 &&
                registro.getTmdeb().compareTo(new BigDecimal("99999999999")) <= 0 &&
                registro.getTmsfev().compareTo(new BigDecimal("99999999999")) <= 0 &&
                registro.getTmdev().compareTo(new BigDecimal("99999999999")) <= 0 &&
                registro.getTmsfet().compareTo(new BigDecimal("99999999999")) <= 0) {
                
                if (registro.getTmsuctx() != null && !registro.getTmsuctx().trim().isEmpty()) {
                    // Procesar para ATVFFFIL
                    Atvfffil filial = mapearAtvffsai2AAtvfffil(registro);
                    atvffilRepository.save(filial);
                } else {
                    // Procesar para ATVFFTEM
                    Atvfftem temporal = mapearAtvffsai2AAtvfftem(registro);
                    atvfftemRepository.save(temporal);
                }
            }
        }
    }

    @Override
    @Transactional
    public void procesarAtvffsai1() {
        List<Atvffsai1> registros = atvffsai1Repository.findAll();
        
        for (Atvffsai1 registro : registros) {
            if (registro.getTmsuctx() != null && !registro.getTmsuctx().trim().isEmpty()) {
                // Procesar para ATVFFFIL
                Atvfffil filial = mapearAtvffsai1AAtvfffil(registro);
                atvffilRepository.save(filial);
            } else {
                // Procesar para ATVFFTEM
                Atvfftem temporal = mapearAtvffsai1AAtvfftem(registro);
                atvfftemRepository.save(temporal);
            }
        }
    }

    private Atvfftem mapearAtvffsai2AAtvfftem(Atvffsai2 origen) {
        Atvfftem destino = new Atvfftem();
        
        // Mapear campos comunes
        destino.setTmfear(dataTransformationUtil.convertirBigDecimalAString(origen.getTmfear()));
        destino.setTmsuc(origen.getTmsuc());
        destino.setTmcdsu(dataTransformationUtil.convertirBigDecimalAInteger(origen.getTmcdsu()));
        destino.setTmcdsuf(dataTransformationUtil.convertirBigDecimalAInteger(origen.getTmcdsuf()));
        destino.setTmprcu(origen.getTmprcu());
        destino.setTmcedcn(origen.getTmcedcn());
        destino.setTmpear(origen.getTmpear());
        destino.setTmcedan(origen.getTmcedan());
        destino.setTmsubg(origen.getTmsubg());
        destino.setTmcesbn(origen.getTmcesbn());
        destino.setTmcopr(origen.getTmcopr());
        destino.setTmcodo(origen.getTmcodo());
        destino.setTmsfar(origen.getTmsfar());
        
        // Procesar diferencia según signo
        if ("+".equals(origen.getTmsig())) {
            destino.setTmdif(origen.getTmdif());
        } else {
            destino.setTmdif(origen.getTmdif().negate());
        }
        
        destino.setTmres(origen.getTmres());
        destino.setTmobs(origen.getTmobs());
        destino.setTmobso(origen.getTmobso());
        
        // Procesar saldo boveda según signo
        destino.setTmsfeb(origen.getTmsfeb());
        if ("+".equals(origen.getTmseb())) {
            destino.setTmdeb(origen.getTmdeb());
        } else {
            destino.setTmdeb(origen.getTmdeb().negate());
        }
        
        // Procesar saldo ventanilla según signo
        destino.setTmsfev(origen.getTmsfev());
        if ("+".equals(origen.getTmsev())) {
            destino.setTmdev(origen.getTmdev());
        } else {
            destino.setTmdev(origen.getTmdev().negate());
        }
        
        destino.setTmsfet(origen.getTmsfet());
        destino.setTmhora(dataTransformationUtil.convertirBigDecimalAInteger(origen.getTmhora()));
        destino.setTmtrans("0144");
        
        // Inicializar campos de rangos con valores vacíos
        destino.setTmrain1("");
        destino.setTmrafn1("");
        destino.setTmcocu1("");
        destino.setTmrain2("");
        destino.setTmrafn2("");
        destino.setTmcocu2("");
        destino.setTmrain3("");
        destino.setTmrafn3("");
        destino.setTmcocu3("");
        destino.setTmrain4("");
        destino.setTmrafn4("");
        destino.setTmcocu4("");
        destino.setTmrain5("");
        destino.setTmrafn5("");
        destino.setTmcocu5("");
        destino.setTmrain6("");
        destino.setTmrafn6("");
        destino.setTmcocu6("");
        destino.setTmrain7("");
        destino.setTmrafn7("");
        destino.setTmcocu7("");
        destino.setTmrain8("");
        destino.setTmrafn8("");
        destino.setTmcocu8("");
        destino.setTmrain9("");
        destino.setTmrafn9("");
        destino.setTmcocu9("");
        destino.setTmrain10("");
        destino.setTmrafn10("");
        destino.setTmcocu10("");
        destino.setTmrain11("");
        destino.setTmrafn11("");
        destino.setTmcocu11("");
        destino.setTmrain12("");
        destino.setTmrafn12("");
        destino.setTmcocu12("");
        destino.setTmrain13("");
        destino.setTmrafn13("");
        destino.setTmcocu13("");
        
        return destino;
    }

    private Atvfffil mapearAtvffsai2AAtvfffil(Atvffsai2 origen) {
        Atvfffil destino = new Atvfffil();
        
        // Mapear campos comunes
        destino.setTmfear(dataTransformationUtil.convertirFechaNumericaADate(origen.getTmfear()));
        destino.setTmsuc(origen.getTmsuc());
        destino.setTmcdsu(origen.getTmcdsu());
        destino.setTmcdsuf(origen.getTmcdsuf());
        destino.setTmprcu(origen.getTmprcu());
        destino.setTmcedcn(origen.getTmcedcn());
        destino.setTmpear(origen.getTmpear());
        destino.setTmcedan(origen.getTmcedan());
        destino.setTmsubg(origen.getTmsubg());
        destino.setTmcesbn(origen.getTmcesbn());
        destino.setTmcopr(origen.getTmcopr());
        destino.setTmcodo(origen.getTmcodo());
        destino.setTmsfar(origen.getTmsfar());
        
        // Procesar diferencia según signo
        if ("+".equals(origen.getTmsig())) {
            destino.setTmdif(origen.getTmdif());
        } else {
            destino.setTmdif(origen.getTmdif().negate());
        }
        
        destino.setTmres(origen.getTmres());
        destino.setTmobs(origen.getTmobs());
        destino.setTmobso(origen.getTmobso());
        
        // Procesar saldo boveda según signo
        destino.setTmsfeb(origen.getTmsfeb());
        if ("+".equals(origen.getTmseb())) {
            destino.setTmdeb(origen.getTmdeb());
        } else {
            destino.setTmdeb(origen.getTmdeb().negate());
        }
        
        // Procesar saldo ventanilla según signo
        destino.setTmsfev(origen.getTmsfev());
        if ("+".equals(origen.getTmsev())) {
            destino.setTmdev(origen.getTmdev());
        } else {
            destino.setTmdev(origen.getTmdev().negate());
        }
        
        destino.setTmsfet(origen.getTmsfet());
        destino.setTmhora(origen.getTmhora());
        destino.setTmtrans("0144");
        destino.setTmsuctx(origen.getTmsuctx());
        
        // Inicializar campos de rangos con valores vacíos
        destino.setTmrain1("");
        destino.setTmrafn1("");
        destino.setTmcocu1("");
        destino.setTmrain2("");
        destino.setTmrafn2("");
        destino.setTmcocu2("");
        destino.setTmrain3("");
        destino.setTmrafn3("");
        destino.setTmcocu3("");
        destino.setTmrain4("");
        destino.setTmrafn4("");
        destino.setTmcocu4("");
        destino.setTmrain5("");
        destino.setTmrafn5("");
        destino.setTmcocu5("");
        destino.setTmrain6("");
        destino.setTmrafn6("");
        destino.setTmcocu6("");
        destino.setTmrain7("");
        destino.setTmrafn7("");
        destino.setTmcocu7("");
        destino.setTmrain8("");
        destino.setTmrafn8("");
        destino.setTmcocu8("");
        destino.setTmrain9("");
        destino.setTmrafn9("");
        destino.setTmcocu9("");
        destino.setTmrain10("");
        destino.setTmrafn10("");
        destino.setTmcocu10("");
        destino.setTmrain11("");
        destino.setTmrafn11("");
        destino.setTmcocu11("");
        destino.setTmrain12("");
        destino.setTmrafn12("");
        destino.setTmcocu12("");
        destino.setTmrain13("");
        destino.setTmrafn13("");
        destino.setTmcocu13("");
        
        return destino;
    }

    private Atvfftem mapearAtvffsai1AAtvfftem(Atvffsai1 origen) {
        Atvfftem destino = new Atvfftem();
        
        // Mapear campos comunes
        destino.setTmfear(dataTransformationUtil.convertirBigDecimalAString(origen.getTmfear()));
        destino.setTmsuc(origen.getTmsuc());
        destino.setTmcdsu(dataTransformationUtil.convertirBigDecimalAInteger(origen.getTmcdsu()));
        destino.setTmcdsuf(dataTransformationUtil.convertirBigDecimalAInteger(origen.getTmcdsuf()));
        destino.setTmprcu(origen.getTmprcu());
        destino.setTmcedcn(origen.getTmcedcn());
        destino.setTmpear(origen.getTmpear());
        destino.setTmcedan(origen.getTmcedan());
        destino.setTmsubg(origen.getTmsubg());
        destino.setTmcesbn(origen.getTmcesbn());
        destino.setTmcopr(origen.getTmcopr());
        destino.setTmcodo(origen.getTmcodo());
        destino.setTmsfar(origen.getTmsfar());
        
        // Procesar diferencia según signo
        if ("+".equals(origen.getTmsig())) {
            destino.setTmdif(origen.getTmdif());
        } else {
            destino.setTmdif(origen.getTmdif().negate());
        }
        
        destino.setTmres(origen.getTmres());
        destino.setTmobs(origen.getTmobs());
        destino.setTmobso(origen.getTmobso());
        
        // Copiar campos de rangos
        destino.setTmrain1(origen.getTmrain1());
        destino.setTmrafn1(origen.getTmrafn1());
        destino.setTmcocu1(origen.getTmcocu1());
        destino.setTmrain2(origen.getTmrain2());
        destino.setTmrafn2(origen.getTmrafn2());
        destino.setTmcocu2(origen.getTmcocu2());
        destino.setTmrain3(origen.getTmrain3());
        destino.setTmrafn3(origen.getTmrafn3());
        destino.setTmcocu3(origen.getTmcocu3());
        destino.setTmrain4(origen.getTmrain4());
        destino.setTmrafn4(origen.getTmrafn4());
        destino.setTmcocu4(origen.getTmcocu4());
        destino.setTmrain5(origen.getTmrain5());
        destino.setTmrafn5(origen.getTmrafn5());
        destino.setTmcocu5(origen.getTmcocu5());
        destino.setTmrain6(origen.getTmrain6());
        destino.setTmrafn6(origen.getTmrafn6());
        destino.setTmcocu6(origen.getTmcocu6());
        destino.setTmrain7(origen.getTmrain7());
        destino.setTmrafn7(origen.getTmrafn7());
        destino.setTmcocu7(origen.getTmcocu7());
        destino.setTmrain8(origen.getTmrain8());
        destino.setTmrafn8(origen.getTmrafn8());
        destino.setTmcocu8(origen.getTmcocu8());
        destino.setTmrain9(origen.getTmrain9());
        destino.setTmrafn9(origen.getTmrafn9());
        destino.setTmcocu9(origen.getTmcocu9());
        destino.setTmrain10(origen.getTmrain10());
        destino.setTmrafn10(origen.getTmrafn10());
        destino.setTmcocu10(origen.getTmcocu10());
        destino.setTmrain11(origen.getTmrain11());
        destino.setTmrafn11(origen.getTmrafn11());
        destino.setTmcocu11(origen.getTmcocu11());
        destino.setTmrain12(origen.getTmrain12());
        destino.setTmrafn12(origen.getTmrafn12());
        destino.setTmcocu12(origen.getTmcocu12());
        destino.setTmrain13(origen.getTmrain13());
        destino.setTmrafn13(origen.getTmrafn13());
        destino.setTmcocu13(origen.getTmcocu13());
        
        // Inicializar campos adicionales
        destino.setTmsfeb(BigDecimal.ZERO);
        destino.setTmdeb(BigDecimal.ZERO);
        destino.setTmsfev(BigDecimal.ZERO);
        destino.setTmdev(BigDecimal.ZERO);
        destino.setTmsfet(BigDecimal.ZERO);
        destino.setTmhora(origen.getTmhora().intValue());
        destino.setTmtrans("0124");
        
        return destino;
    }

    private Atvfffil mapearAtvffsai1AAtvfffil(Atvffsai1 origen) {
        Atvfffil destino = new Atvfffil();
        
        // Mapear campos comunes
        destino.setTmfear(dataTransformationUtil.convertirFechaNumericaADate(origen.getTmfear()));
        destino.setTmsuc(origen.getTmsuc());
        destino.setTmcdsu(origen.getTmcdsu());
        destino.setTmcdsuf(origen.getTmcdsuf());
        destino.setTmprcu(origen.getTmprcu());
        destino.setTmcedcn(origen.getTmcedcn());
        destino.setTmpear(origen.getTmpear());
        destino.setTmcedan(origen.getTmcedan());
        destino.setTmsubg(origen.getTmsubg());
        destino.setTmcesbn(origen.getTmcesbn());
        destino.setTmcopr(origen.getTmcopr());
        destino.setTmcodo(origen.getTmcodo());
        destino.setTmsfar(origen.getTmsfar());
        
        // Procesar diferencia según signo
        if ("+".equals(origen.getTmsig())) {
            destino.setTmdif(origen.getTmdif());
        } else {
            destino.setTmdif(origen.getTmdif().negate());
        }
        
        destino.setTmres(origen.getTmres());
        destino.setTmobs(origen.getTmobs());
        destino.setTmobso(origen.getTmobso());
        
        // Copiar campos de rangos
        destino.setTmrain1(origen.getTmrain1());
        destino.setTmrafn1(origen.getTmrafn1());
        destino.setTmcocu1(origen.getTmcocu1());
        destino.setTmrain2(origen.getTmrain2());
        destino.setTmrafn2(origen.getTmrafn2());
        destino.setTmcocu2(origen.getTmcocu2());
        destino.setTmrain3(origen.getTmrain3());
        destino.setTmrafn3(origen.getTmrafn3());
        destino.setTmcocu3(origen.getTmcocu3());
        destino.setTmrain4(origen.getTmrain4());
        destino.setTmrafn4(origen.getTmrafn4());
        destino.setTmcocu4(origen.getTmcocu4());
        destino.setTmrain5(origen.getTmrain5());
        destino.setTmrafn5(origen.getTmrafn5());
        destino.setTmcocu5(origen.getTmcocu5());
        destino.setTmrain6(origen.getTmrain6());
        destino.setTmrafn6(origen.getTmrafn6());
        destino.setTmcocu6(origen.getTmcocu6());
        destino.setTmrain7(origen.getTmrain7());
        destino.setTmrafn7(origen.getTmrafn7());
        destino.setTmcocu7(origen.getTmcocu7());
        destino.setTmrain8(origen.getTmrain8());
        destino.setTmrafn8(origen.getTmrafn8());
        destino.setTmcocu8(origen.getTmcocu8());
        destino.setTmrain9(origen.getTmrain9());
        destino.setTmrafn9(origen.getTmrafn9());
        destino.setTmcocu9(origen.getTmcocu9());
        destino.setTmrain10(origen.getTmrain10());
        destino.setTmrafn10(origen.getTmrafn10());
        destino.setTmcocu10(origen.getTmcocu10());
        destino.setTmrain11(origen.getTmrain11());
        destino.setTmrafn11(origen.getTmrafn11());
        destino.setTmcocu11(origen.getTmcocu11());
        destino.setTmrain12(origen.getTmrain12());
        destino.setTmrafn12(origen.getTmrafn12());
        destino.setTmcocu12(origen.getTmcocu12());
        destino.setTmrain13(origen.getTmrain13());
        destino.setTmrafn13(origen.getTmrafn13());
        destino.setTmcocu13(origen.getTmcocu13());
        
        // Inicializar campos adicionales
        destino.setTmsfeb(BigDecimal.ZERO);
        destino.setTmdeb(BigDecimal.ZERO);
        destino.setTmsfev(BigDecimal.ZERO);
        destino.setTmdev(BigDecimal.ZERO);
        destino.setTmsfet(BigDecimal.ZERO);
        destino.setTmhora(origen.getTmhora());
        destino.setTmtrans("0124");
        destino.setTmsuctx(origen.getTmsuctx());
        
        return destino;
    }
    
    
}
