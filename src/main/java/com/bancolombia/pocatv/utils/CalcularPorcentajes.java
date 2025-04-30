package com.bancolombia.pocatv.utils;

import org.springframework.stereotype.Component;

@Component
public class CalcularPorcentajes {
	 
	/**
     * Calcula el porcentaje de cumplimiento
     * @param ejecutados Arqueos ejecutados
     * @param programados Arqueos programados
     * @return Porcentaje de cumplimiento
     */
    public int calcularPorcentajeCumplimiento(int ejecutados, int programados) {
        if (programados == 0) {
            return 0;
        }
        return (int) Math.round((double) ejecutados / programados * 100);
    }

    /**
     * Calcula el porcentaje de calidad
     * @param cuadrados Arqueos cuadrados
     * @param ejecutados Arqueos ejecutados
     * @return Porcentaje de calidad
     */
    public int calcularPorcentajeCalidad(int cuadrados, int ejecutados) {
        if (ejecutados == 0) {
            return 0;
        }
        return (int) Math.round((double) cuadrados / ejecutados * 100);
    }
    
}
