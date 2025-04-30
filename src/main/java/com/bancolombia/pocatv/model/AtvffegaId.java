package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtvffegaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "egmesin", nullable = false)
    private Integer egmesin;

    @Column(name = "eganoin", nullable = false)
    private Integer eganoin;

    @Column(name = "egsucursal", nullable = false)
    private Integer egsucursal;
}
