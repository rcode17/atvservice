package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class AtvffPdsId implements Serializable  {

	@Column(name = "xscosu", nullable = false)
	private Integer xsCosu;
	
	@Column(name = "xscopr", nullable = false, length = 2)
	private String xsCopr;
	
	@Column(name = "xscodo", nullable = false, length = 3)
	private String xsCodo;
	
}
