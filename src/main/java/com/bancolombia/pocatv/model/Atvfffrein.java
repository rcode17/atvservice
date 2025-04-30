package com.bancolombia.pocatv.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Atvfffrein")
public class Atvfffrein {

    @Id
    @Column(name = "resp", length = 10)
    private String resp;

    @Column(name = "cod")
    private Integer cod;

    @Column(name = "area", length = 30)
    private String area;

    @Column(name = "prod", length = 2)
    private String prod;

    @Column(name = "doc", length = 3)
    private String doc;

    @Column(name = "cal")
    private Integer cal;

    @Column(name = "messubf")
    private Integer messubf;

    @Column(name = "anosubf")
    private Integer anosubf;

    @Column(name = "dresp", length = 20)
    private String dresp;

    @Column(name = "ddoc", length = 25)
    private String ddoc;
}
