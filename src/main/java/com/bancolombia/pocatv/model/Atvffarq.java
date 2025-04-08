package com.bancolombia.pocatv.model;




import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffarq")
@IdClass(AtvffarqId.class)
public class Atvffarq {
	
	@Id
    @Column(name = "aqcdsu", nullable = false)
    private Integer aqcdsu;
    
    @Id
    @Column(name = "aqcopr", nullable = false, length = 2)
    private String aqcopr;
    
    @Id
    @Column(name = "aqcodo", nullable = false, length = 3)
    private String aqcodo;
    
    @Id
    @Column(name = "aqfear", nullable = false)
    private String aqfear;
    
    // Campos no incluidos en la clave primaria
    @Column(name = "aqsuc", nullable = false, length = 20)
    private String aqsuc;

    @Column(name = "aqcdsuf", nullable = false)
    private Integer aqcdsuf;

    @Column(name = "aqprcu", nullable = false, length = 20)
    private String aqprcu;

    @Column(name = "aqcedcn", nullable = false, length = 10)
    private String aqcedcn;

    @Column(name = "aqpear", nullable = false, length = 20)
    private String aqpear;

    @Column(name = "aqcedan", nullable = false, length = 10)
    private String aqcedan;

    @Column(name = "aqsubg", nullable = false, length = 20)
    private String aqsubg;

    @Column(name = "aqcesbn", nullable = false, length = 10)
    private String aqcesbn;

    @Column(name = "aqsfar", nullable = false, precision = 15, scale = 2)
    private BigDecimal aqsfar;

    @Column(name = "aqdif", nullable = false, precision = 15, scale = 2)
    private BigDecimal aqdif;

    @Column(name = "aqres", nullable = false, length = 2)
    private String aqres;

    @Column(name = "aqobsn", nullable = false, length = 250)
    private String aqobsn;

    @Column(name = "aqobsno", nullable = false, length = 250)
    private String aqobsno;

    @Column(name = "aqsfeb", nullable = false, precision = 17, scale = 2)
    private BigDecimal aqsfeb;

    @Column(name = "aqdeb", nullable = false, precision = 17, scale = 2)
    private BigDecimal aqdeb;

    @Column(name = "aqsfev", nullable = false, precision = 17, scale = 2)
    private BigDecimal aqsfev;

    @Column(name = "aqdev", nullable = false, precision = 17, scale = 2)
    private BigDecimal aqdev;

    @Column(name = "aqsfet", nullable = false, precision = 17, scale = 2)
    private BigDecimal aqsfet;

    @Column(name = "aqsts", nullable = false, length = 1)
    private String aqsts;

    @Column(name = "aqgest", nullable = false, length = 1)
    private String aqgest;

    @Column(name = "aqjust", nullable = false, length = 1)
    private String aqjust;

    @Column(name = "aqhora", nullable = false)
    private Integer aqhora;

    @Column(name = "aqtrans", nullable = false, length = 4)
    private String aqtrans;
    
    // Campos de rangos y códigos de cuenta
    @Column(name = "aqrain1", nullable = false, length = 16)
    private String aqrain1;

    @Column(name = "aqrafn1", nullable = false, length = 16)
    private String aqrafn1;
    
    @Column(name = "aqcocu1", nullable = false, length = 16)
    private String aqcocu1;

    @Column(name = "aqrain2", nullable = false, length = 16)
    private String aqrain2;

    @Column(name = "aqrafn2", nullable = false, length = 16)
    private String aqrafn2;

    @Column(name = "aqcocu2", nullable = false, length = 16)
    private String aqcocu2;

    @Column(name = "aqrain3", nullable = false, length = 16)
    private String aqrain3;

    @Column(name = "aqrafn3", nullable = false, length = 16)
    private String aqrafn3;

    @Column(name = "aqcocu3", nullable = false, length = 16)
    private String aqcocu3;

    @Column(name = "aqrain4", nullable = false, length = 16)
    private String aqrain4;

    @Column(name = "aqrafn4", nullable = false, length = 16)
    private String aqrafn4;

    @Column(name = "aqcocu4", nullable = false, length = 16)
    private String aqcocu4;

    @Column(name = "aqrain5", nullable = false, length = 16)
    private String aqrain5;

    @Column(name = "aqrafn5", nullable = false, length = 16)
    private String aqrafn5;

