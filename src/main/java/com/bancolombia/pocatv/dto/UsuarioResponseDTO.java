package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.bancolombia.pocatv.model.Xbknam;

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
    private BigDecimal xuAcce;
    private String xuDom;
    private BigDecimal xuUsrt;
    private String xuPass;
    private Set<Xbknam> xuArea;
}
