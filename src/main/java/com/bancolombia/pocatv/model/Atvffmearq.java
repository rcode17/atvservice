package com.bancolombia.pocatv.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ATVFFMEARQ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvffmearqId.class)
public class Atvffmearq {
    
	@Id
    @Column(name = "RQFEAR")
    private Integer rqfear;
	
	@Id
    @Column(name = "RQCDSU")
    private Integer rqcdsu;
	
	@Id
    @Column(name = "RQCOPR", length = 5)
    private String rqcopr;
    
    @Id
    @Column(name = "RQCODO", length = 5)
    private String rqcodo;
    
    @Column(name = "RQSUC", length = 20)
    private String rqsuc;
    
    @Column(name = "RQCDSUF")
    private Integer rqcdsuf;
    
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
    
    @Column(name = "RQSFAR")
    private Long rqsfar;
    
    @Column(name = "RQSIG", length = 1)
    private String rqsig;
    
    @Column(name = "RQDIF")
    private Long rqdif;
    
    @Column(name = "RQRES", length = 2)
    private String rqres;
    
    @Column(name = "RQOBS", length = 250)
    private String rqobs;
    
    @Column(name = "RQOBSO", length = 250)
    private String rqobso;
    
    @Column(name = "RQRAIN1", length = 16)
    private String rqrain1;
    
    @Column(name = "RQRAFN1", length = 16)
    private String rqrafn1;
    
    @Column(name = "RQCOCU1", length = 16)
    private String rqcocu1;
    
    @Column(name = "RQRAIN2", length = 16)
    private String rqrain2;
    
    @Column(name = "RQRAFN2", length = 16)
    private String rqrafn2;
    
    @Column(name = "RQCOCU2", length = 16)
    private String rqcocu2;
    
    @Column(name = "RQRAIN3", length = 16)
    private String rqrain3;
    
    @Column(name = "RQRAFN3", length = 16)
    private String rqrafn3;
    
    @Column(name = "RQCOCU3", length = 16)
    private String rqcocu3;
    
    @Column(name = "RQRAIN4", length = 16)
    private String rqrain4;
    
    @Column(name = "RQRAFN4", length = 16)
    private String rqrafn4;
    
    @Column(name = "RQCOCU4", length = 16)
    private String rqcocu4;
    
    @Column(name = "RQRAIN5", length = 16)
    private String rqrain5;
    
    @Column(name = "RQRAFN5", length = 16)
    private String rqrafn5;
    
    @Column(name = "RQCOCU5", length = 16)
    private String rqcocu5;
    
    @Column(name = "RQRAIN6", length = 16)
    private String rqrain6;
    
    @Column(name = "RQRAFN6", length = 16)
    private String rqrafn6;
    
    @Column(name = "RQCOCU6", length = 16)
    private String rqcocu6;
    
    @Column(name = "RQRAIN7", length = 16)
    private String rqrain7;
    
    @Column(name = "RQRAFN7", length = 16)
    private String rqrafn7;
    
    @Column(name = "RQCOCU7", length = 16)
    private String rqcocu7;
    
    @Column(name = "RQRAIN8", length = 16)
    private String rqrain8;
    
    @Column(name = "RQRAFN8", length = 16)
    private String rqrafn8;
    
    @Column(name = "RQCOCU8", length = 16)
    private String rqcocu8;
    
    @Column(name = "RQRAIN9", length = 16)
    private String rqrain9;
    
    @Column(name = "RQRAFN9", length = 16)
    private String rqrafn9;
    
    @Column(name = "RQCOCU9", length = 16)
    private String rqcocu9;
    
    @Column(name = "RQRAIN10", length = 16)
    private String rqrain10;
    
    @Column(name = "RQRAFN10", length = 16)
    private String rqrafn10;
    
    @Column(name = "RQCOCU10", length = 16)
    private String rqcocu10;
    
    @Column(name = "RQRAIN11", length = 16)
    private String rqrain11;
    
    @Column(name = "RQRAFN11", length = 16)
    private String rqrafn11;
    
    @Column(name = "RQCOCU11", length = 16)
    private String rqcocu11;
    
    @Column(name = "RQRAIN12", length = 16)
    private String rqrain12;
    
    @Column(name = "RQRAFN12", length = 16)
    private String rqrafn12;
    
    @Column(name = "RQCOCU12", length = 16)
    private String rqcocu12;
    
    @Column(name = "RQRAIN13", length = 16)
    private String rqrain13;
    
    @Column(name = "RQRAFN13", length = 16)
    private String rqrafn13;
    
    @Column(name = "RQCOCU13", length = 16)
    private String rqcocu13;
    
    @Column(name = "RQUSU1", length = 12)
    private String rqusu1;
    
    @Column(name = "RQUSU2", length = 20)
    private String rqusu2;
    
    @Column(name = "RQUSU3", length = 20)
    private String rqusu3;
    
    @Column(name = "RQUSU4", precision = 15, scale = 2)
    private BigDecimal rqusu4;
    
    @Column(name = "RQUSU5", precision = 15, scale = 2)
    private BigDecimal rqusu5;
}
