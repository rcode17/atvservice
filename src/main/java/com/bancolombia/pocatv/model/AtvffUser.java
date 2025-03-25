package com.bancolombia.pocatv.model;



import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "atvffuser")
public class AtvffUser {

    @Id
    @Column(name = "xuuser", length = 10, nullable = false)
    private String xuUser;

    @Column(name = "xuname", length = 60, nullable = false)
    private String xuName;

    @Column(name = "xucarg", length = 6, nullable = false)
    private String xuCarg;

    @Column(name = "xuarea", nullable = false)
    private BigDecimal xuArea;

    @Column(name = "xuacce", nullable = false)
    private BigDecimal xuAcce;

    @Column(name = "xudom", length = 2, nullable = false)
    private String xuDom;

    @Column(name = "xuusrt", nullable = false)
    private BigDecimal xuUsrt;
    
    @Column(name = "xupass", length = 60)
    private String xuPass; 

    
}