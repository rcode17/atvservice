package com.bancolombia.pocatv.model;



import lombok.Data;

import java.math.BigDecimal;

import com.bancolombia.pocatv.model.AtvffcasalId;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "atvffcasal")
@IdClass(AtvffcasalId.class)  // Especificar la clase de ID compuesta
public class Atvffcasal {
    
    @Id
    @Column(name = "sdeofic")
    private Integer sdeofic; // OF. FISICA
    
    @Id
    @Column(name = "sdtdoc")
    private String sdtdoc; // DOCUMENTO
    
    @Column(name = "sdfech")
    private Integer sdfech; // FECHA SALDO
    
    @Column(name = "sdtpro")
    private String sdtpro; // PRODUCTO
    
    @Column(name = "sddisp")
    private BigDecimal sddisp; // SALDO DISP.
    
    @Column(name = "sdeofco")
    private Integer sdeofco; // OF. CONTABLE
    
    @Column(name = "sdusu1")
    private String sdusu1; // USUARIO 1
    
    @Column(name = "sdusu2")
    private String sdusu2; // USUARIO 2
    
    @Column(name = "sdusu3")
    private String sdusu3; // USUARIO 3
    
    @Column(name = "sdusu4")
    private BigDecimal sdusu4; // USUARIO 4
    
    @Column(name = "sdusu5")
    private BigDecimal sdusu5; // USUARIO 5
}