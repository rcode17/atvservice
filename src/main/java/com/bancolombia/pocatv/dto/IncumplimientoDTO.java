package com.bancolombia.pocatv.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncumplimientoDTO {
    private String producto;
    private String codZona;
    private Integer codArea;
    private String codpro;
    private String coddoc;
    private String descArea;
    private LocalDate fechaIncumplimiento;
    
}
