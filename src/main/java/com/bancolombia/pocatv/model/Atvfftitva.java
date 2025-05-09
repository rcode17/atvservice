package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ATVFFTITVA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvfftitvaId.class)
public class Atvfftitva {
	
	@Id
	@Column(name = "RQFEAR")
    private Integer rqfear;
    
	@Id
    @Column(name = "RQCDSU")
    private Integer rqcdsu;
	
	@Id
    @Column(name = "RQCOPR", length = 2)
    private String rqcopr;
    
	@Id
    @Column(name = "RQCODO", length = 3)
    private String rqcodo;
    
    @Column(name = "RQSUC", length = 20)
    private String rqsuc;
    
    @Column(name = "RQDIF", precision = 15, scale = 2)
    private BigDecimal rqdif;
    
    @Column(name = "RQRES", length = 2)
    private String rqres;
    
    @Column(name = "RQSFAR", precision = 15, scale = 2)
    private BigDecimal rqsfar;
    
    @Column(name = "RQSFET", precision = 15, scale = 2)
    private BigDecimal rqsfet;
    
    @Column(name = "RQOBS", length = 50)
    private String rqobs;
    
    @Column(name = "RQOBSO", length = 50)
    private String rqobso;
    
    @Column(name = "RQPRCU", length = 20)
    private String rqprcu;
    
    @Column(name = "RQCEDCN", length = 10)
    private String rqcedcn;
    
    @Column(name = "RQPEAR", length = 20)
    private String rqpear;
    
    @Column(name = "RQCEDAN", length = 10)
    private String rqcedan;
    
    @Column(name = "RQSUBG", length = 20)
    private String rqsubg;
    
    @Column(name = "RQCESBN", length = 10)
    private String rqcesbn;
    
    @Column(name = "RQRAIN1", length = 16)
    private String rqrain1;
}