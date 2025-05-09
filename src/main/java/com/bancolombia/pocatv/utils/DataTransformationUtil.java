package com.bancolombia.pocatv.utils;

import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.dto.FechaTransformResponseDTO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase utilitaria para transformaciones de datos durante el proceso de carga
 */
@Component
public class DataTransformationUtil {

    /**
     * Convierte una fecha en formato numérico (YYYYMMDD) a un objeto Date
     * 
     * @param fechaNumerica La fecha en formato numérico
     * @return Objeto Date correspondiente a la fecha numérica
     */
    public Date convertirFechaNumericaADate(BigDecimal fechaNumerica) {
        if (fechaNumerica == null) {
            return null;
        }
        
        try {
            // Convertir BigDecimal a String con formato YYYYMMDD
            String fechaStr = String.format("%08d", fechaNumerica.longValue());
            
            // Verificar que la cadena tenga la longitud correcta
            if (fechaStr.length() != 8) {
                throw new IllegalArgumentException("El formato de fecha numérica debe ser YYYYMMDD");
            }
            
            // Extraer año, mes y día
            int year = Integer.parseInt(fechaStr.substring(0, 4));
            int month = Integer.parseInt(fechaStr.substring(4, 6)) - 1; // Los meses en Calendar van de 0 a 11
            int day = Integer.parseInt(fechaStr.substring(6, 8));
            
            // Crear objeto Calendar y configurar la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            
            return calendar.getTime();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al convertir la fecha numérica: " + e.getMessage(), e);
        }
    }
    
    /**
     * Convierte una cadena de fecha en formato ISO (YYYY-MM-DD) a un objeto Date
     * 
     * @param fechaIso La fecha en formato ISO
     * @return Objeto Date correspondiente a la fecha ISO
     */
    public Date convertirFechaIsoADate(String fechaIso) {
        if (fechaIso == null || fechaIso.trim().isEmpty()) {
            return null;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            return sdf.parse(fechaIso);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error al convertir la fecha ISO: " + e.getMessage(), e);
        }
    }
    
    /**
     * Convierte un objeto Date a una representación numérica en formato YYYYMMDD
     * 
     * @param fecha El objeto Date a convertir
     * @return BigDecimal representando la fecha en formato YYYYMMDD
     */
    public BigDecimal convertirDateAFechaNumerica(Date fecha) {
        if (fecha == null) {
            return null;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Los meses en Calendar van de 0 a 11
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        // Formar el número YYYYMMDD
        long fechaNumerica = year * 10000L + month * 100L + day;
        
        return new BigDecimal(fechaNumerica);
    }
    
    /**
     * Convierte una hora en formato numérico (HHMMSS) a un objeto Date configurado con esa hora
     * 
     * @param horaNumerica La hora en formato numérico
     * @return Objeto Date configurado con la hora especificada
     */
    public Date convertirHoraNumericaADate(BigDecimal horaNumerica) {
        if (horaNumerica == null) {
            return null;
        }
        
        try {
            // Convertir BigDecimal a String con formato HHMMSS
            String horaStr = String.format("%06d", horaNumerica.longValue());
            
            // Verificar que la cadena tenga la longitud correcta
            if (horaStr.length() != 6) {
                throw new IllegalArgumentException("El formato de hora numérica debe ser HHMMSS");
            }
            
            // Extraer hora, minutos y segundos
            int hour = Integer.parseInt(horaStr.substring(0, 2));
            int minute = Integer.parseInt(horaStr.substring(2, 4));
            int second = Integer.parseInt(horaStr.substring(4, 6));
            
            // Crear objeto Calendar y configurar la hora
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
            calendar.set(Calendar.MILLISECOND, 0);
            
            return calendar.getTime();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al convertir la hora numérica: " + e.getMessage(), e);
        }
    }
    
    /**
     * Aplica el signo a un valor numérico según el indicador de signo
     * 
     * @param valor El valor numérico
     * @param signo El indicador de signo ('+' o '-')
     * @return El valor con el signo aplicado
     */
    public BigDecimal aplicarSigno(BigDecimal valor, String signo) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        
        if (signo != null && signo.equals("-")) {
            return valor.negate();
        }
        
        return valor;
    }
    
    /**
     * Convierte una cadena a BigDecimal, manejando valores nulos o vacíos
     * 
     * @param valor La cadena a convertir
     * @return BigDecimal correspondiente, o ZERO si la entrada es nula o vacía
     */
    public BigDecimal convertirStringABigDecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        try {
            return new BigDecimal(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al convertir a BigDecimal: " + e.getMessage(), e);
        }
    }
    
    /**
     * Verifica si una cadena está vacía o es nula, devolviendo un valor por defecto en ese caso
     * 
     * @param valor La cadena a verificar
     * @param valorPorDefecto El valor por defecto a devolver si la cadena está vacía
     * @return La cadena original o el valor por defecto
     */
    public String valorPorDefectoSiVacio(String valor, String valorPorDefecto) {
        if (valor == null || valor.trim().isEmpty()) {
            return valorPorDefecto;
        }
        return valor;
    }
    
 // Añade este método a DataTransformationUtil
    public String convertirDateAString(Date fecha) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }
    
    public Integer convertirBigDecimalAInteger(BigDecimal valor) {
        if (valor == null) {
            return null;
        }
        return valor.intValue();
    }
    
    public String convertirBigDecimalAString(BigDecimal valor) {
        if (valor == null) {
            return null;
        }
        return valor.toString();
    }
    
    
    /**
     * Resta un día a la fecha proporcionada en formato MMddyyyy y devuelve un objeto FechaResponse
     * que contiene el día, mes y año resultantes.
     *
     * @param fecha La fecha en formato MMddyyyy.
     * @return Un objeto FechaResponse con el día, mes y año después de restar un día.
     * @throws IllegalArgumentException Si el formato de la fecha es inválido.
     */
    public static FechaTransformResponseDTO restarUnDia(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            LocalDate fechaOriginal = LocalDate.parse(fecha, formatter);
            LocalDate fechaModificada = fechaOriginal.minusDays(1);

            int dia = fechaModificada.getDayOfMonth();
            int mes = fechaModificada.getMonthValue();
            int ano = fechaModificada.getYear();

            return new FechaTransformResponseDTO(dia, mes, ano);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use MMddyyyy.");
        }
    }
}