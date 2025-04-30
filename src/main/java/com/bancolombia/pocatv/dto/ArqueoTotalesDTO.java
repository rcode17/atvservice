package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArqueoTotalesDTO {
	
	private Integer tprog;    // Total programados
    private Integer tejec;    // Total ejecutados
    private Integer tcump;    // Total % cumplimiento
    private Integer tcal;     // Total % calidad
    private Integer area;     // Área
    private String fecha;     // Fecha (formato AAAAMM)
	
}
