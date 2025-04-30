package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "atvffsai2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Atvffsai2Id.class)
public class Atvffsai2 {
    
    @Id
    @Column(name = "tmcdsu")
    private BigDecimal tmcdsu; // CODIGO SUCURSAL
    
    @Id
    @Column(name = "tmcopr")
    private String tmcopr; // CODIGO PRODUCTO
    
    @Id
    @Column(name = "tmcodo")
    private String tmcodo; // CODIGO DOCUMENTO
    
    @Id
    @Column(name = "tmfear")
    private BigDecimal tmfear; // FECHA ARQUEO
    
    @Column(name = "tmsuc")
    private String tmsuc; // NOMBRE SUCURSAL
    
    @Column(name = "tmcdsuf")
    private BigDecimal tmcdsuf; // CODIGO SUCURSAL
    
    @Column(name = "tmprcu")
    private String tmprcu; // PERSONA RESP. CUSTODIA
    
    @Column(name = "tmcedcn")
    private String tmcedcn; // CEDULA RESP. CUSTODIA
    
    @Column(name = "tmpear")
    private String tmpear; // PERSONA REALIZA ARQUEO
    
    @Column(name = "tmcedan")
    private String tmcedan; // CEDULA REALIZA ARQUEO
    
    @Column(name = "tmsubg")
    private String tmsubg; // NOMBRE SUBGERENTE
    
    @Column(name = "tmcesbn")
    private String tmcesbn; // CEDULA SUBGENTE
    
    @Column(name = "tmsfar")
    private BigDecimal tmsfar; // SALDO FISICO ARQUEO
    
    @Column(name = "tmsig")
    private String tmsig; // SIGNO DIFERENCIA
    
    @Column(name = "tmdif")
    private BigDecimal tmdif; // DIFERENCIA
    
    @Column(name = "tmres")
    private String tmres; // RESULTADO
    
    @Column(name = "tmobs")
    private String tmobs; // OBSERVACIONES
    
    @Column(name = "tmobso")
    private String tmobso; // OBSERVACIONES OTROS
    
    @Column(name = "tmsfeb")
    private BigDecimal tmsfeb; // SALDO FISICO EFEC.BO
    
    @Column(name = "tmseb")
    private String tmseb; // SIGNO DIFERENCIA
    
    @Column(name = "tmdeb")
    private BigDecimal tmdeb; // DIFERENCIA
    
    @Column(name = "tmsfev")
    private BigDecimal tmsfev; // SALDO FISICO EFEC.VE
    
    @Column(name = "tmsev")
    private String tmsev; // SIGNO DIFERENCIA
    
    @Column(name = "tmdev")
    private BigDecimal tmdev; // DIFERENCIA
    
    @Column(name = "tmsfet")
    private BigDecimal tmsfet; // SALDO FISICO TOTAL
    
    @Column(name = "tmhora")
    private BigDecimal tmhora; // HORA ARQUEO
    
    @Column(name = "tmsuctx")
    private String tmsuctx; // SUCURSAL TEXTO
}