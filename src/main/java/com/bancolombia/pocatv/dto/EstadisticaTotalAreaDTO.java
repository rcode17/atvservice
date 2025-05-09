package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaTotalAreaDTO {
	private Integer ent; // Total enero
    private Integer fet; // Total febrero
    private Integer mart; // Total marzo
    private Integer abt; // Total abril
    private Integer mayt; // Total mayo
    private Integer junt; // Total junio
    private Integer jult; // Total julio
    private Integer agt; // Total agosto
    private Integer set; // Total septiembre
    private Integer oct; // Total octubre
    private Integer nt; // Total noviembre
    private Integer dit; // Total diciembre
}
