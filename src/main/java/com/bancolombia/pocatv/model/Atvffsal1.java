package com.bancolombia.pocatv.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.persistence.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atvffsal1", schema = "public")
@IdClass(Atvffsal1Id.class)
public class Atvffsal1 implements Serializable {
    
    @Id
    @Column(name = "safech")
    private Long safech;        // FECHA SALDO
    
    @Id
    @Column(name = "satpro")
    private String satpro;      // PRODUCTO
    
    @Column(name = "satdoc")
    private String satdoc;      // DOCUMENTO
    
    @Column(name = "sacger")
    private String sacger;      // CEDULA DEL GERENTE
    
    @Column(name = "sanger")
    private String sanger;      // NOMBRE DEL GERENTE
    
    @Column(name = "sadisp")
    private BigDecimal sadisp;  // SALDO DISPONIBLE
    
    @Column(name = "saanul")
    private Long saanul;        // SALDO ANULADO
    
    @Column(name = "sapimp")
    private Long sapimp;        // SALDO PENDIENTE IMPRIMIR
    
    @Column(name = "saimpr")
    private Long saimpr;        // SALDO IMPRESO
    
    @Column(name = "sautil")
    private Long sautil;        // SALDO UTILIZADO
    
    @Column(name = "saofic")
    private Integer saofic;     // OFICINA FISICA
    
    @Column(name = "saofco")
    private Integer saofco;     // OFICINA CONTABLE
}