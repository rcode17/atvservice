package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AtvffmdId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "mdcopr", length = 2, nullable = false)
    private String mdcopr;

    @Column(name = "mdcodo", length = 3, nullable = false)
    private String mdcodo;

    @Column(name = "mdano", length = 3, nullable = false)
    private Integer mdano;

    @Column(name = "mdmes", length = 3, nullable = false)
    private Short mdmes;

}