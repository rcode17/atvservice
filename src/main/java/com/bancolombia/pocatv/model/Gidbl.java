package com.bancolombia.pocatv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "gidbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gidbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O usa otro tipo de generación si es necesario
    private Long id; // Si deseas un ID auto-generado, de lo contrario, puedes omitir esta línea

    @Column(name = "gxnobr", nullable = false)
    private String gxnobr; // Número de sucursal

    @Column(name = "gxnoac", nullable = false)
    private String gxnoac; // Número de cuenta

    @Column(name = "gxfccd", nullable = false)
    private LocalDate gxfccd; // Código de la moneda

    @Column(name = "gxfac", nullable = false)
    private String gxfac; // Cuenta de finalización

    @Column(name = "gxdtdy", nullable = false)
    private LocalDate gxdtdy; // Fecha del saldo diario

    @Column(name = "gxamdt", nullable = false)
    private BigDecimal gxamdt; // Monto del saldo final
}
