package com.bancolombia.pocatv.model;


import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class AtvffapaId implements Serializable {
    private Integer apano;
    private Integer apmes;
    private Integer apcodsuc;
    private String apcopr;
    private String apcodo;
    private LocalDate apfear;
    private String apcedcn;
    private String apcedan;
    private BigDecimal apsfar;
    private BigDecimal apdif;
}
