package com.bancolombia.pocatv.model;


import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "atvlfarq")
@IdClass(AtvlfarqId.class)
public class Atvlfarq {
 
 @Id
 @Column(name = "saofco")
 private String saofco; // Clave de organización
 
 @Id
 @Column(name = "satpro")
 private String satpro; // Código de producto
 
 @Id
 @Column(name = "satdoc")
 private String satdoc; // Código de documento
 
 @Id
 @Column(name = "safech")
 private String safech; // Código de cheque
 
 @Column(name = "sadisp", columnDefinition = "NUMERIC(15,2)")
 private BigDecimal sadisp; // Saldo disponible
 
 @Column(name = "saofic")
 private Integer saofic; // Oficina física
}

