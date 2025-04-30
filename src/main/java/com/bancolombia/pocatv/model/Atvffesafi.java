package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffesafi")
public class Atvffesafi {
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
}