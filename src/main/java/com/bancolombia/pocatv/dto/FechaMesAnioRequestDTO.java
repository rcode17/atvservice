package com.bancolombia.pocatv.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FechaMesAnioRequestDTO {
	
	@NotNull(message = "El identificador del usuario es requerido")
    private String usuario;

    @NotNull(message = "El mes es requerido")
    @Min(value = 1, message = "El mes debe ser mayor o igual a 1")
    @Max(value = 12, message = "El mes debe ser menor o igual a 12")
    private Integer mes;

    @NotNull(message = "El año es requerido")
    private Integer anio;

}
