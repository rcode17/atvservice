package com.bancolombia.pocatv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffilId implements Serializable {
    private BigDecimal tmcdsu;
    private String tmcopr;
    private String tmcodo;
    private Date tmfear;
}