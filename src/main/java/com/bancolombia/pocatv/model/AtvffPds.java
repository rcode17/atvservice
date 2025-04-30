package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffpds")
@IdClass(AtvffPdsId.class)
public class AtvffPds {

    @Id
    @Column(name = "xscosu", nullable = false)
    private Integer xscosu; // Definido como numeric(5)

    @Id
    @Column(name = "xscopr", nullable = false)
    private String xscopr; // Definido como bpchar(2)

    @Id
    @Column(name = "xscodo", nullable = false)
    private String xscodo; // Definido como bpchar(3)

    // Getters y Setters
    public Integer getXscosu() {
        return xscosu;
    }

    public void setXscosu(Integer xscosu) {
        this.xscosu = xscosu;
    }

    public String getXscopr() {
        return xscopr;
    }

    public void setXscopr(String xscopr) {
        this.xscopr = xscopr;
    }

    public String getXscodo() {
        return xscodo;
    }

    public void setXscodo(String xscodo) {
        this.xscodo = xscodo;
    }
}