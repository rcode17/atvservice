package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "atvffsai1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Atvffsai1Id.class)
public class Atvffsai1 {
    
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
    
    @Column(name = "tmhora")
    private BigDecimal tmhora; // HORA ARQUEO
    
    @Column(name = "tmsuctx")
    private String tmsuctx; // SUCURSAL TEXTO
    
    @Column(name = "tmobs")
    private String tmobs; // OBSERVACIONES
    
    @Column(name = "tmobso")
    private String tmobso; // OBSERVACIONES OTROS
    
    @Column(name = "tmrain1")
    private String tmrain1; // RANGO INICIAL
    
    @Column(name = "tmrafn1")
    private String tmrafn1; // RANGO FINAL
    
    @Column(name = "tmcocu1")
    private String tmcocu1; // CODIGO CUENTA
    
    @Column(name = "tmrain2")
    private String tmrain2; // RANGO INICIAL
    
    @Column(name = "tmrafn2")
    private String tmrafn2; // RANGO FINAL
    
    @Column(name = "tmcocu2")
    private String tmcocu2; // CODIGO CUENTA
    
    @Column(name = "tmrain3")
    private String tmrain3; // RANGO INICIAL
    
    @Column(name = "tmrafn3")
    private String tmrafn3; // RANGO FINAL
    
    @Column(name = "tmcocu3")
    private String tmcocu3; // CODIGO CUENTA
    
    @Column(name = "tmrain4")
    private String tmrain4; // RANGO INICIAL
    
    @Column(name = "tmrafn4")
    private String tmrafn4; // RANGO FINAL
    
    @Column(name = "tmcocu4")
    private String tmcocu4; // CODIGO CUENTA
    
    @Column(name = "tmrain5")
    private String tmrain5; // RANGO INICIAL
    
    @Column(name = "tmrafn5")
    private String tmrafn5; // RANGO FINAL
    
    @Column(name = "tmcocu5")
    private String tmcocu5; // CODIGO CUENTA
    
    @Column(name = "tmrain6")
    private String tmrain6; // RANGO INICIAL
    
    @Column(name = "tmrafn6")
    private String tmrafn6; // RANGO FINAL
    
    @Column(name = "tmcocu6")
    private String tmcocu6; // CODIGO CUENTA
    
    @Column(name = "tmrain7")
    private String tmrain7; // RANGO INICIAL
    
    @Column(name = "tmrafn7")
    private String tmrafn7; // RANGO FINAL
    
    @Column(name = "tmcocu7")
    private String tmcocu7; // CODIGO CUENTA
    
    @Column(name = "tmrain8")
    private String tmrain8; // RANGO INICIAL
    
    @Column(name = "tmrafn8")
    private String tmrafn8; // RANGO FINAL
    
    @Column(name = "tmcocu8")
    private String tmcocu8; // CODIGO CUENTA
    
    @Column(name = "tmrain9")
    private String tmrain9; // RANGO INICIAL
    
    @Column(name = "tmrafn9")
    private String tmrafn9; // RANGO FINAL
    
    @Column(name = "tmcocu9")
    private String tmcocu9; // CODIGO CUENTA
    
    @Column(name = "tmrain10")
    private String tmrain10; // RANGO INICIAL
    
    @Column(name = "tmrafn10")
    private String tmrafn10; // RANGO FINAL
    
    @Column(name = "tmcocu10")
    private String tmcocu10; // CODIGO CUENTA
    
    @Column(name = "tmrain11")
    private String tmrain11; // RANGO INICIAL
    
    @Column(name = "tmrafn11")
    private String tmrafn11; // RANGO FINAL
    
    @Column(name = "tmcocu11")
    private String tmcocu11; // CODIGO CUENTA
    
    @Column(name = "tmrain12")
    private String tmrain12; // RANGO INICIAL
    
    @Column(name = "tmrafn12")
    private String tmrafn12; // RANGO FINAL
    
    @Column(name = "tmcocu12")
    private String tmcocu12; // CODIGO CUENTA
    
    @Column(name = "tmrain13")
    private String tmrain13; // RANGO INICIAL
    
    @Column(name = "tmrafn13")
    private String tmrafn13; // RANGO FINAL
    
    @Column(name = "tmcocu13")
    private String tmcocu13; // CODIGO CUENTA
}