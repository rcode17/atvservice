package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteArqueoDTO {
    private String nombre;
    private String region;
    private Integer sucursal;
    private Integer cump11;
    private Integer cump22;
    private Integer varc;
    private Integer inf11;
    private Integer inf22;
    private Integer vari;
    private String mesAnterior;
    private String mesActual;
    private Integer anoAnterior;
    private Integer anoActual;
}
