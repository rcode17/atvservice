package com.bancolombia.pocatv.dto;

import java.util.ArrayList;
import java.util.List;


public class ResultadoProcesamientoDTO {
    private int totalRegistros;
    private int registrosExitosos;
    private int registrosFallidos;
    private List<String> errores;

    
    public ResultadoProcesamientoDTO() {
        this.totalRegistros = 0;
        this.registrosExitosos = 0;
        this.registrosFallidos = 0;
        this.errores = new ArrayList<>();
    }

    
    public int getTotalRegistros() {
        return totalRegistros;
    }

    
    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    
    public int getRegistrosExitosos() {
        return registrosExitosos;
    }

    
    public void incrementarExitosos() {
        this.registrosExitosos++;
    }

    
    public void decrementarExitosos() {
        this.registrosExitosos--;
    }

    
    public int getRegistrosFallidos() {
        return registrosFallidos;
    }

    
    public void incrementarFallidos() {
        this.registrosFallidos++;
    }

    
    public List<String> getErrores() {
        return errores;
    }

    
    public void agregarError(String error) {
        this.errores.add(error);
    }

    
    @Override
    public String toString() {
        return "Resultado del procesamiento: " +
               "Total de registros: " + totalRegistros +
               ", Exitosos: " + registrosExitosos +
               ", Fallidos: " + registrosFallidos;
    }
}