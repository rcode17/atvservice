package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.Atvrapa2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class Atvrapa2ServiceImpl implements Atvrapa2Service {

    @Autowired
    private AtvffarqRepository arqueoRepository;

    @Autowired
    private AtvffFreRepository frecuenciaRepository;

    @Autowired
    private AtvffranRepository rangoRepository;

    @Autowired
    private AtvffapaRepository arqueoAnormalRepository;

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @Transactional
    public int procesarArqueosAnormales(Integer ano, String fechaStr) {
        // Inicializar variables
        LocalDate fechaProceso = fechaStr != null && !fechaStr.isEmpty()
                ? LocalDate.parse(fechaStr, ISO_FORMATTER)
                : LocalDate.now();

        int mes = fechaProceso.getMonthValue();
        int year = fechaProceso.getYear();
        int mesInicial, mesFinal;

        // Determinar rango de meses a procesar
        if (year == ano) {
            mesInicial = 1;
            mesFinal = mes - 1;
            if (mesFinal < 1) mesFinal = 1;
        } else if (year > ano) {
            mesInicial = 1;
            mesFinal = 12;
        } else {
            throw new IllegalArgumentException("El año a procesar no puede ser mayor al año actual");
        }

        // Contador de arqueos procesados
        int arqueosProcesados = 0;

        // Obtener todos los arqueos del año
        String anoStr = String.valueOf(ano);
        List<Atvffarq> todosArqueos = arqueoRepository.findByYear(anoStr);

        // Agrupar arqueos por sucursal, producto y documento
        Map<String, List<Atvffarq>> arqueosPorGrupo = todosArqueos.stream()
                .collect(Collectors.groupingBy(arqueo ->
                        arqueo.getAqcdsu() + "-" + arqueo.getAqcopr() + "-" + arqueo.getAqcodo()));

        // Procesar cada grupo de arqueos
        for (Map.Entry<String, List<Atvffarq>> entry : arqueosPorGrupo.entrySet()) {
            List<Atvffarq> arqueos = entry.getValue();
            if (arqueos.size() < 2) continue;

            // Ordenar arqueos por fecha
            arqueos.sort(Comparator.comparing(Atvffarq::getAqfear));

            // Obtener información de frecuencia
            Atvffarq primerArqueo = arqueos.get(0);
            Optional<AtvffFre> frecuenciaOpt = frecuenciaRepository.findByFxCoprAndFxCodo(
                    primerArqueo.getAqcopr(),
                    primerArqueo.getAqcodo()
            );

            if (frecuenciaOpt.isEmpty()) continue;
            AtvffFre frecuencia = frecuenciaOpt.get();

            // Obtener rangos de validación
            Optional<Atvffran> rangoOpt = rangoRepository.findByRnfrecAndRncant(
                    frecuencia.getFxFrar(),
                    frecuencia.getFxDifr()
            );

            if (rangoOpt.isEmpty()) continue;
            Atvffran rango = rangoOpt.get();

            // Validar periodicidad entre arqueos
            for (int i = 1; i < arqueos.size(); i++) {
                Atvffarq arqueoActual = arqueos.get(i);
                Atvffarq arqueoAnterior = arqueos.get(i-1);

                // Calcular días entre arqueos
                LocalDate fechaActual = LocalDate.parse(arqueoActual.getAqfear(), ISO_FORMATTER);
                LocalDate fechaAnterior = LocalDate.parse(arqueoAnterior.getAqfear(), ISO_FORMATTER);
                long diasEntreFechas = ChronoUnit.DAYS.between(fechaAnterior, fechaActual);

                // Verificar si está fuera del rango permitido
                if (diasEntreFechas < rango.getRnrain() || diasEntreFechas > rango.getRnrafn()) {
                    // Extraer mes de la fecha actual
                    int mesArqueo = fechaActual.getMonthValue();

                    // Verificar si ya existe un registro para este arqueo
                    boolean existeRegistro = arqueoAnormalRepository.existsByApanoAndApmesAndApcodsucAndApcoprAndApcodoAndApfear(
                            ano,
                            mesArqueo,
                            arqueoActual.getAqcdsu(),
                            arqueoActual.getAqcopr(),
                            arqueoActual.getAqcodo(),
                            LocalDate.parse(arqueoActual.getAqfear(), ISO_FORMATTER)
                    );

                    if (!existeRegistro) {
                        // Crear registro de arqueo anormal
                        Atvffapa arqueoAnormal = new Atvffapa();

                        arqueoAnormal.setApano(ano);
                        arqueoAnormal.setApmes(mesArqueo);
                        arqueoAnormal.setApcodsuc(arqueoActual.getAqcdsu());
                        arqueoAnormal.setApcopr(arqueoActual.getAqcopr());
                        arqueoAnormal.setApcodo(arqueoActual.getAqcodo());
                        arqueoAnormal.setApfear(LocalDate.parse(arqueoActual.getAqfear(), ISO_FORMATTER));
                        arqueoAnormal.setApcedcn(arqueoActual.getAqcedcn());
                        arqueoAnormal.setApcedan(arqueoActual.getAqcedan());
                        arqueoAnormal.setApsfar(arqueoActual.getAqsfar());
                        arqueoAnormal.setApdif(arqueoActual.getAqdif());
                        arqueoAnormal.setApnomsuc(arqueoActual.getAqsuc());

                        // Guardar en la base de datos
                        arqueoAnormalRepository.save(arqueoAnormal);
                        arqueosProcesados++;
                    }
                }
            }
        }

        return arqueosProcesados;
    }

    @Override
    public List<Atvffapa> obtenerArqueosAnormales(Integer ano) {
        if (ano == null) {
            throw new IllegalArgumentException("El año no puede ser nulo");
        }
        return arqueoAnormalRepository.findByApano(ano);
    }

    @Override
    public List<Atvffapa> obtenerArqueosAnormalesPorMes(Integer ano, Integer mes) {
        if (ano == null || mes == null) {
            throw new IllegalArgumentException("El año y el mes no pueden ser nulos");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        return arqueoAnormalRepository.findByApanoAndApmes(ano, mes);
    }
}
