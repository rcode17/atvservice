package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atvffmd")
@IdClass(AtvffmdId.class)
public class Atvffmd {

    @Id
    @Column(name = "mdcopr", nullable = false, length = 2)
    private String mdcopr; // Código de proveedor

    @Id
    @Column(name = "mdcodo", nullable = false, length = 3)
    private String mdcodo; // Código

    @Id
    @Column(name = "mdano", nullable = false)
    private Integer mdano; // Año

    @Id
    @Column(name = "mdmes", nullable = false)
    private Short mdmes; // Mes

    @Column(name = "mddia", nullable = false)
    private Short mddia; // Día

    @Column(name = "mdrang1", nullable = false)
    private Short mdrang1; // Rango 1

    @Column(name = "mdrang2", nullable = false)
    private Short mdrang2; // Rango 2
}