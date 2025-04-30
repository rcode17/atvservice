package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffesal")
public class Atvffesal {
    @Id
    @Column(name = "esafec", nullable = false)
    private Long esafec; // Fecha (clave primaria)

    @Column(name = "esager", nullable = false)
    private String esager; // Agencia

    @Column(name = "esaced", nullable = false)
    private String esaced; // Cédula

    @Column(name = "saofic", nullable = false)
    private Long saofic; // Oficina

    @Column(name = "saofco", nullable = false)
    private Long saofco; // Código de oficina

    @Column(name = "sahora", nullable = false)
    private Long sahora; // Hora

    @Column(name = "saoftx", nullable = false)
    private String saoftx; // Indicador de filial
}