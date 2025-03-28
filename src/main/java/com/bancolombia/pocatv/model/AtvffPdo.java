package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "atvffpdo")
public class AtvffPdo {
	
	@EmbeddedId
    private AtvffPdoId id;

    @Column(name = "xpdsdo", length = 25, nullable = false)
    private String xpDsdo;

    @Column(name = "xpcta", nullable = false)
    private Double xpCta;

    @Column(name = "xpstdo", length = 1, nullable = false)
    private String xpStdo;

    @Column(name = "xpfeca", length = 1, nullable = false)
    private String xpFeca;

}
