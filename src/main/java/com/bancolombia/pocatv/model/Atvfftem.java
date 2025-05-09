package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "atvfftem")
@IdClass(AtvfftemId.class)
public class Atvfftem {
    @Id
    @Column(name = "tmcdsu")
    private Integer tmcdsu;

    @Id
    @Column(name = "tmcopr")
    private String tmcopr;

    @Id
    @Column(name = "tmcodo")
    private String tmcodo;

    @Id
    @Column(name = "tmfear")
    private LocalDate tmfear;

    @Column(name = "tmsuc", nullable = false)
    private String tmsuc;

    @Column(name = "tmcdsuf", nullable = false)
    private Integer tmcdsuf;

    @Column(name = "tmprcu", nullable = false)
    private String tmprcu;

    @Column(name = "tmcedcn", nullable = false)
    private String tmcedcn;

    @Column(name = "tmpear", nullable = false)
    private String tmpear;

    @Column(name = "tmcedan", nullable = false)
    private String tmcedan;

    @Column(name = "tmsubg", nullable = false)
    private String tmsubg;

    @Column(name = "tmcesbn", nullable = false)
    private String tmcesbn;

    @Column(name = "tmsfar", nullable = false)
    private BigDecimal tmsfar;

    @Column(name = "tmdif", nullable = false)
    private BigDecimal tmdif;

    @Column(name = "tmres", nullable = false)
    private String tmres;

    @Column(name = "tmobs", nullable = false)
    private String tmobs;

    @Column(name = "tmobso", nullable = false)
    private String tmobso;

    @Column(name = "tmrain1", nullable = false)
    private String tmrain1;

    @Column(name = "tmrafn1", nullable = false)
    private String tmrafn1;

    @Column(name = "tmcocu1", nullable = false)
    private String tmcocu1;

    @Column(name = "tmrain2", nullable = false)
    private String tmrain2;

    @Column(name = "tmrafn2", nullable = false)
    private String tmrafn2;

    @Column(name = "tmcocu2", nullable = false)
    private String tmcocu2;

    @Column(name = "tmrain3", nullable = false)
    private String tmrain3;

    @Column(name = "tmrafn3", nullable = false)
    private String tmrafn3;

    @Column(name = "tmcocu3", nullable = false)
    private String tmcocu3;

    @Column(name = "tmrain4", nullable = false)
    private String tmrain4;

    @Column(name = "tmrafn4", nullable = false)
    private String tmrafn4;

    @Column(name = "tmcocu4", nullable = false)
    private String tmcocu4;

    @Column(name = "tmrain5", nullable = false)
    private String tmrain5;

    @Column(name = "tmrafn5", nullable = false)
    private String tmrafn5;

    @Column(name = "tmcocu5", nullable = false)
    private String tmcocu5;

    @Column(name = "tmrain6", nullable = false)
    private String tmrain6;

    @Column(name = "tmrafn6", nullable = false)
    private String tmrafn6;

    @Column(name = "tmcocu6", nullable = false)
    private String tmcocu6;

    @Column(name = "tmrain7", nullable = false)
    private String tmrain7;

    @Column(name = "tmrafn7", nullable = false)
    private String tmrafn7;

    @Column(name = "tmcocu7", nullable = false)
    private String tmcocu7;

    @Column(name = "tmrain8", nullable = false)
    private String tmrain8;

    @Column(name = "tmrafn8", nullable = false)
    private String tmrafn8;

    @Column(name = "tmcocu8", nullable = false)
    private String tmcocu8;

    @Column(name = "tmrain9", nullable = false)
    private String tmrain9;

    @Column(name = "tmrafn9", nullable = false)
    private String tmrafn9;

    @Column(name = "tmcocu9", nullable = false)
    private String tmcocu9;

    @Column(name = "tmrain10", nullable = false)
    private String tmrain10;

    @Column(name = "tmrafn10", nullable = false)
    private String tmrafn10;

