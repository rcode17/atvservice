package com.bancolombia.pocatv.model;


import lombok.Data;
import java.io.Serializable;

@Data
public class AtvffcasalId implements Serializable {
    private Integer sdeofic; // OF. FISICA
    private String sdtdoc;   // DOCUMENTO
    
    // Constructor por defecto requerido por JPA
    public AtvffcasalId() {}
    
    // Constructor con parámetros
    public AtvffcasalId(Integer sdeofic, String sdtdoc) {
        this.sdeofic = sdeofic;
        this.sdtdoc = sdtdoc;
    }
}