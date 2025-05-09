package com.bancolombia.pocatv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvfftitvaId implements Serializable {
	private Integer rqfear;
	private Integer rqcdsu;
	private String rqcopr;
	private String rqcodo;
}
