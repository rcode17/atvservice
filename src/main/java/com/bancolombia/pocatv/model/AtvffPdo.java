package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "atvffpdo")
@IdClass(AtvffPdoId.class)
public class AtvffPdo {
    
    @Id
    @Column(name = "xpcopr", nullable = false)
    private String xpcopr; // Definido como bpchar(2)

    @Id
    @Column(name = "xpcodo", nullable = false)
    private String xpcodo; // Definido como bpchar(3)

    @Column(name = "xpdsdo", nullable = false)
    private String xpdsdo; // Definido como bpchar(25)

    @Column(name = "xpcta", nullable = false)
    private BigDecimal xpcta; // Definido como numeric(12)

    @Column(name = "xpstdo", nullable = false)
    private String xpstdo; // Definido como bpchar(1)

    @Column(name = "xpfeca", nullable = false)
    private String xpfeca; // Definido como bpchar(1)

    // Getters y Setters
    public String getXpcopr() {
        return xpcopr;
    }

    public void setXpcopr(String xpcopr) {
        this.xpcopr = xpcopr;
    }

    public String getXpcodo() {
        return xpcodo;
    }

    public void setXpcodo(String xpcodo) {
        this.xpcodo = xpcodo;
    }

    public String getXpdsdo() {
        return xpdsdo;
    }

    public void setXpdsdo(String xpdsdo) {
        this.xpdsdo = xpdsdo;
    }

    public BigDecimal getXpcta() {
        return xpcta;
    }

    public void setXpcta(BigDecimal xpcta) {
        this.xpcta = xpcta;
    }

    public String getXpstdo() {
        return xpstdo;
    }

    public void setXpstdo(String xpstdo) {
        this.xpstdo = xpstdo;
    }

    public String getXpfeca() {
        return xpfeca;
    }

    public void setXpfeca(String xpfeca) {
        this.xpfeca = xpfeca;
    }
}
