package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffapaService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class AtvffapaServiceImpl implements AtvffapaService {

    private AtvffarqRepository atvffarqRepository;

    @Autowired
    private AtvffFreRepository atvfffreRepository;

    @Autowired
    private AtvffranRepository atvffranRepository;

    @Autowired
    private AtvffapaRepository atvffapaRepository;

    @Autowired
    private AtvffPdoRepository atvffpdoRepository;

    @Autowired
    public AtvffapaServiceImpl(AtvffarqRepository atvffarqRepository) {
        this.atvffarqRepository = atvffarqRepository;
    }

    @Override
    @Transactional
    public int procesarArqueosAnormales(Integer ano) {
        if (ano == null) {
            throw new IllegalArgumentException("El año no puede ser nulo");
        }

        // Contador de registros procesados
        int registrosProcesados = 0;

        // Obtener el año y mes actual
        LocalDate fechaActual = LocalDate.now();
        int anoActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonthValue();

        // Determinar el mes final para el procesamiento
        int mesFinal;
        if (anoActual == ano) {
            mesFinal = mesActual - 1;
            if (mesFinal <= 0) {
                mesFinal = 12;
            }
        } else if (anoActual > ano) {
            mesFinal = 12;
        } else {
            throw new IllegalArgumentException("No se puede procesar un año futuro");
        }

        // Obtener todos los arqueos del año especificado
        List<Atvffarq> arqueos = atvffarqRepository.findByYear(String.valueOf(ano));

        // Agrupar arqueos por sucursal, producto y documento
        for (Atvffarq arqueo : arqueos) {
            Integer sucursal = arqueo.getAqcdsu();
            String codigoProducto = arqueo.getAqcopr();
            String codigoDocumento = arqueo.getAqcodo();

            // Obtener la frecuencia de arqueo para este producto/documento
            Optional<AtvffFre> frecuenciaOpt = atvfffreRepository.findByFxCoprAndFxCodo(codigoProducto, codigoDocumento);
            if (!frecuenciaOpt.isPresent()) {
                continue; // Si no hay configuración de frecuencia, pasar al siguiente
            }

            AtvffFre frecuencia = frecuenciaOpt.get();
            String tipoFrecuencia = frecuencia.getFxFrar();
            Integer diasFrecuencia = frecuencia.getFxDifr();

            // Obtener los rangos permitidos para esta frecuencia
            Optional<Atvffran> rangoOpt = atvffranRepository.findByRnfrecAndRncant(tipoFrecuencia, diasFrecuencia);
            if (!rangoOpt.isPresent()) {
                continue; // Si no hay configuración de rango, pasar al siguiente
            }

            Atvffran rango = rangoOpt.get();
            Integer rangoInicial = rango.getRnrain();
            Integer rangoFinal = rango.getRnrafn();

            // Obtener todos los arqueos para esta sucursal/producto/documento ordenados por fecha
            List<Atvffarq> arqueosOrdenados = atvffarqRepository.findBySucursalAndProductoAndDocumentoOrderByFecha(
                    sucursal, codigoProducto, codigoDocumento);

            // Verificar periodicidad anormal
            for (int i = 1; i < arqueosOrdenados.size(); i++) {
                Atvffarq arqueoActual = arqueosOrdenados.get(i);
                Atvffarq arqueoAnterior = arqueosOrdenados.get(i - 1);

                // Convertir fechas de String a LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta el formato según tus datos
                LocalDate fechaAnterior = LocalDate.parse(arqueoAnterior.getAqfear(), formatter);
                LocalDate fechaActual_ = LocalDate.parse(arqueoActual.getAqfear(), formatter);

                // Calcular días entre fechas
                long diasEntreFechas = ChronoUnit.DAYS.between(fechaAnterior, fechaActual_);

                // Verificar si está fuera del rango permitido
                if (diasEntreFechas < rangoInicial || diasEntreFechas > rangoFinal) {

                    // Convertir la fecha de String a LocalDate
                    DateTimeFormatter _formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta el formato según tus datos
                    LocalDate fechaArqueo = LocalDate.parse(arqueoActual.getAqfear(), _formatter);

                    // Crear registro de arqueo anormal
                    Atvffapa arqueoAnormal = new Atvffapa();
                    arqueoAnormal.setApano(ano);
                    arqueoAnormal.setApmes(fechaArqueo.getMonthValue());
                    arqueoAnormal.setApcodsuc(sucursal);
                    arqueoAnormal.setApcopr(codigoProducto);
                    arqueoAnormal.setApcodo(codigoDocumento);
                    arqueoAnormal.setApfear(fechaArqueo);
                    arqueoAnormal.setApcedcn(arqueoActual.getAqcedcn());
                    arqueoAnormal.setApcedan(arqueoActual.getAqcedan());
                    arqueoAnormal.setApsfar(arqueoActual.getAqsfar());
                    arqueoAnormal.setApdif(arqueoActual.getAqdif());

                    // Obtener nombre de sucursal (simulando la lógica del RPG original)
                    arqueoAnormal.setApnomsuc("Sucursal " + sucursal); // Esto debería obtenerse de otra tabla

                    // Verificar si ya existe este registro para evitar duplicados
                    AtvffapaId id = new AtvffapaId();
                    id.setApano(arqueoAnormal.getApano());
                    id.setApmes(arqueoAnormal.getApmes());
                    id.setApcodsuc(arqueoAnormal.getApcodsuc());
                    id.setApcopr(arqueoAnormal.getApcopr());
                    id.setApcodo(arqueoAnormal.getApcodo());
                    id.setApfear(arqueoAnormal.getApfear());
                    id.setApcedcn(arqueoAnormal.getApcedcn());
                    id.setApcedan(arqueoAnormal.getApcedan());
                    id.setApsfar(arqueoAnormal.getApsfar());
                    id.setApdif(arqueoAnormal.getApdif());

                    if (!atvffapaRepository.existsById(id)) {
                        atvffapaRepository.save(arqueoAnormal);
                        registrosProcesados++;
                    }
                }
            }
        }

        return registrosProcesados;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ArqueoAnormalDTO> consultarArqueosAnormales(Integer ano, Integer mes) {
        // Validación de parámetros
        if (ano == null) {
            throw new IllegalArgumentException("El año no puede ser nulo");
        }

        // Consultar los arqueos anormales desde la tabla ATVFFAPA
        List<Atvffapa> arqueosAnormales;
        if (mes != null) {
            // Si se especifica el mes, filtrar por año y mes
            arqueosAnormales = atvffapaRepository.findByApanoAndApmes(ano, mes);
        } else {
            // Si no se especifica el mes, consultar todos los del año
            arqueosAnormales = atvffapaRepository.findByApano(ano);
        }

        if (arqueosAnormales.isEmpty()) {
            throw new IllegalArgumentException("No hay registros de arqueos anormales para el año " + ano +
                    (mes != null ? " y mes " + mes : ""));
        }

        // Obtener todas las frecuencias para los productos/documentos
        List<String> productosDocumentos = arqueosAnormales.stream()
                .map(a -> a.getApcopr() + "-" + a.getApcodo())
                .distinct()
                .toList();

        // Consultar las frecuencias y rangos para cada producto/documento
        Map<String, AtvffFre> frecuenciasMap = new HashMap<>();
        Map<String, Atvffran> rangosMap = new HashMap<>();

        for (String prodDoc : productosDocumentos) {
            String[] parts = prodDoc.split("-");
            String codigoProducto = parts[0];
            String codigoDocumento = parts[1];

            Optional<AtvffFre> frecuenciaOpt = atvfffreRepository.findByFxCoprAndFxCodo(codigoProducto, codigoDocumento);
            if (frecuenciaOpt.isPresent()) {
                AtvffFre frecuencia = frecuenciaOpt.get();
                frecuenciasMap.put(prodDoc, frecuencia);

                Optional<Atvffran> rangoOpt = atvffranRepository.findByRnfrecAndRncant(
                        frecuencia.getFxFrar(), frecuencia.getFxDifr());
                rangoOpt.ifPresent(atvffran -> rangosMap.put(prodDoc, atvffran));
            }
        }

        // Convertir entidades a DTOs
        List<ArqueoAnormalDTO> resultado = new ArrayList<>();

        // Definir el formateador de fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Atvffapa arqueo : arqueosAnormales) {
            ArqueoAnormalDTO dto = new ArqueoAnormalDTO();

            // Mapear campos según el DTO proporcionado
            dto.setCodsuc(arqueo.getApcodsuc());
            dto.setSuc(arqueo.getApnomsuc());
            dto.setProd(arqueo.getApcopr());
            dto.setDoc(arqueo.getApcodo());
            dto.setFecharq(arqueo.getApfear());
            dto.setFechaArqueoStr(arqueo.getApfear().format(formatter));



            // Obtener la descripción del producto directamente
            String codigoProducto = arqueo.getApcopr();
            String codigoDocumento = arqueo.getApcodo();
            AtvffPdo producto = atvffpdoRepository.findByXpcoprAndXpcodo(codigoProducto, codigoDocumento);
            Optional<AtvffPdo> productoOpt = Optional.ofNullable(producto);
            String descripcion = productoOpt.isPresent() ? productoOpt.get().getXpdsdo() : "Descripción no disponible";
            dto.setDesprod(descripcion);



            // Campos adicionales
            dto.setAno(arqueo.getApano());
            dto.setMes(arqueo.getApmes());
            dto.setCedulaCustodia(arqueo.getApcedcn());
            dto.setCedulaArqueo(arqueo.getApcedan());
            dto.setSaldoFisicoArqueo(arqueo.getApsfar());
            dto.setDiferencia(arqueo.getApdif());

            // Buscar el arqueo anterior para calcular la diferencia de días
            Optional<Atvffarq> arqueoAnteriorOpt = atvffarqRepository.findArqueoAnterior(
                    arqueo.getApcodsuc(), arqueo.getApcopr(), arqueo.getApcodo(), arqueo.getApfear());

            if (arqueoAnteriorOpt.isPresent()) {

                Atvffarq arqueoAnterior = arqueoAnteriorOpt.get();
                // Convertir las fechas de String a LocalDate
                LocalDate fechaAnterior = LocalDate.parse(arqueoAnterior.getAqfear(), formatter);

                long diasDiferencia = ChronoUnit.DAYS.between(fechaAnterior, arqueo.getApfear());
                dto.setDiasDiferencia(diasDiferencia);

                // Determinar el estado del arqueo basado en los rangos permitidos
                if (rangosMap.containsKey(productosDocumentos)) {
                    Atvffran rango = rangosMap.get(productosDocumentos);
                    if (diasDiferencia < rango.getRnrain()) {
                        dto.setEstadoArqueo("ANTICIPADO");
                    } else if (diasDiferencia > rango.getRnrafn()) {
                        dto.setEstadoArqueo("TARDÍO");
                    } else {
                        dto.setEstadoArqueo("NORMAL"); // No debería ocurrir, pero por completitud
                    }
                } else {
                    dto.setEstadoArqueo("SIN CONFIGURACIÓN");
                }
            } else {
                dto.setDiasDiferencia(null);
                dto.setEstadoArqueo("PRIMER ARQUEO");
            }

            resultado.add(dto);
        }

        return resultado;
    }

}
