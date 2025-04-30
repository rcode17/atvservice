package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ArqueoAnormalDTO {
    private Integer codsuc;
    private String suc;
    private String prod;
    private String doc;
    private String desprod;
    private LocalDate fecharq;
    private String fechaArqueoStr;

    // Atributos adicionales necesarios
    private Integer ano;
    private Integer mes;
    private String cedulaCustodia;
    private String cedulaArqueo;
    private BigDecimal saldoFisicoArqueo;
    private BigDecimal diferencia;
    private Long diasDiferencia;
    private String estadoArqueo;
}
