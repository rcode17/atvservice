package com.bancolombia.pocatv.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtvffTemf1Id implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer tfano1;
    private Integer tfmes1;
    private Integer tfsuc1;
    private String tfprod1;
    private String tfdoc1;
}