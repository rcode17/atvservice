package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaRegionDTO {
    private String nombre;
    private Integer enero;
    private Integer feb;
    private Integer marzo;
    private Integer abril;
    private Integer mayo;
    private Integer junio;
    private Integer julio;
    private Integer agosto;
    private Integer sep;
    private Integer octubre;
    private Integer nov;
    private Integer dic;
    private String region;
    private Integer proma;
}
