package com.bancolombia.pocatv.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {
    private String usuario;
    private Integer fecha;
    private Integer hora;
    private Integer codigo;
    private String comentario;
    private String comentario1;
    private String comentario2;
    private String comentario3;
    private String comentario4;
    private Integer sucursal;
    private String producto;
    private String documento;
    private Integer fechaArqueo;
    private BigDecimal diferencia;
}