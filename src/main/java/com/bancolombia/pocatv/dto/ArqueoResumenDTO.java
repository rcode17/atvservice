package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArqueoResumenDTO {
	
	private String pro;           // Código producto
    private String doc;           // Código documento
    private String des;           // Descripción
    private String nomReg;        // Nombre región
    private String fecha;         // Fecha
    private Integer arqueos;      // Número de arqueos
    private Integer cuadrados;    // Arqueos cuadrados
    private Integer cumplimiento; // % Cumplimiento
    private Integer calidad;      // % Calidad
	
}
