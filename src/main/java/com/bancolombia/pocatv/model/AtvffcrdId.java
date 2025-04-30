package com.bancolombia.pocatv.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AtvffcrdId implements Serializable {
 
 @Column(name = "cdano", nullable = false)
 private Integer cdano; // Año
 
 @Column(name = "cdmes", nullable = false)
 private Integer cdmes; // Mes
 
 @Column(name = "cdcodsuc", nullable = false)
 private Integer cdcodsuc; // Código Sucursal
 
 @Column(name = "cdcopr", nullable = false, length = 2)
 private String cdcopr; // Código Producto
 
 @Column(name = "cdcodo", nullable = false, length = 3)
 private String cdcodo; // Código Documento
 
 @Column(name = "cdfear", nullable = false)
 private LocalDate cdfear; // Fecha Arqueo
 
 @Override
 public boolean equals(Object o) {
     if (this == o) return true;
     if (o == null || getClass() != o.getClass()) return false;
     AtvffcrdId that = (AtvffcrdId) o;
     return Objects.equals(cdano, that.cdano) &&
            Objects.equals(cdmes, that.cdmes) &&
            Objects.equals(cdcodsuc, that.cdcodsuc) &&
            Objects.equals(cdcopr, that.cdcopr) &&
            Objects.equals(cdcodo, that.cdcodo) &&
            Objects.equals(cdfear, that.cdfear);
 }
 
 @Override
 public int hashCode() {
     return Objects.hash(cdano, cdmes, cdcodsuc, cdcopr, cdcodo, cdfear);
 }
}