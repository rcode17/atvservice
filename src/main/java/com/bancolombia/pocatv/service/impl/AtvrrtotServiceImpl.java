package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.FechaVerificacionDTO;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffapaService;
import com.bancolombia.pocatv.service.AtvrrtotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class AtvrrtotServiceImpl implements AtvrrtotService {

    @Override
    public void validarFecha(FechaVerificacionDTO fechaVerificacion) {
        if (fechaVerificacion.getFechaConsulta() == null || fechaVerificacion.getFechaConsulta().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }

        String fechaStr = fechaVerificacion.getFechaConsulta();
        if (fechaStr.length() != 6 || !fechaStr.matches("\\d{6}")) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser MMAAAA");
        }

        int mes = Integer.parseInt(fechaStr.substring(0, 2));
        int ano = Integer.parseInt(fechaStr.substring(2));

        // Validar que el mes esté entre 1 y 12
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("FECHA INVALIDA");
        }

        // Obtener fecha actual
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        int anoActual = fechaActual.getYear();

        // Validar que el año no sea posterior al actual
        if (ano > anoActual) {
            throw new IllegalArgumentException("AÑO POSTERIOR");
        }

        // Validar que si es el año actual, el mes no sea posterior al actual
        if (ano == anoActual && mes > mesActual) {
            throw new IllegalArgumentException("MES NO CUMPLIDO");
        }

        // Validar que el año esté entre 2000 y 3000
        if (ano < 2000 || ano > 3000) {
            throw new IllegalArgumentException("FECHA INVALIDA");
        }

        // Si pasa todas las validaciones, recalcular totales
        recalcularTotales(mes, ano);
    }

    @Override
    public void recalcularTotales(int mes, int ano) {
        // Aquí iría la lógica que en RPG se implementa con la llamada a ATVRMPERC
        // Por ahora, solo imprimimos un mensaje de log
        System.out.println("Recalculando totales para el mes " + mes + " del año " + ano);
        // Implementar la lógica específica según lo que haga ATVRMPERC
    }
}