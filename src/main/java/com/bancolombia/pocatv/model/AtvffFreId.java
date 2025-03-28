package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class AtvffFreId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "fxcopr", length = 2, nullable = false)
    private String fxCopr;

    @Column(name = "fxcodo", length = 3, nullable = false)
    private String fxCodo;
	
}
