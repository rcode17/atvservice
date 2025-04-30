package com.bancolombia.pocatv.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de operaciones
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    
    private boolean exito;
    private String mensaje;
    
    // Constructor adicional para respuestas exitosas con mensaje predeterminado
    public static ResponseDTO exitoso() {
        return new ResponseDTO(true, "Operación realizada con éxito");
    }
    
    // Constructor adicional para respuestas de error con mensaje predeterminado
    public static ResponseDTO error(String mensaje) {
        return new ResponseDTO(false, mensaje);
    }
}