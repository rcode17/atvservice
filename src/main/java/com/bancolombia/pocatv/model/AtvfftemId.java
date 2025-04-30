package com.bancolombia.pocatv.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AtvfftemId implements Serializable {

	private Integer tmcdsu;
	private String tmcopr;
	private String tmcodo;
	private String tmfear;

	// Constructor vacío
	public AtvfftemId() {}



	public AtvfftemId(Integer tmcdsu, String tmcopr, String tmcodo, String tmfear) {
		this.tmcdsu = tmcdsu;
		this.tmcopr = tmcopr;
		this.tmcodo = tmcodo;
		this.tmfear = tmfear;
	}

}
