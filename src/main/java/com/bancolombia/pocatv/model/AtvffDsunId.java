package com.bancolombia.pocatv.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtvffDsunId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer dnano;
    private Integer dnmes;
    private Integer dnxnnmky;
}