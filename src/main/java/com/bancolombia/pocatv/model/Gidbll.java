package com.bancolombia.pocatv.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "GIDBL")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GidbllId.class)
public class Gidbll {
    
    @Id
    @Column(name = "GXDTDY")
    private LocalDate gxdtdy;
    
    @Id
    @Column(name = "GXNOAC")
    private String gxnoac;
    
    @Column(name = "GXFCCD")
    private String gxfccd;
    
    @Column(name = "GXAMDT")
    private BigDecimal gxamdt;
}