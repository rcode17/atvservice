package com.bancolombia.pocatv.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffCaeId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer caano;
    private Integer cames;
}