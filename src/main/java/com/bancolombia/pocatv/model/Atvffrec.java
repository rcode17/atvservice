package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import java.util.Date;

@Data
@Entity
@Table(name = "atvffrec")
public class Atvffrec {

    @Id
    @Column(name = "rcfere", nullable = false)
    private String rcfere; // Definido como bpchar(8)

    @Column(name = "rcfear", nullable = false)
    private Date rcfear; // Definido como date

    @Column(name = "rcsuc", nullable = false)
    private String rcsuc; // Definido como bpchar(20)

    @Column(name = "rccdsu", nullable = false)
    private Integer rccdsu; // Definido como numeric(5)

    @Column(name = "rccdsuf", nullable = false)
    private Integer rccdsuf; // Definido como numeric(5)

    @Column(name = "rcprcu", nullable = false)
    private String rcprcu; // Definido como bpchar(20)

    @Column(name = "rccedcn", nullable = false)
    private String rccedcn; // Definido como bpchar(10)

    @Column(name = "rcpear", nullable = false)
    private String rcpear; // Definido como bpchar(20)

    @Column(name = "rccedan", nullable = false)
    private String rccedan; // Definido como bpchar(10)

    @Column(name = "rcsubg", nullable = false)
    private String rcsubg; // Definido como bpchar(20)

    @Column(name = "rccesbn", nullable = false)
    private String rccesbn; // Definido como bpchar(10)

    @Column(name = "rccopr", nullable = false)
    private String rccopr; // Definido como bpchar(2)

    @Column(name = "rccodo", nullable = false)
    private String rccodo; // Definido como bpchar(3)

    @Column(name = "rcsfar", nullable = false)
    private BigDecimal rcsfar; // Definido como numeric(15, 2)

    @Column(name = "rcdif", nullable = false)
    private BigDecimal rcdif; // Definido como numeric(15, 2)

    @Column(name = "rcres", nullable = false)
    private String rcres; // Definido como bpchar(2)

    @Column(name = "rcobsn", nullable = false)
    private String rcobsn; // Definido como bpchar(250)

    @Column(name = "rcobsno", nullable = false)
    private String rcobsno; // Definido como bpchar(250)

    @Column(name = "rcsfeb", nullable = false)
    private BigDecimal rcsfeb; // Definido como numeric(17, 2)

    @Column(name = "rcdeb", nullable = false)
    private BigDecimal rcdeb; // Definido como numeric(17, 2)

    @Column(name = "rcsfev", nullable = false)
    private BigDecimal rcsfev; // Definido como numeric(17, 2)

    @Column(name = "rcdev", nullable = false)
    private BigDecimal rcdev; // Definido como numeric(17, 2)

    @Column(name = "rcsfet", nullable = false)
    private BigDecimal rcsfet; // Definido como numeric(17, 2)

    @Column(name = "rcsts", nullable = false)
    private String rcsts; // Definido como bpchar(1)

    @Column(name = "rchora", nullable = false)
    private Integer rchora; // Definido como numeric(6)

    @Column(name = "rctrans", nullable = false)
    private String rctrans; // Definido como bpchar(4)

    @Column(name = "rcrain1", nullable = false)
    private String rcrain1; // Definido como bpchar(16)

    @Column(name = "rcrafn1", nullable = false)
    private String rcrafn1; // Definido como bpchar(16)

    @Column(name = "rccocu1", nullable = false)
    private String rccocu1; // Definido como bpchar(16)

    @Column(name = "rcrain2", nullable = false)
    private String rcrain2; // Definido como bpchar(16)

    @Column(name = "rcrafn2", nullable = false)
    private String rcrafn2; // Definido como bpchar(16)

    @Column(name = "rccocu2", nullable = false)
    private String rccocu2; // Definido como bpchar(16)

    @Column(name = "rcrain3", nullable = false)
    private String rcrain3; // Definido como bpchar(16)

    @Column(name = "rcrafn3", nullable = false)
    private String rcrafn3; // Definido como bpchar(16)

    @Column(name = "rccocu3", nullable = false)
    private String rccocu3; // Definido como bpchar(16)

    @Column(name = "rcrain4", nullable = false)
    private String rcrain4; // Definido como bpchar(16)

    @Column(name = "rcrafn4", nullable = false)
    private String rcrafn4; // Definido como bpchar(16)

    @Column(name = "rccocu4", nullable = false)
    private String rccocu4; // Definido como bpchar(16)

    @Column(name = "rcrain5", nullable = false)
    private String rcrain5; // Definido como bpchar(16)

    @Column(name = "rcrafn5", nullable = false)
    private String rcrafn5; // Definido como bpchar(16)

    @Column(name = "rccocu5", nullable = false)
    private String rccocu5; // Definido como bpchar(16)

