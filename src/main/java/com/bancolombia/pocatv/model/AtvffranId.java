package com.bancolombia.pocatv.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AtvffranId implements Serializable {
	private String rnfrec;
	private Integer rncant;
}