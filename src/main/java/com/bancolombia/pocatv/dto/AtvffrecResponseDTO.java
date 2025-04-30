package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtvffrecResponseDTO {
	
	private String rcfere;
	private Date rcfear;
    private String rcsuc;
    private String rccedcn;
    private BigDecimal rcsfar;
    private Integer rccdsu;
    private Integer rccdsuf;
    private String rcrech;
    private String rcprcu;
    private String rcpear;

}
