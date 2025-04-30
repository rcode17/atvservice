package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArqueoDTO {

	private String xpcopr;        // Código producto
    private String xpcodo;        // Código documento
    private String xpdsdo;        // Descripción documento
    private String region;        // Región
    private String fxfrar;        // Frecuencia arqueo
    private Integer prog;         // Arqueos programados
    private Integer ejec;         // Arqueos ejecutados
    private Integer cump;         // % Cumplimiento
    private Integer cal;          // % Calidad
    private String opt;           // Opción seleccionada
	
}