    @Column(name = "tmcocu10", nullable = false)
    private String tmcocu10;

    @Column(name = "tmrain11", nullable = false)
    private String tmrain11;

    @Column(name = "tmrafn11", nullable = false)
    private String tmrafn11;

    @Column(name = "tmcocu11", nullable = false)
    private String tmcocu11;

    @Column(name = "tmrain12", nullable = false)
    private String tmrain12;

    @Column(name = "tmrafn12", nullable = false)
    private String tmrafn12;

    @Column(name = "tmcocu12", nullable = false)
    private String tmcocu12;

    @Column(name = "tmrain13", nullable = false)
    private String tmrain13;

    @Column(name = "tmrafn13", nullable = false)
    private String tmrafn13;

    @Column(name = "tmcocu13", nullable = false)
    private String tmcocu13;

    @Column(name = "tmdeb", nullable = false)
    private BigDecimal tmdeb;

    @Column(name = "tmsfev", nullable = false)
    private BigDecimal tmsfev;

    @Column(name = "tmdev", nullable = false)
    private BigDecimal tmdev;

    @Column(name = "tmsfet", nullable = false)
    private BigDecimal tmsfet;

    @Column(name = "tmhora", nullable = false)
    private Integer tmhora;

    @Column(name = "tmtrans", nullable = false)
    private String tmtrans;
    
    @Column(name = "tmsfeb", nullable = false)
    private BigDecimal tmsfeb;

    // Getters y Setters
    public Integer getTmcdsu() {
        return tmcdsu;
    }

    public void setTmcdsu(Integer tmcdsu) {
        this.tmcdsu = tmcdsu;
    }

    public String getTmcopr() {
        return tmcopr;
    }

    public void setTmcopr(String tmcopr) {
        this.tmcopr = tmcopr;
    }

    public String getTmcodo() {
        return tmcodo;
    }

    public void setTmcodo(String tmcodo) {
        this.tmcodo = tmcodo;
    }

    public LocalDate getTmfear() {
        return tmfear;
    }

    public void setTmfear(LocalDate tmfear) {
        this.tmfear = tmfear;
    }

    public String getTmsuc() {
        return tmsuc;
    }

    public void setTmsuc(String tmsuc) {
        this.tmsuc = tmsuc;
    }

    public Integer getTmcdsuf() {
        return tmcdsuf;
    }

    public void setTmcdsuf(Integer tmcdsuf) {
        this.tmcdsuf = tmcdsuf;
    }

    public String getTmprcu() {
        return tmprcu;
    }

    public void setTmprcu(String tmprcu) {
        this.tmprcu = tmprcu;
    }

    public String getTmcedcn() {
        return tmcedcn;
    }

    public void setTmcedcn(String tmcedcn) {
        this.tmcedcn = tmcedcn;
    }

    public String getTmpear() {
        return tmpear;
    }

    public void setTmpear(String tmpear) {
        this.tmpear = tmpear;
    }

    public String getTmcedan() {
        return tmcedan;
    }

    public void setTmcedan(String tmcedan) {
        this.tmcedan = tmcedan;
    }

    public String getTmsubg() {
        return tmsubg;
    }

    public void setTmsubg(String tmsubg) {
        this.tmsubg = tmsubg;
    }

    public String getTmcesbn() {
        return tmcesbn;
    }

    public void setTmcesbn(String tmcesbn) {
        this.tmcesbn = tmcesbn;
    }

    public BigDecimal getTmsfar() {
        return tmsfar;
    }

    public void setTmsfar(BigDecimal tmsfar) {
        this.tmsfar = tmsfar;
    }

    public BigDecimal getTmdif() {
        return tmdif;
    }

    public void setTmdif(BigDecimal tmdif) {
        this.tmdif = tmdif;
    }

    public String getTmres() {
        return tmres;
    }

    public void setTmres(String tmres) {
        this.tmres = tmres;
    }

    public String getTmobs() {
        return tmobs;
    }

