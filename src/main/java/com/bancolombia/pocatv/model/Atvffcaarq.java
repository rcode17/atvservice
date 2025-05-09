package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "atvffcaarq") // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvffcaarqId.class) // Indica que la llave compuesta está definida en AtvffcaarqId
public class Atvffcaarq {

    @Id
    @Column(name = "rqfear", nullable = false)
    private LocalDate  rqfear; // Fecha arqueo

    @Id
    @Column(name = "rqcdsu", precision = 5, nullable = false)
    private Integer rqcdsu; // Código sucursal

    @Id
    @Column(name = "rqcopr", length = 5, nullable = false)
    private String rqcopr; // Código producto

    @Id
    @Column(name = "rqcodo", length = 5, nullable = false)
    private String rqcodo; // Código documento

    @Column(name = "rqsuc", length = 20, nullable = false)
    private String rqsuc; // Nombre sucursal

    @Column(name = "rqcdsuf", precision = 5, nullable = false)
    private Integer rqcdsuf; // Código sucursal final

    @Column(name = "rqprcu", length = 20, nullable = false)
    private String rqprcu; // Persona responsable de custodia

    @Column(name = "rqcedcn", length = 10, nullable = false)
    private String rqcedcn; // Cédula responsable de custodia

    @Column(name = "rqpear", length = 20, nullable = false)
    private String rqpear; // Persona que realiza el arqueo

    @Column(name = "rqcedan", length = 10, nullable = false)
    private String rqcedan; // Cédula de quien realiza el arqueo

    @Column(name = "rqsubg", length = 20, nullable = false)
    private String rqsubg; // Nombre del subgerente

    @Column(name = "rqcesbn", length = 10, nullable = false)
    private String rqcesbn; // Cédula del subgerente

    @Column(name = "rqsfar", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqsfar; // Saldo físico del arqueo

    @Column(name = "rqsig", length = 1, nullable = false)
    private String rqsig; // Signo de la diferencia

    @Column(name = "rqdif", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqdif; // Diferencia

    @Column(name = "rqres", length = 2, nullable = false)
    private String rqres; // Resultado

    @Column(name = "rqobs", length = 250, nullable = false)
    private String rqobs; // Observaciones

    @Column(name = "rqobso", length = 250, nullable = false)
    private String rqobso; // Observaciones adicionales

    // Rangos y códigos de cuenta (1-13)
    @Column(name = "rqrain1", length = 16, nullable = false)
    private String rqrain1; // Rango inicial 1

    @Column(name = "rqrafn1", length = 16, nullable = false)
    private String rqrafn1; // Rango final 1

    @Column(name = "rqcocu1", length = 16, nullable = false)
    private String rqcocu1; // Código de cuenta 1

    @Column(name = "rqrain2", length = 16, nullable = false)
    private String rqrain2;

    @Column(name = "rqrafn2", length = 16, nullable = false)
    private String rqrafn2;

    @Column(name = "rqcocu2", length = 16, nullable = false)
    private String rqcocu2;

    @Column(name = "rqrain3", length = 16, nullable = false)
    private String rqrain3;

    @Column(name = "rqrafn3", length = 16, nullable = false)
    private String rqrafn3;

    @Column(name = "rqcocu3", length = 16, nullable = false)
    private String rqcocu3;

    @Column(name = "rqrain4", length = 16, nullable = false)
    private String rqrain4;

    @Column(name = "rqrafn4", length = 16, nullable = false)
    private String rqrafn4;

    @Column(name = "rqcocu4", length = 16, nullable = false)
    private String rqcocu4;

    @Column(name = "rqrain5", length = 16, nullable = false)
    private String rqrain5;

    @Column(name = "rqrafn5", length = 16, nullable = false)
    private String rqrafn5;

    @Column(name = "rqcocu5", length = 16, nullable = false)
    private String rqcocu5;

    @Column(name = "rqrain6", length = 16, nullable = false)
    private String rqrain6;

    @Column(name = "rqrafn6", length = 16, nullable = false)
    private String rqrafn6;

    @Column(name = "rqcocu6", length = 16, nullable = false)
    private String rqcocu6;

    @Column(name = "rqrain7", length = 16, nullable = false)
    private String rqrain7;

    @Column(name = "rqrafn7", length = 16, nullable = false)
    private String rqrafn7;

    @Column(name = "rqcocu7", length = 16, nullable = false)
    private String rqcocu7;

    @Column(name = "rqrain8", length = 16, nullable = false)
    private String rqrain8;

    @Column(name = "rqrafn8", length = 16, nullable = false)
    private String rqrafn8;

    @Column(name = "rqcocu8", length = 16, nullable = false)
    private String rqcocu8;

    @Column(name = "rqrain9", length = 16, nullable = false)
    private String rqrain9;

    @Column(name = "rqrafn9", length = 16, nullable = false)
    private String rqrafn9;

    @Column(name = "rqcocu9", length = 16, nullable = false)
    private String rqcocu9;
    
    @Column(name = "rqrain10", length = 16, nullable = false)
    private String rqrain10; // Rango inicial 1

    @Column(name = "rqrafn10", length = 16, nullable = false)
    private String rqrafn10; // Rango final 1

    @Column(name = "rqcocu10", length = 16, nullable = false)
    private String rqcocu10; // Código de cuenta 1
    
    @Column(name = "rqrain11", length = 16, nullable = false)
    private String rqrain11; // Rango inicial 1

    @Column(name = "rqrafn11", length = 16, nullable = false)
    private String rqrafn11; // Rango final 1

    @Column(name = "rqcocu11", length = 16, nullable = false)
    private String rqcocu11; // Código de cuenta 1

    @Column(name = "rqrain12", length = 16, nullable = false)
    private String rqrain12;

    @Column(name = "rqrafn12", length = 16, nullable = false)
    private String rqrafn12;

    @Column(name = "rqcocu12", length = 16, nullable = false)
    private String rqcocu12;

    @Column(name = "rqrain13", length = 16, nullable = false)
    private String rqrain13;

    @Column(name = "rqrafn13", length = 16, nullable = false)
    private String rqrafn13;

    @Column(name = "rqcocu13", length = 16, nullable = false)
    private String rqcocu13;

    @Column(name = "rqsfeb", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqsfeb; // Saldo bóveda

    @Column(name = "rqdeb", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqdeb; // Diferencia bóveda

    @Column(name = "rqsfev", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqsfev; // Saldo ventanilla

    @Column(name = "rqdev", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqdev; // Diferencia ventanilla

    @Column(name = "rqsfet", precision = 15, scale = 2, nullable = false)
    private BigDecimal rqsfet; // Saldo físico total

    @Column(name = "rqhora", precision = 6, nullable = false)
    private Integer rqhora; // Hora del arqueo

    @Column(name = "rqtrans", length = 4, nullable = false)
    private String rqtrans; // Transacción
}