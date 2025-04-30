package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEspecificaDTO {
    private Integer mes;
    private Integer ano;
    private Integer cumpli;
    private Integer calid;
    private String copr;
    private String codo;
    private String dsdo;
}