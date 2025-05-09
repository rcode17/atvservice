package com.bancolombia.pocatv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffcaarqId implements Serializable {

    private LocalDate rqfear; // Fecha arqueo
    private Integer rqcdsu;   // Código sucursal
    private String rqcopr;   // Código producto
    private String rqcodo;   // Código documento
}
