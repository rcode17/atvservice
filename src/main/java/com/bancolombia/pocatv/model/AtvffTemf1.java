package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvfftemf1")
@IdClass(AtvffTemf1Id.class)
public class AtvffTemf1 {
    
    @Id
    @Column(name = "tfano1")
    private Integer tfano1;
    
    @Id
    @Column(name = "tfmes1")
    private Integer tfmes1;
    
    @Id
    @Column(name = "tfsuc1")
    private Integer tfsuc1;
    
    @Id
    @Column(name = "tfprod1", length = 2)
    private String tfprod1;
    
    @Id
    @Column(name = "tfdoc1", length = 3)
    private String tfdoc1;
    
    @Column(name = "tfresp1", length = 10)
    private String tfresp1;
    
    @Column(name = "tfres1", length = 2)
    private String tfres1;
    
    @Column(name = "tfrel1")
    private Integer tfrel1;
    
    @Column(name = "tfpcal1")
    private Integer tfpcal1;
    
    @Column(name = "tfdresp1", length = 20)
    private String tfdresp1;
}