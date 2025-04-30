package com.bancolombia.pocatv.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "ATVFFCAD")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvffcadId.class)
public class Atvffcad {
    
    @Id
    @Column(name = "CAANO")
    private Integer caano;
    
    @Id
    @Column(name = "CAMES")
    private Integer cames;
    
    @Id
    @Column(name = "CADIA")
    private Integer cadia;
    
    @Id
    @Column(name = "CACOPR")
    private String cacopr;
    
    @Id
    @Column(name = "CACODO")
    private String cacodo;
    
    @Column(name = "CASINV")
    private BigDecimal casinv;
    
    @Column(name = "CASCONT")
    private BigDecimal cascont;
    
    @Column(name = "CADIF")
    private BigDecimal cadif;
}