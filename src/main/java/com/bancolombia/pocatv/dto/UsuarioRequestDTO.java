package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioRequestDTO {
	private String xuUser;
    private String xuName;
    private String xuCarg;
    //private BigDecimal xuArea;
    private BigDecimal xuAcce;
    private String xuDom;
    private BigDecimal xuUsrt;
    private String xuPass;
    private List<BigDecimal> xuArea;

}
