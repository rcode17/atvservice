package com.bancolombia.pocatv.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Atvffgesu")
public class Atvffgesu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codsuc")
    private Integer codsuc;

    @Column(name = "codpro", length = 2)
    private String codpro;

    @Column(name = "coddoc", length = 3)
    private String coddoc;

    @Column(name = "aqfear", length = 10)
    private String aqfear;

    @Column(name = "coddesg")
    private Integer coddesg;
}
