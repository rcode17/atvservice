package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "atvffcps")
@Data
public class Atvffcps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "csano", nullable = false)
    private Integer csano;

    @Column(name = "csmes", nullable = false)
    private Integer csmes;

    @Column(name = "cscopr", length = 2, nullable = false)
    private String cscopr;

    @Column(name = "cscodo", length = 3, nullable = false)
    private String cscodo;

    @Column(name = "cscodsuc", nullable = false)
    private Integer cscodsuc;

    @Column(name = "csnomsuc", length = 30, nullable = false)
    private String csnomsuc;

    @Column(name = "cscumpli", nullable = false)
    private Integer cscumpli;

    @Column(name = "cscalid", nullable = false)
    private Integer cscalid;
}
