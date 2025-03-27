package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
	private String xuUser;
    private String xuName;
    private String xuCarg;
    //private BigDecimal xuArea;
    private BigDecimal xuAcce;
    private String xuDom;
    private BigDecimal xuUsrt;
}
