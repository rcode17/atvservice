package com.bancolombia.pocatv.utils;

public class RangoUtil {

        /**
         * Verifica si un campo de rango tiene valor
         * @param valor Valor a verificar
         * @return true si tiene valor, false en caso contrario
         */
        public static boolean tieneValor(String valor) {
            return valor != null && !valor.trim().isEmpty();
        }
}