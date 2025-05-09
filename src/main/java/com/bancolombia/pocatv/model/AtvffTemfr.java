package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvfftemfr")
@IdClass(AtvffTemfrId.class)
public class AtvffTemfr {
    
    @Id
    @Column(name = "tfano")
    private Integer tfano;
    
    @Id
    @Column(name = "tfmes")
    private Integer tfmes;
    
    @Id
    @Column(name = "tfresp", length = 10)
    private String tfresp;
    
    @Column(name = "tfsuc")
    private Integer tfsuc;
    
    @Column(name = "tfprod", length = 2)
    private String tfprod;
    
    @Column(name = "tfdoc", length = 3)
    private String tfdoc;
    
    @Column(name = "tfres", length = 2)
    private String tfres;
    
    @Column(name = "tfrel")
    private Integer tfrel;
    
    @Column(name = "tfpcal")
    private Integer tfpcal;
    
    @Column(name = "tfdresp", length = 20)
    private String tfdresp;
}