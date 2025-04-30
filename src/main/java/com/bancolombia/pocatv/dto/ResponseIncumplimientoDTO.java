package com.bancolombia.pocatv.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseIncumplimientoDTO<T> {
	 /**
     * Código de estado de la respuesta
     */
    private String codigo;
    
    /**
     * Mensaje descriptivo de la respuesta
     */
    private String mensaje;
    
    /**
     * Datos de la respuesta, pueden ser de cualquier tipo
     */
    private T datos;
    
    /**
     * Constructor para respuestas exitosas con datos
     * 
     * @param datos Los datos a incluir en la respuesta
     */
    public ResponseIncumplimientoDTO(T datos) {
        this.codigo = "00";
        this.mensaje = "Operación exitosa";
        this.datos = datos;
    }
    
    /**
     * Constructor para respuestas con código y mensaje personalizados
     * 
     * @param codigo Código de la respuesta
     * @param mensaje Mensaje descriptivo
     */
    public ResponseIncumplimientoDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.datos = null;
    }
    
    /**
     * Método estático para crear una respuesta exitosa
     * 
     * @param datos Los datos a incluir en la respuesta
     * @return Un objeto ResponseDTO con los datos proporcionados
     */
    public static <T> ResponseIncumplimientoDTO<T> exitoso(T datos) {
        return new ResponseIncumplimientoDTO<>("00", "Operación exitosa", datos);
    }
    
    /**
     * Método estático para crear una respuesta de error
     * 
     * @param codigo Código de error
     * @param mensaje Mensaje de error
     * @return Un objeto ResponseDTO con el código y mensaje de error
     */
    public static <T> ResponseIncumplimientoDTO<T> error(String codigo, String mensaje) {
        return new ResponseIncumplimientoDTO<>(codigo, mensaje, null);
    }
}
