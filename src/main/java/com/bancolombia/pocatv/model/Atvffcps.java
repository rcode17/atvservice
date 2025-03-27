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
    private Integer year;

    @Column(name = "csmes", nullable = false)
    private Integer month;

    @Column(name = "cscopr", length = 2, nullable = false)
    private String codigoProducto;

    @Column(name = "cscodo", length = 3, nullable = false)
    private String codigoDocumento;

    @Column(name = "cscodsuc", nullable = false)
    private Integer branchCode;

    @Column(name = "csnomsuc", length = 30, nullable = false)
    private String branchName;

    @Column(name = "cscumpli", nullable = false)
    private Integer porcentajeCumplimiento;

    @Column(name = "cscalid", nullable = false)
    private Integer porcentajeCalidad;
}
