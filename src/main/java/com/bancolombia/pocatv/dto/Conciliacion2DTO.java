package com.bancolombia.pocatv.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conciliacion2DTO {
    private String cacopr;
    private String cacodo;
    private BigDecimal casinv;
    private BigDecimal cascont;
    private BigDecimal cadif;
    private String xpdsdo;  // Nombre del documento
    private String opt;     // Opción de selección
}