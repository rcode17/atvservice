package com.bancolombia.pocatv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffranId implements Serializable {
	private String rnfrec;
	private Integer rncant;
}