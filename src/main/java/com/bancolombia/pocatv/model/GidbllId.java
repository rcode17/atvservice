package com.bancolombia.pocatv.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GidbllId implements Serializable {
    private LocalDate gxdtdy;
    private String gxnoac;
}