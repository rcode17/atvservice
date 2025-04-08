package com.bancolombia.pocatv.utils;

import java.time.LocalDate;

public class ValidateFechaUtil {
	/**
     * Valida que el mes y año ingresados sean correctos.
     *
     * @param mes   El mes a validar (1-12)
     * @param anio  El año a validar (por ejemplo, entre 2000 y 3000)
     * @return      Cadena vacía si es válido, o un mensaje de error si no lo es.
     */
    public static String validarMesYAnio(int mes, int anio) {
        // Validar que el mes esté en rango.
        if (mes < 1 || mes > 12) {
            return "MES INVALIDO: El mes debe estar entre 1 y 12.";
        }

        // Validar el rango del año.
        if (anio < 2000 || anio > 3000) {
            return "AÑO INVALIDO: El año debe estar entre 2000 y 3000.";
        }

        // Obtener la fecha actual.
        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        int anioActual = hoy.getYear();

        // Validar que el año ingresado no sea posterior al año actual.
        if (anio > anioActual) {
            return "AÑO POSTERIOR: El año ingresado es mayor que el actual.";
        }

        // Si el año ingresado es el actual, el mes no debe ser posterior al mes actual.
        if (anio == anioActual && mes > mesActual) {
            return "MES NO CUMPLIDO: El mes ingresado es posterior al mes actual.";
        }

        // Todo es válido
        return "";
    }

}
