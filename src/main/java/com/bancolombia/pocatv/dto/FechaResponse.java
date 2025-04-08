package com.bancolombia.pocatv.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FechaResponse {
	private String resultado;
    private String mensajeError;

}
