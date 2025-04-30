package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalMetricaDTO {
	private Integer codArea;
    private String nombre;
    private Integer cump11;
    private Integer cump22;
    private String varc;
    private Integer inf11;
    private Integer inf22;
    private String vari;
}
