package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class atvffcepfechaResponseDTO {
	  private String cpcopr;
	    private String cpcodo;
	    private String xpdsdo;
	    private BigDecimal cpcumpli;
	    private BigDecimal cpcalid;

}
