package com.bancolombia.pocatv.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffcadId implements Serializable {
    private Integer caano;
    private Integer cames;
    private Integer cadia;
    private String cacopr;
    private String cacodo;
}