    @Column(name = "aqcocu5", nullable = false, length = 16)
    private String aqcocu5;

    @Column(name = "aqrain6", nullable = false, length = 16)
    private String aqrain6;

    @Column(name = "aqrafn6", nullable = false, length = 16)
    private String aqrafn6;

    @Column(name = "aqcocu6", nullable = false, length = 16)
    private String aqcocu6;

    @Column(name = "aqrain7", nullable = false, length = 16)
    private String aqrain7;

    @Column(name = "aqrafn7", nullable = false, length = 16)
    private String aqrafn7;

    @Column(name = "aqcocu7", nullable = false, length = 16)
    private String aqcocu7;

    @Column(name = "aqrain8", nullable = false, length = 16)
    private String aqrain8;

    @Column(name = "aqrafn8", nullable = false, length = 16)
    private String aqrafn8;

    @Column(name = "aqcocu8", nullable = false, length = 16)
    private String aqcocu8;

    @Column(name = "aqrain9", nullable = false, length = 16)
    private String aqrain9;

    @Column(name = "aqrafn9", nullable = false, length = 16)
    private String aqrafn9;

    @Column(name = "aqcocu9", nullable = false, length = 16)
    private String aqcocu9;

    @Column(name = "aqrain10", nullable = false, length = 16)
    private String aqrain10;

    @Column(name = "aqrafn10", nullable = false, length = 16)
    private String aqrafn10;

    @Column(name = "aqcocu10", nullable = false, length = 16)
    private String aqcocu10;

    @Column(name = "aqrain11", nullable = false, length = 16)
    private String aqrain11;

    @Column(name = "aqrafn11", nullable = false, length = 16)
    private String aqrafn11;

    @Column(name = "aqcocu11", nullable = false, length = 16)
    private String aqcocu11;

    @Column(name = "aqrain12", nullable = false, length = 16)
    private String aqrain12;

    @Column(name = "aqrafn12", nullable = false, length = 16)
    private String aqrafn12;

    @Column(name = "aqcocu12", nullable = false, length = 16)
    private String aqcocu12;

    @Column(name = "aqrain13", nullable = false, length = 16)
    private String aqrain13;

    @Column(name = "aqrafn13", nullable = false, length = 16)
    private String aqrafn13;

    @Column(name = "aqcocu13", nullable = false, length = 16)
    private String aqcocu13;

    @Column(name = "aqrain14", nullable = false, length = 16)
    private String aqrain14;

    @Column(name = "aqrafn14", nullable = false, length = 16)
    private String aqrafn14;

    @Column(name = "aqcocu14", nullable = false, length = 16)
    private String aqcocu14;

    @Column(name = "aqcusu1", nullable = false, length = 15)
    private String aqcusu1;

    @Column(name = "aqcusu2", nullable = false, length = 15)
    private String aqcusu2;

    @Column(name = "aqcusu3", nullable = false, length = 15)
    private String aqcusu3;

    @Column(name = "aqcusu4", nullable = false, precision = 15)
    private BigDecimal aqcusu4;

    @Column(name = "aqcusu5", nullable = false, precision = 15)
    private BigDecimal aqcusu5;
    
    
    public boolean hasDataInAqrain() {
        return (aqrain1 != null && !aqrain1.trim().isEmpty()) ||
               (aqrain2 != null && !aqrain2.trim().isEmpty()) ||
               (aqrain3 != null && !aqrain3.trim().isEmpty()) ||
               (aqrain4 != null && !aqrain4.trim().isEmpty()) ||
               (aqrain5 != null && !aqrain5.trim().isEmpty()) ||
               (aqrain6 != null && !aqrain6.trim().isEmpty()) ||
               (aqrain7 != null && !aqrain7.trim().isEmpty()) ||
               (aqrain8 != null && !aqrain8.trim().isEmpty()) ||
               (aqrain9 != null && !aqrain9.trim().isEmpty()) ||
               (aqrain10 != null && !aqrain10.trim().isEmpty()) ||
               (aqrain11 != null && !aqrain11.trim().isEmpty()) ||
               (aqrain12 != null && !aqrain12.trim().isEmpty()) ||
               (aqrain13 != null && !aqrain13.trim().isEmpty()) ||
               (aqrain14 != null && !aqrain14.trim().isEmpty());
    }

}
