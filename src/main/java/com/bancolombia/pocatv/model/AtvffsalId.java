package com.bancolombia.pocatv.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffsalId implements Serializable {
    private String satpro;
    private String satdoc;
}