package com.bancolombia.pocatv.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "atvffrec")
public class Atvffrec {

    @Id
    @Column(name = "rcfere", length = 8)
    private String rcfere; // Fecha rechazo (clave primaria)

    @Column(name = "rcfear")
    private Date rcfear; // Puedes ajustar el tipo (puede ser Date o Boolean según la lógica)

    @Column(name = "rcsuc", length = 20)
    private String rcsuc;

    @Column(name = "rccdsu")
    private Integer rccdsu;

    @Column(name = "rccdsuf")
    private Integer rccdsuf;

    @Column(name = "rcprcu", length = 20)
    private String rcprcu;

    @Column(name = "rccedcn", length = 10)
    private String rccedcn;

    @Column(name = "rcpear", length = 20)
    private String rcpear;

    @Column(name = "rccedan", length = 10)
    private String rccedan;

    @Column(name = "rcsubg", length = 20)
    private String rcsubg;

    @Column(name = "rccesbn", length = 10)
    private String rccesbn;

    @Column(name = "rccopr", length = 2)
    private String rccopr;

    @Column(name = "rccodo", length = 3)
    private String rccodo;

    @Column(name = "rcsfar", precision = 15, scale = 2)
    private BigDecimal rcsfar;

    @Column(name = "rcdif", precision = 15, scale = 2)
    private BigDecimal rcdif;

    @Column(name = "rcres", length = 2)
    private String rcres;

    @Column(name = "rcobsn", length = 250)
    private String rcobsn;

    @Column(name = "rcobsno", length = 250)
    private String rcobsno;

    @Column(name = "rcsfeb", precision = 17, scale = 2)
    private BigDecimal rcsfeb;

    @Column(name = "rcdeb", precision = 17, scale = 2)
    private BigDecimal rcdeb;

    @Column(name = "rcsfev", precision = 17, scale = 2)
    private BigDecimal rcsfev;

    @Column(name = "rcdev", precision = 17, scale = 2)
    private BigDecimal rcdev;

    @Column(name = "rcsfet", precision = 17, scale = 2)
    private BigDecimal rcsfet;

    @Column(name = "rcsts", length = 1)
    private String rcsts;

    @Column(name = "rchora")
    private Integer rchora;

    @Column(name = "rctrans", length = 4)
    private String rctrans;
    
    @Column(name = "rcrech", length = 4)
    private String rcrech;

}
