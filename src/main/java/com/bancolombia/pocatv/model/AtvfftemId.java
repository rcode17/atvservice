package com.bancolombia.pocatv.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AtvfftemId implements Serializable {

	private Integer tmcdsu;
	private String tmcopr;
	private String tmcodo;
	private LocalDate tmfear;

	// Constructor vacío
	public AtvfftemId() {}



	public AtvfftemId(Integer tmcdsu, String tmcopr, String tmcodo, LocalDate tmfear) {
		this.tmcdsu = tmcdsu;
		this.tmcopr = tmcopr;
		this.tmcodo = tmcodo;
		this.tmfear = tmfear;
	}

}
