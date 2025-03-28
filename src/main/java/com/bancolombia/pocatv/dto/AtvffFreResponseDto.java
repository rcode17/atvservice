package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtvffFreResponseDto {
    private String	xpcopr; // Campo de atvffpdo
    private String	xpcodo; // Campo de atvffpdo
    private String	xpdsdo; // Campo de atvffpdo
    private Character	fxfrar; // Campo de atvffpdo
    private BigDecimal	fxdifr; // Campo de atvffpdo
}
