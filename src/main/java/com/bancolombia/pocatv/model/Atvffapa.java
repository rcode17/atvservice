package com.bancolombia.pocatv.model;


import lombok.Data;

import java.time.LocalDate;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ATVFFAPA")
@IdClass(AtvffapaId.class)
public class Atvffapa {
    
    @Id
    @Column(name = "APANO")
    private Integer apano;
    
    @Id
    @Column(name = "APMES")
    private Integer apmes;
    
    @Id
    @Column(name = "APCODSUC")
    private Integer apcodsuc;
    
    @Id
    @Column(name = "APCOPR")
    private String apcopr;
    
    @Id
    @Column(name = "APCODO")
    private String apcodo;
    
    @Id
    @Column(name = "APFEAR")
    private LocalDate apfear;
    
    @Column(name = "APNOMSUC")
    private String apnomsuc;
    
    @Id
    @Column(name = "APCEDCN")
    private String apcedcn;
    
    @Id
    @Column(name = "APCEDAN")
    private String apcedan;
    
    @Id
    @Column(name = "APSFAR")
    private BigDecimal apsfar;
    
    @Id
    @Column(name = "APDIF")
    private BigDecimal apdif;
}
