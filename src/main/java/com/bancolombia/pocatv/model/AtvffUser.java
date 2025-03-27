package com.bancolombia.pocatv.model;



import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "atvffuser")
public class AtvffUser {

    @Id
    @Column(name = "xuuser")
    private String xuUser;

    @Column(name = "xuname")
    private String xuName;

    @Column(name = "xucarg")
    private String xuCarg;
    
    /*@Column(name = "xuarea")
    private BigDecimal xuArea;*/

    @Column(name = "xuacce")
    private BigDecimal xuAcce;

    @Column(name = "xudom")
    private String xuDom;

    @Column(name = "xuusrt")
    private BigDecimal xuUsrt;
    
    @Column(name = "xupass")
    private String xuPass;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "ATVFFUSER_XBKNAM", 
        joinColumns = @JoinColumn(name = "xuuser", referencedColumnName = "xuuser"),
        inverseJoinColumns = @JoinColumn(name = "xnnmky", referencedColumnName = "xnnmky")
    )
    private Set<Xbknam> xuArea = new HashSet<>();

    
    public void addArea(Xbknam area) {
    	xuArea.add(area);
    }

    public void removeArea(Xbknam area) {
    	xuArea.remove(area);
    }

    
}