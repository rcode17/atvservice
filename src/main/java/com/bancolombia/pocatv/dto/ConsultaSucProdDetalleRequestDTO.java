package com.bancolombia.pocatv.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaSucProdDetalleRequestDTO {
    private String xuUser;
    private int mes;
    private int ano;
    private int xpCopR;
    private int xpCoD0;
    private String xpDsdo;

}
