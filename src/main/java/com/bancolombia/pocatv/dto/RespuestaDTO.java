package com.bancolombia.pocatv.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO {
    private String mensaje;
    private boolean exito;
}