    public void setTmobs(String tmobs) {
        this.tmobs = tmobs;
    }

    public String getTmobso() {
        return tmobso;
    }

    public void setTmobso(String tmobso) {
        this.tmobso = tmobso;
    }

    public String getTmrain1() {
        return tmrain1;
    }

    public void setTmrain1(String tmrain1) {
        this.tmrain1 = tmrain1;
    }

    public String getTmrafn1() {
        return tmrafn1;
    }

    public void setTmrafn1(String tmrafn1) {
        this.tmrafn1 = tmrafn1;
    }

    public String getTmcocu1() {
        return tmcocu1;
    }

    public void setTmcocu1(String tmcocu1) {
        this.tmcocu1 = tmcocu1;
    }

    public String getTmrain2() {
        return tmrain2;
    }

    public void setTmrain2(String tmrain2) {
        this.tmrain2 = tmrain2;
    }

    public String getTmrafn2() {
        return tmrafn2;
    }

    public void setTmrafn2(String tmrafn2) {
        this.tmrafn2 = tmrafn2;
    }

    public String getTmcocu2() {
        return tmcocu2;
    }

    public void setTmcocu2(String tmcocu2) {
        this.tmcocu2 = tmcocu2;
    }

    public String getTmrain3() {
        return tmrain3;
    }

    public void setTmrain3(String tmrain3) {
        this.tmrain3 = tmrain3;
    }

    public String getTmrafn3() {
        return tmrafn3;
    }

    public void setTmrafn3(String tmrafn3) {
        this.tmrafn3 = tmrafn3;
    }

    public String getTmcocu3() {
        return tmcocu3;
    }

    public void setTmcocu3(String tmcocu3) {
        this.tmcocu3 = tmcocu3;
    }

    public String getTmrain4() {
        return tmrain4;
    }

    public void setTmrain4(String tmrain4) {
        this.tmrain4 = tmrain4;
    }

    public String getTmrafn4() {
        return tmrafn4;
    }

    public void setTmrafn4(String tmrafn4) {
        this.tmrafn4 = tmrafn4;
    }

    public String getTmcocu4() {
        return tmcocu4;
    }

    public void setTmcocu4(String tmcocu4) {
        this.tmcocu4 = tmcocu4;
    }

    public String getTmrain5() {
        return tmrain5;
    }

    public void setTmrain5(String tmrain5) {
        this.tmrain5 = tmrain5;
    }

    public String getTmrafn5() {
        return tmrafn5;
    }

    public void setTmrafn5(String tmrafn5) {
        this.tmrafn5 = tmrafn5;
    }

    public String getTmcocu5() {
        return tmcocu5;
    }

    public void setTmcocu5(String tmcocu5) {
        this.tmcocu5 = tmcocu5;
    }

    public String getTmrain6() {
        return tmrain6;
    }

    public void setTmrain6(String tmrain6) {
        this.tmrain6 = tmrain6;
    }

    public String getTmrafn6() {
        return tmrafn6;
    }

    public void setTmrafn6(String tmrafn6) {
        this.tmrafn6 = tmrafn6;
    }

    public String getTmcocu6() {
        return tmcocu6;
    }

    public void setTmcocu6(String tmcocu6) {
        this.tmcocu6 = tmcocu6;
    }

    public String getTmrain7() {
        return tmrain7;
    }

    public void setTmrain7(String tmrain7) {
        this.tmrain7 = tmrain7;
    }

    public String getTmrafn7() {
        return tmrafn7;
    }

    public void setTmrafn7(String tmrafn7) {
        this.tmrafn7 = tmrafn7;
    }

    public String getTmcocu7() {
        return tmcocu7;
    }

    public void setTmcocu7(String tmcocu7) {
        this.tmcocu7 = tmcocu7;
    }

    public String getTmrain8() {
        return tmrain8;
    }

    public void setTmrain8(String tmrain8) {
        this.tmrain8 = tmrain8;
    }

