package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.FechaVerificacionDTO;


public interface AtvrrtotService {
    void validarFecha(FechaVerificacionDTO fecha);
    void recalcularTotales(int mes, int ano);
}