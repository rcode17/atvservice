package com.bancolombia.pocatv.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar un arqueo descuadrado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArqueoDescuadradoDTO {
    
	private Integer codsuc;
    private String sucurs;
    private String codpro;
    private String coddoc;
    private String producto;
    private LocalDate aqfear;
    private BigDecimal aqdif;
    private BigDecimal aqsfar;
    private String aqres;
    private String shceda;
    private String aqprcu;
    private String aqpear;
    private String aqobsn;
    private String aqobsno;
    private String vaqgest;
    private String vdjust;
    private String accesso;
}