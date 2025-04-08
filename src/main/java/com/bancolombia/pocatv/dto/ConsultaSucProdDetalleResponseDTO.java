package com.bancolombia.pocatv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaSucProdDetalleResponseDTO {
    private String salida;
    private int ejec;        
    private int cumpl;       
    private int cuad;        
    private int desc;      
    private int cuad1;      
    private int desc1;      
    private int cal;      
    private int cant;       
    private int arq;  

}