    @Column(name = "rcrain6", nullable = false)
    private String rcrain6; // Definido como bpchar(16)

    @Column(name = "rcrafn6", nullable = false)
    private String rcrafn6; // Definido como bpchar(16)

    @Column(name = "rccocu6", nullable = false)
    private String rccocu6; // Definido como bpchar(16)

    @Column(name = "rcrain7", nullable = false)
    private String rcrain7; // Definido como bpchar(16)

    @Column(name = "rcrafn7", nullable = false)
    private String rcrafn7; // Definido como bpchar(16)

    @Column(name = "rccocu7", nullable = false)
    private String rccocu7; // Definido como bpchar(16)

    @Column(name = "rcrain8", nullable = false)
    private String rcrain8; // Definido como bpchar(16)

    @Column(name = "rcrafn8", nullable = false)
    private String rcrafn8; // Definido como bpchar(16)

    @Column(name = "rccocu8", nullable = false)
    private String rccocu8; // Definido como bpchar(16)

    @Column(name = "rcrain9", nullable = false)
    private String rcrain9; // Definido como bpchar(16)

    @Column(name = "rcrafn9", nullable = false)
    private String rcrafn9; // Definido como bpchar(16)

    @Column(name = "rccocu9", nullable = false)
    private String rccocu9; // Definido como bpchar(16)

    @Column(name = "rcrain10", nullable = false)
    private String rcrain10; // Definido como bpchar(16)

    @Column(name = "rcrafn10", nullable = false)
    private String rcrafn10; // Definido como bpchar(16)

    @Column(name = "rccocu10", nullable = false)
    private String rccocu10; // Definido como bpchar(16)

    @Column(name = "rcrain11", nullable = false)
    private String rcrain11; // Definido como bpchar(16)

    @Column(name = "rcrafn11", nullable = false)
    private String rcrafn11; // Definido como bpchar(16)

    @Column(name = "rccocu11", nullable = false)
    private String rccocu11; // Definido como bpchar(16)

    @Column(name = "rcrain12", nullable = false)
    private String rcrain12; // Definido como bpchar(16)

    @Column(name = "rcrafn12", nullable = false)
    private String rcrafn12; // Definido como bpchar(16)

    @Column(name = "rccocu12", nullable = false)
    private String rccocu12; // Definido como bpchar(16)

    @Column(name = "rcrain13", nullable = false)
    private String rcrain13; // Definido como bpchar(16)

    @Column(name = "rcrafn13", nullable = false)
    private String rcrafn13; // Definido como bpchar(16)

    @Column(name = "rccocu13", nullable = false)
    private String rccocu13; // Definido como bpchar(16)

    @Column(name = "rcrain14", nullable = false)
    private String rcrain14; // Definido como bpchar(16)

    @Column(name = "rcrafn14", nullable = false)
    private String rcrafn14; // Definido como bpchar(16)

    @Column(name = "rccocu14", nullable = false)
    private String rccocu14; // Definido como bpchar(16)

    @Column(name = "rcrech", nullable = false)
    private String rcrech; // Definido como bpchar(4)

    // Getters y Setters
    public String getRcfere() {
        return rcfere;
    }

    public void setRcfere(String rcfere) {
        this.rcfere = rcfere;
    }

    public Date getRcfear() {
        return rcfear;
    }

    public void setRcfear(Date rcfear) {
        this.rcfear = rcfear;
    }

    public String getRcsuc() {
        return rcsuc;
    }

    public void setRcsuc(String rcsuc) {
        this.rcsuc = rcsuc;
    }

    public Integer getRccdsu() {
        return rccdsu;
    }

    public void setRccdsu(Integer rccdsu) {
        this.rccdsu = rccdsu;
    }

    public Integer getRccdsuf() {
        return rccdsuf;
    }

    public void setRccdsuf(Integer rccdsuf) {
        this.rccdsuf = rccdsuf;
    }

    public String getRcprcu() {
        return rcprcu;
    }

    public void setRcprcu(String rcprcu) {
        this.rcprcu = rcprcu;
    }

    public String getRccedcn() {
        return rccedcn;
    }

    public void setRccedcn(String rccedcn) {
        this.rccedcn = rccedcn;
    }

    public String getRcpear() {
        return rcpear;
    }

    public void setRcpear(String rcpear) {
        this.rcpear = rcpear;
    }

    public String getRccedan() {
        return rccedan;
    }

    public void setRccedan(String rccedan) {
        this.rccedan = rccedan;
    }

    public String getRcsubg() {
        return rcsubg;
    }

    public void setRcsubg(String rcsubg) {
        this.rcsubg = rcsubg;
    }

    public String getRccesbn() {
        return rccesbn;
    }

