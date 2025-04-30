package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class AtvffPdsId implements Serializable {
	private Integer xscosu;
	private String xscopr;
	private String xscodo;

	// Constructor vacío
	public AtvffPdsId() {}

	// Constructor con parámetros
	public AtvffPdsId(Integer xscosu, String xscopr, String xscodo) {
		this.xscosu = xscosu;
		this.xscopr = xscopr;
		this.xscodo = xscodo;
	}

}
