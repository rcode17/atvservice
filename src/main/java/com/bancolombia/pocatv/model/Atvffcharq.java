package com.bancolombia.pocatv.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "atvffcharq")
public class Atvffcharq {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RQFEAR")
    private Integer fechaArqueo;

    @Column(name = "RQSUC")
    private String sucursal;

    @Column(name = "RQCDSU")
    private Integer codigoSucursal;

    @Column(name = "RQPRCU")
    private String responsableCustodia;

    @Column(name = "RQSFAR")
    private Double saldoFisicoArqueo;

    @Column(name = "RQDIF")
    private Double diferencia;

    @Column(name = "RQOBS", length = 250)
    private String observaciones;
    
    @Column(name = "rqobso")
    private String rqobso; // OBSERVACIONES OTROS
    
    @Column(name = "rqrain1")
    private String rqrain1; // RANGO INICIAL 1
    
    @Column(name = "rqrafn1")
    private String rqrafn1; // RANGO FINAL 1
    
    @Column(name = "rqcocu1")
    private String rqcocu1; // CODIGO CUENTA 1
    
    // Campos para rangos 2-13 (similar a los anteriores)
    @Column(name = "rqrain2")
    private String rqrain2;
    
    @Column(name = "rqrafn2")
    private String rqrafn2;
    
    @Column(name = "rqcocu2")
    private String rqcocu2;
    
    @Column(name = "rqrain3")
    private String rqrain3;
    
    @Column(name = "rqrafn3")
    private String rqrafn3;
    
    @Column(name = "rqcocu3")
    private String rqcocu3;
    
    @Column(name = "rqrain4")
    private String rqrain4;
    
    @Column(name = "rqrafn4")
    private String rqrafn4;
    
    @Column(name = "rqcocu4")
    private String rqcocu4;
    
    @Column(name = "rqrain5")
    private String rqrain5;
    
    @Column(name = "rqrafn5")
    private String rqrafn5;
    
    @Column(name = "rqcocu5")
    private String rqcocu5;
    
    @Column(name = "rqrain6")
    private String rqrain6;
    
    @Column(name = "rqrafn6")
    private String rqrafn6;
    
    @Column(name = "rqcocu6")
    private String rqcocu6;
    
    @Column(name = "rqrain7")
    private String rqrain7;
    
    @Column(name = "rqrafn7")
    private String rqrafn7;
    
    @Column(name = "rqcocu7")
    private String rqcocu7;
    
    @Column(name = "rqrain8")
    private String rqrain8;
    
    @Column(name = "rqrafn8")
    private String rqrafn8;
    
    @Column(name = "rqcocu8")
    private String rqcocu8;
    
    @Column(name = "rqrain9")
    private String rqrain9;
    
    @Column(name = "rqrafn9")
    private String rqrafn9;
    
    @Column(name = "rqcocu9")
    private String rqcocu9;
    
    @Column(name = "rqrain10")
    private String rqrain10;
    
    @Column(name = "rqrafn10")
    private String rqrafn10;
    
    @Column(name = "rqcocu10")
    private String rqcocu10;
    
    @Column(name = "rqrain11")
    private String rqrain11;
    
    @Column(name = "rqrafn11")
    private String rqrafn11;
    
    @Column(name = "rqcocu11")
    private String rqcocu11;
    
    @Column(name = "rqrain12")
    private String rqrain12;
    
    @Column(name = "rqrafn12")
    private String rqrafn12;
    
    @Column(name = "rqcocu12")
    private String rqcocu12;
    
    @Column(name = "rqrain13")
    private String rqrain13;
    
    @Column(name = "rqrafn13")
    private String rqrafn13;
    
    @Column(name = "rqcocu13")
    private String rqcocu13;
    
    @Column(name = "rqusu1")
    private String rqusu1; // CAMPO USUARIO 1
    
    @Column(name = "rqusu2")
    private String rqusu2; // CAMPO USUARIO 2
    
    @Column(name = "rqusu3")
    private String rqusu3; // CAMPO USUARIO 3
    
    @Column(name = "rqusu4")
    private BigDecimal rqusu4; // CAMPO USUARIO 4
    
    @Column(name = "rqusu5")
    private BigDecimal rqusu5; // CAMPO USUARIO 5
}

