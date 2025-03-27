package com.bancolombia.pocatv.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "XBKNAM")
public class Xbknam {

    @Id
    @Column(name = "XNNMKY", nullable = false)
    private BigDecimal xnnmky;

    @Column(name = "XNCLCD", nullable = false)
    private String xnclcd;

    @Column(name = "XNNAME", nullable = false)
    private String xnname;
    
}
