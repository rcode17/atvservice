package com.bancolombia.pocatv.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Table(name = "atvffcri")
public class AtvffCri {

    @EmbeddedId
    private AtvffCriId id; // Clave primaria compuesta

    @Column(name = "crnomsuc", length = 15, nullable = false)
    private String crnomsuc;

    @Column(name = "crdifer", nullable = false)
    private BigDecimal crdifer;

    @Column(name = "crcedan", length = 10, nullable = false)
    private String crcedan;

    @Column(name = "crres", length = 2, nullable = false)
    private String crres;

    @Column(name = "crdif", nullable = false)
    private BigDecimal crdif;

}