package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenMetricasDTO {
	private List<SucursalMetricaDTO> sucursales;
    private Short tot1; // Promedio cump11
    private Short tot2; // Promedio cump22
    private Short tot4; // Promedio inf11
    private Short tot5; // Promedio inf22
    private String antc;
    private String actc;
    private String anti;
    private String acti;
    private Integer anonc;
    private Integer anocc;
    private Integer anoni;
    private Integer anoci;
}
