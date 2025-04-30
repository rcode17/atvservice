package com.bancolombia.pocatv.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacionDTO {
    private Integer codigoArea;
    private String nombreArea;
    private BigDecimal saldoInventario;
    private BigDecimal saldoContable;
    private BigDecimal diferencia;
}