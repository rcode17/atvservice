package com.bancolombia.pocatv.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "ATVFFSAL")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvffsalId.class)
public class Atvffsal {
    
    @Id
    @Column(name = "SATPRO")
    private String satpro;
    
    @Id
    @Column(name = "SATDOC")
    private String satdoc;
    
    @Column(name = "SAFECH")
    private Long safech;
    
    @Column(name = "SADISP")
    private BigDecimal sadisp;
    
    // Añadimos los campos necesarios para la operación de actualización
    @Column(name = "SAOFIC")
    private String saofic;
    
    @Column(name = "SAOFCO")
    private String saofco;
}