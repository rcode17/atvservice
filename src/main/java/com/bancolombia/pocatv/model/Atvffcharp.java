package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "atvffcharp")
public class Atvffcharp {

    @Id
    @Column(name = "PLANO", length = 1377)
    private String plano;

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
}