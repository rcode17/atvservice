package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffdsun")
@IdClass(AtvffDsunId.class)
public class AtvffDsun {
    
    @Id
    @Column(name = "dnano", nullable = false)
    private Integer dnano;
    
    @Id
    @Column(name = "dnmes", nullable = false)
    private Integer dnmes;
    
    @Id
    @Column(name = "dnxnnmky", nullable = false)
    private Integer dnxnnmky;
    
    @Column(name = "dncumplit")
    private Integer dncumplit;
    
    @Column(name = "dncalidt")
    private Integer dncalidt;
    
    @Column(name = "dnnombre", length = 30)
    private String dnnombre;
    
    // Campos de cumplimiento y calidad (1-70)
    @Column(name = "dncumpli1", length = 3)
    private String dncumpli1;
    
    @Column(name = "dncalid1", length = 3)
    private String dncalid1;
    
    // Continúan los campos dncumpli2-70 y dncalid2-70
    // Por brevedad, solo incluyo algunos ejemplos
    
    @Column(name = "dncumpli2", length = 3)
    private String dncumpli2;
    
    @Column(name = "dncalid2", length = 3)
    private String dncalid2;
    
    // ... (campos similares hasta dncumpli70 y dncalid70)
    
    @Column(name = "dncalid70", length = 3)
    private String dncalid70;
    
    // Método para verificar si un campo de calidad es menor o igual a 50 y no es "NA"
    public boolean isCalidadBaja(String calidad) {
        if (calidad == null || "NA ".equals(calidad.trim())) {
            return false;
        }
        try {
            int valor = Integer.parseInt(calidad.trim());
            return valor <= 50;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}