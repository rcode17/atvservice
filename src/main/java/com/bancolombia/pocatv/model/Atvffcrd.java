package com.bancolombia.pocatv.model;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atvffcrd")
public class Atvffcrd {
 
 @EmbeddedId
 private AtvffcrdId id;
 
 @Column(name = "cdcon", nullable = false)
 private Integer cdcon; // Consecutivo
 
 @Column(name = "cdnomsuc", nullable = false, length = 15)
 private String cdnomsuc; // Área Operativa
 
 @Column(name = "cddif", nullable = false, precision = 15, scale = 2)
 private BigDecimal cddif; // Diferencia
 
 @Column(name = "cdcedan", nullable = false, length = 10)
 private String cdcedan; // Cédula Realiza Arqueo
 
 @Column(name = "cdsfar", nullable = false, precision = 15, scale = 2)
 private BigDecimal cdsfar; // Saldo Físico Arqueo
 
 @Column(name = "cdres", nullable = false, length = 2)
 private String cdres; // Resultado
}