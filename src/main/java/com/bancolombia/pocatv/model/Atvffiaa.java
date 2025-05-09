package com.bancolombia.pocatv.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "atvffiaa")
public class Atvffiaa {

    @Id
    @Column(name = "iaano")
    private Integer iaano; // Clave primaria

    @Column(name = "ianombre", length = 25, nullable = false)
    private String ianombre;

    @Column(name = "iaenero", precision = 3, scale = 0, nullable = false)
    private Integer iaenero; // Usamos BigDecimal para representar números decimales

    @Column(name = "iafeb", precision = 3, scale = 0, nullable = false)
    private Integer iafeb;

    @Column(name = "iamarzo", precision = 3, scale = 0, nullable = false)
    private Integer iamartzo;

    @Column(name = "iaabril", precision = 3, scale = 0, nullable = false)
    private Integer iaabril;

    @Column(name = "iamayo", precision = 3, scale = 0, nullable = false)
    private Integer iamay;

    @Column(name = "iajunio", precision = 3, scale = 0, nullable = false)
    private Integer iajunio;

    @Column(name = "iajulio", precision = 3, scale = 0, nullable = false)
    private Integer iajulio;

    @Column(name = "iaagosto", precision = 3, scale = 0, nullable = false)
    private Integer iaagosto;

    @Column(name = "iasep", precision = 3, scale = 0, nullable = false)
    private Integer iasep;

    @Column(name = "iaoctubre", precision = 3, scale = 0, nullable = false)
    private Integer iaoctubre;

    @Column(name = "ianov", precision = 3, scale = 0, nullable = false)
    private Integer ianov;

    @Column(name = "iadic", precision = 3, scale = 0, nullable = false)
    private Integer iadic;

    @Column(name = "iaregion", length = 6, nullable = false)
    private String iaregion;

    @Column(name = "iaproma", precision = 3, scale = 0, nullable = false)
    private Integer iaproma;
    


}