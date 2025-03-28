package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "atvfffre")
public class AtvffFre {

	@EmbeddedId
    private AtvffFreId id;

    @Column(name = "fxfrar", length = 1, nullable = false)
    private String fxFrar;

    @Column(name = "fxdifr", nullable = false)
    private Integer fxDifr;
}