    public void setRccesbn(String rccesbn) {
        this.rccesbn = rccesbn;
    }

    public String getRccopr() {
        return rccopr;
    }

    public void setRccopr(String rccopr) {
        this.rccopr = rccopr;
    }

    public String getRccodo() {
        return rccodo;
    }

    public void setRccodo(String rccodo) {
        this.rccodo = rccodo;
    }

    public BigDecimal getRcsfar() {
        return rcsfar;
    }

    public void setRcsfar(BigDecimal rcsfar) {
        this.rcsfar = rcsfar;
    }

    public BigDecimal getRcdif() {
        return rcdif;
    }

    public void setRcdif(BigDecimal rcdif) {
        this.rcdif = rcdif;
    }

    public String getRcres() {
        return rcres;
    }

    public void setRcres(String rcres) {
        this.rcres = rcres;
    }

    public String getRcobsn() {
        return rcobsn;
    }

    public void setRcobsn(String rcobsn) {
        this.rcobsn = rcobsn;
    }

    public String getRcobsno() {
        return rcobsno;
    }

    public void setRcobsno(String rcobsno) {
        this.rcobsno = rcobsno;
    }

    public BigDecimal getRcsfeb() {
        return rcsfeb;
    }

    public void setRcsfeb(BigDecimal rcsfeb) {
        this.rcsfeb = rcsfeb;
    }

    public BigDecimal getRcdeb() {
        return rcdeb;
    }

    public void setRcdeb(BigDecimal rcdeb) {
        this.rcdeb = rcdeb;
    }

    public BigDecimal getRcsfev() {
        return rcsfev;
    }

    public void setRcsfev(BigDecimal rcsfev) {
        this.rcsfev = rcsfev;
    }

    public BigDecimal getRcdev() {
        return rcdev;
    }

    public void setRcdev(BigDecimal rcdev) {
        this.rcdev = rcdev;
    }

    public BigDecimal getRcsfet() {
        return rcsfet;
    }

    public void setRcsfet(BigDecimal rcsfet) {
        this.rcsfet = rcsfet;
    }

    public String getRcsts() {
        return rcsts;
    }

    public void setRcsts(String rcsts) {
        this.rcsts = rcsts;
    }

    public Integer getRchora() {
        return rchora;
    }

    public void setRchora(Integer rchora) {
        this.rchora = rchora;
    }

    public String getRctrans() {
        return rctrans;
    }

    public void setRctrans(String rctrans) {
        this.rctrans = rctrans;
    }

    public String getRcrain1() {
        return rcrain1;
    }

    public void setRcrain1(String rcrain1) {
        this.rcrain1 = rcrain1;
    }

    public String getRcrafn1() {
        return rcrafn1;
    }

    public void setRcrafn1(String rcrafn1) {
        this.rcrafn1 = rcrafn1;
    }

    public String getRccocu1() {
        return rccocu1;
    }

    public void setRccocu1(String rccocu1) {
        this.rccocu1 = rccocu1;
    }

    public String getRcrain2() {
        return rcrain2;
    }

    public void setRcrain2(String rcrain2) {
        this.rcrain2 = rcrain2;
    }

    public String getRcrafn2() {
        return rcrafn2;
    }

    public void setRcrafn2(String rcrafn2) {
        this.rcrafn2 = rcrafn2;
    }

    public String getRccocu2() {
        return rccocu2;
    }

    public void setRccocu2(String rccocu2) {
        this.rccocu2 = rccocu2;
    }

    public String getRcrain3() {
        return rcrain3;
    }

    public void setRcrain3(String rcrain3) {
        this.rcrain3 = rcrain3;
    }

    public String getRcrafn3() {
        return rcrafn3;
    }

    public void setRcrafn3(String rcrafn3) {
        this.rcrafn3 = rcrafn3;
    }

    public String getRccocu3() {
        return rccocu3;
    }

    public void setRccocu3(String rccocu3) {
        this.rccocu3 = rccocu3;
    }

    public String getRcrain4() {
        return rcrain4;
    }

    public void setRcrain4(String rcrain4) {
        this.rcrain4 = rcrain4;
    }

    public String getRcrafn4() {
        return rcrafn4;
    }

    public void setRcrafn4(String rcrafn4) {
        this.rcrafn4 = rcrafn4;
    }

    public String getRccocu4() {
        return rccocu4;
    }

    public void setRccocu4(String rccocu4) {
        this.rccocu4 = rccocu4;
    }

    public String getRcrain5() {
        return rcrain5;
    }

    public void setRcrain5(String rcrain5) {
        this.rcrain5 = rcrain5;
    }

    public String getRcrafn5() {
        return rcrafn5;
    }

    public void setRcrafn5(String rcrafn5) {
        this.rcrafn5 = rcrafn5;
    }