    public String getTmrafn8() {
        return tmrafn8;
    }

    public void setTmrafn8(String tmrafn8) {
        this.tmrafn8 = tmrafn8;
    }

    public String getTmcocu8() {
        return tmcocu8;
    }

    public void setTmcocu8(String tmcocu8) {
        this.tmcocu8 = tmcocu8;
    }

    public String getTmrain9() {
        return tmrain9;
    }

    public void setTmrain9(String tmrain9) {
        this.tmrain9 = tmrain9;
    }

    public String getTmrafn9() {
        return tmrafn9;
    }

    public void setTmrafn9(String tmrafn9) {
        this.tmrafn9 = tmrafn9;
    }

    public String getTmcocu9() {
        return tmcocu9;
    }

    public void setTmcocu9(String tmcocu9) {
        this.tmcocu9 = tmcocu9;
    }

    public String getTmrain10() {
        return tmrain10;
    }

    public void setTmrain10(String tmrain10) {
        this.tmrain10 = tmrain10;
    }

    public String getTmrafn10() {
        return tmrafn10;
    }

    public void setTmrafn10(String tmrafn10) {
        this.tmrafn10 = tmrafn10;
    }

    public String getTmcocu10() {
        return tmcocu10;
    }

    public void setTmcocu10(String tmcocu10) {
        this.tmcocu10 = tmcocu10;
    }

    public String getTmrain11() {
        return tmrain11;
    }

    public void setTmrain11(String tmrain11) {
        this.tmrain11 = tmrain11;
    }

    public String getTmrafn11() {
        return tmrafn11;
    }

    public void setTmrafn11(String tmrafn11) {
        this.tmrafn11 = tmrafn11;
    }

    public String getTmcocu11() {
        return tmcocu11;
    }

    public void setTmcocu11(String tmcocu11) {
        this.tmcocu11 = tmcocu11;
    }

    public String getTmrain12() {
        return tmrain12;
    }

    public void setTmrain12(String tmrain12) {
        this.tmrain12 = tmrain12;
    }

    public String getTmrafn12() {
        return tmrafn12;
    }

    public void setTmrafn12(String tmrafn12) {
        this.tmrafn12 = tmrafn12;
    }

    public String getTmcocu12() {
        return tmcocu12;
    }

    public void setTmcocu12(String tmcocu12) {
        this.tmcocu12 = tmcocu12;
    }

    public String getTmrain13() {
        return tmrain13;
    }

    public void setTmrain13(String tmrain13) {
        this.tmrain13 = tmrain13;
    }

    public String getTmrafn13() {
        return tmrafn13;
    }

    public void setTmrafn13(String tmrafn13) {
        this.tmrafn13 = tmrafn13;
    }

    public String getTmcocu13() {
        return tmcocu13;
    }

    public void setTmcocu13(String tmcocu13) {
        this.tmcocu13 = tmcocu13;
    }

    public BigDecimal getTmdeb() {
        return tmdeb;
    }

    public void setTmdeb(BigDecimal tmdeb) {
        this.tmdeb = tmdeb;
    }

    public BigDecimal getTmsfev() {
        return tmsfev;
    }

    public void setTmsfev(BigDecimal tmsfev) {
        this.tmsfev = tmsfev;
    }

    public BigDecimal getTmdev() {
        return tmdev;
    }

    public void setTmdev(BigDecimal tmdev) {
        this.tmdev = tmdev;
    }

    public BigDecimal getTmsfet() {
        return tmsfet;
    }

    public void setTmsfet(BigDecimal tmsfet) {
        this.tmsfet = tmsfet;
    }

    public Integer getTmhora() {
        return tmhora;
    }

    public void setTmhora(Integer tmhora) {
        this.tmhora = tmhora;
    }

    public String getTmtrans() {
        return tmtrans;
    }

    public void setTmtrans(String tmtrans) {
        this.tmtrans = tmtrans;
    }
}