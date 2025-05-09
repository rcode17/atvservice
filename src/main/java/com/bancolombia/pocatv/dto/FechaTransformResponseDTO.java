package com.bancolombia.pocatv.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class FechaTransformResponseDTO {
	private final int dia;
    private final int mes;
    private final int ano;
}