    public String getRccocu5() {
        return rccocu5;
    }

    public void setRccocu5(String rccocu5) {
        this.rccocu5 = rccocu5;
    }

    public String getRcrain6() {
        return rcrain6;
    }

    public void setRcrain6(String rcrain6) {
        this.rcrain6 = rcrain6;
    }

    public String getRcrafn6() {
        return rcrafn6;
    }

    public void setRcrafn6(String rcrafn6) {
        this.rcrafn6 = rcrafn6;
    }

    public String getRccocu6() {
        return rccocu6;
    }

    public void setRccocu6(String rccocu6) {
        this.rccocu6 = rccocu6;
    }

    public String getRcrain7() {
        return rcrain7;
    }

    public void setRcrain7(String rcrain7) {
        this.rcrain7 = rcrain7;
    }

    public String getRcrafn7() {
        return rcrafn7;
    }

    public void setRcrafn7(String rcrafn7) {
        this.rcrafn7 = rcrafn7;
    }

    public String getRccocu7() {
        return rccocu7;
    }

    public void setRccocu7(String rccocu7) {
        this.rccocu7 = rccocu7;
    }

    public String getRcrain8() {
        return rcrain8;
    }

    public void setRcrain8(String rcrain8) {
        this.rcrain8 = rcrain8;
    }

    public String getRcrafn8() {
        return rcrafn8;
    }

    public void setRcrafn8(String rcrafn8) {
        this.rcrafn8 = rcrafn8;
    }

    public String getRccocu8() {
        return rccocu8;
    }

    public void setRccocu8(String rccocu8) {
        this.rccocu8 = rccocu8;
    }

    public String getRcrain9() {
        return rcrain9;
    }

    public void setRcrain9(String rcrain9) {
        this.rcrain9 = rcrain9;
    }

    public String getRcrafn9() {
        return rcrafn9;
    }

    public void setRcrafn9(String rcrafn9) {
        this.rcrafn9 = rcrafn9;
    }

    public String getRccocu9() {
        return rccocu9;
    }

    public void setRccocu9(String rccocu9) {
        this.rccocu9 = rccocu9;
    }

    public String getRcrain10() {
        return rcrain10;
    }

    public void setRcrain10(String rcrain10) {
        this.rcrain10 = rcrain10;
    }

    public String getRcrafn10() {
        return rcrafn10;
    }

    public void setRcrafn10(String rcrafn10) {
        this.rcrafn10 = rcrafn10;
    }

    public String getRccocu10() {
        return rccocu10;
    }

    public void setRccocu10(String rccocu10) {
        this.rccocu10 = rccocu10;
    }

    public String getRcrain11() {
        return rcrain11;
    }

    public void setRcrain11(String rcrain11) {
        this.rcrain11 = rcrain11;
    }

    public String getRcrafn11() {
        return rcrafn11;
    }

    public void setRcrafn11(String rcrafn11) {
        this.rcrafn11 = rcrafn11;
    }

    public String getRccocu11() {
        return rccocu11;
    }

    public void setRccocu11(String rccocu11) {
        this.rccocu11 = rccocu11;
    }

    public String getRcrain12() {
        return rcrain12;
    }

    public void setRcrain12(String rcrain12) {
        this.rcrain12 = rcrain12;
    }

    public String getRcrafn12() {
        return rcrafn12;
    }

    public void setRcrafn12(String rcrafn12) {
        this.rcrafn12 = rcrafn12;
    }

    public String getRccocu12() {
        return rccocu12;
    }

    public void setRccocu12(String rccocu12) {
        this.rccocu12 = rccocu12;
    }

    public String getRcrain13() {
        return rcrain13;
    }

    public void setRcrain13(String rcrain13) {
        this.rcrain13 = rcrain13;
    }

    public String getRcrafn13() {
        return rcrafn13;
    }

    public void setRcrafn13(String rcrafn13) {
        this.rcrafn13 = rcrafn13;
    }

    public String getRccocu13() {
        return rccocu13;
    }

    public void setRccocu13(String rccocu13) {
        this.rccocu13 = rccocu13;
    }

    public String getRcrain14() {
        return rcrain14;
    }

    public void setRcrain14(String rcrain14) {
        this.rcrain14 = rcrain14;
    }

    public String getRcrafn14() {
        return rcrafn14;
    }

    public void setRcrafn14(String rcrafn14) {
        this.rcrafn14 = rcrafn14;
    }

    public String getRccocu14() {
        return rccocu14;
    }

    public void setRccocu14(String rccocu14) {
        this.rccocu14 = rccocu14;
    }

    public String getRcrech() {
        return rcrech;
    }

    public void setRcrech(String rcrech) {
        this.rcrech = rcrech;
    }
}