package com.bancolombia.pocatv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atvffsai1Id implements Serializable {
    private BigDecimal tmcdsu;
    private String tmcopr;
    private String tmcodo;
    private BigDecimal tmfear;
}