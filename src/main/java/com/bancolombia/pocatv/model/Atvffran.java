package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ATVFFRAN")
@IdClass(AtvffranId.class)
public class Atvffran {

    @Id
    @Column(name = "RNFREC", length = 1)
    private String rnfrec; // Frecuencia arqueo

    @Id
    @Column(name = "RNCANT", precision = 3)
    private Integer rncant; // Días de frecuencia

    @Column(name = "RNRAIN")
    private Integer rnrain; // Rango inicial

    @Column(name = "RNRAFN")
    private Integer rnrafn; // Rango final
}