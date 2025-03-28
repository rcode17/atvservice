package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class AtvffPdoId implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(name = "xpcopr", length = 2, nullable = false)
    private String xpCopr;

    @Column(name = "xpcodo", length = 3, nullable = false)
    private String xpCodo;

}
