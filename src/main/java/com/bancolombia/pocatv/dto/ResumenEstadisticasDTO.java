package com.bancolombia.pocatv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenEstadisticasDTO {
    private List<EstadisticaProductoDTO> estadisticas;
    private Integer eneTotal;
    private Integer febTotal;
    private Integer marTotal;
    private Integer abrTotal;
    private Integer mayTotal;
    private Integer junTotal;
    private Integer julTotal;
    private Integer agoTotal;
    private Integer sepTotal;
    private Integer octTotal;
    private Integer novTotal;
    private Integer dicTotal;
    private Integer contadorProductos;
    private String sucursal;
    private Integer ano;
}