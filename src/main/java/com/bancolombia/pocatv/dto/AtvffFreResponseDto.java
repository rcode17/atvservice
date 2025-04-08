package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtvffFreResponseDto {
    private String	xpCopr;
    private String	xpCodo;
    private String	xpDsdo;
    private BigDecimal	xpCta;
    private Character	xpStdo;
    private Character	xpFeca;
    private Character	fxFrar;
    private BigDecimal	fxDifr;
}
