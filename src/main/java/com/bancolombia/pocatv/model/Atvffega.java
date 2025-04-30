package com.bancolombia.pocatv.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffega")
@IdClass(AtvffegaId.class)
public class Atvffega {
    
    @Id
    @Column(name = "egmesin", nullable = false)
    private Integer egmesin;
    
    @Id
    @Column(name = "eganoin", nullable = false)
    private Integer eganoin;
    
    @Id
    @Column(name = "egsucursal", nullable = false)
    private Integer egsucursal;
    
    @Column(name = "egnombre")
    private String egnombre;
    
    @Column(name = "egcump11")
    private Integer egcump11;
    
    @Column(name = "egcump22")
    private Integer egcump22;
    
    @Column(name = "eginf11")
    private Integer eginf11;
    
    @Column(name = "eginf22")
    private Integer eginf22;
    
    @Column(name = "egvarc")
    private String egvarc;
    
    @Column(name = "egvari")
    private String egvari;
    
    @Column(name = "egantc")
    private String egantc;
    
    @Column(name = "egactc")
    private String egactc;
    
    @Column(name = "eganti")
    private String eganti;
    
    @Column(name = "egacti")
    private String egacti;
    
    @Column(name = "eganonc")
    private Integer eganonc;
    
    @Column(name = "eganoni")
    private Integer eganoni;
    
    @Column(name = "eganocc")
    private Integer eganocc;
    
    @Column(name = "eganoci")
    private Integer eganoci;
    
    @Column(name = "egind07")
    private String egind07;

    // Getters y Setters
    public Integer getEgmesin() {
        return egmesin;
    }

    public void setEgmesin(Integer egmesin) {
        this.egmesin = egmesin;
    }

    public Integer getEganoin() {
        return eganoin;
    }

    public void setEganoin(Integer eganoin) {
        this.eganoin = eganoin;
    }

    public Integer getEgsucursal() {
        return egsucursal;
    }

    public void setEgsucursal(Integer egsucursal) {
        this.egsucursal = egsucursal;
    }

    public String getEgnombre() {
        return egnombre;
    }

    public void setEgnombre(String egnombre) {
        this.egnombre = egnombre;
    }

    public Integer getEgcump11() {
        return egcump11;
    }

    public void setEgcump11(Integer egcump11) {
        this.egcump11 = egcump11;
    }

    public Integer getEgcump22() {
        return egcump22;
    }

    public void setEgcump22(Integer egcump22) {
        this.egcump22 = egcump22;
    }

    public Integer getEginf11() {
        return eginf11;
    }

    public void setEginf11(Integer eginf11) {
        this.eginf11 = eginf11;
    }

    public Integer getEginf22() {
        return eginf22;
    }

    public void setEginf22(Integer eginf22) {
        this.eginf22 = eginf22;
    }

    public String getEgvarc() {
        return egvarc;
    }

    public void setEgvarc(String egvarc) {
        this.egvarc = egvarc;
    }

    public String getEgvari() {
        return egvari;
    }

    public void setEgvari(String egvari) {
        this.egvari = egvari;
    }

    public String getEgantc() {
        return egantc;
    }

    public void setEgantc(String egantc) {
        this.egantc = egantc;
    }

    public String getEgactc() {
        return egactc;
    }

    public void setEgactc(String egactc) {
        this.egactc = egactc;
    }

    public String getEganti() {
        return eganti;
    }

    public void setEganti(String eganti) {
        this.eganti = eganti;
    }

    public String getEgacti() {
        return egacti;
    }

    public void setEgacti(String egacti) {
        this.egacti = egacti;
    }

    public Integer getEganonc() {
        return eganonc;
    }

    public void setEganonc(Integer eganonc) {
        this.eganonc = eganonc;
    }

    public Integer getEganoni() {
        return eganoni;
    }

    public void setEganoni(Integer eganoni) {
        this.eganoni = eganoni;
    }

    public Integer getEganocc() {
        return eganocc;
    }

    public void setEganocc(Integer eganocc) {
        this.eganocc = eganocc;
    }

    public Integer getEganoci() {
        return eganoci;
    }

    public void setEganoci(Integer eganoci) {
        this.eganoci = eganoci;
    }

    public String getEgind07() {
        return egind07;
    }

    public void setEgind07(String egind07) {
        this.egind07 = egind07;
    }
}
