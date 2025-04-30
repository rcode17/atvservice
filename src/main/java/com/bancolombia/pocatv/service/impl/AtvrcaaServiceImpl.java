package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.AtvffrecResponseDTO;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvrcaaService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AtvrcaaServiceImpl implements AtvrcaaService {

    private static final Logger logger = LoggerFactory.getLogger(AtvrcaaServiceImpl.class);

    private final AtvffFreRepository atvfffreRepository;
    private final AtvffarqRepository atvffarqRepository;
    private final AtvffmdRepository atvffmdRepository;
    private final AtvffPdoRepository atvffpdoRepository;
    private final XbknamRepository xbknamRepository;
    private final XmgregRepository xmgregRepository;
    private final AtvffcaaRepository atvffcaaRepository;

    @Autowired
    public AtvrcaaServiceImpl(
            AtvffFreRepository atvfffreRepository,
            AtvffarqRepository atvffarqRepository,
            AtvffmdRepository atvffmdRepository,
            AtvffPdoRepository atvffpdoRepository,
            XbknamRepository xbknamRepository,
            XmgregRepository xmgregRepository,
            AtvffcaaRepository atvffcaaRepository) {
        this.atvfffreRepository = atvfffreRepository;
        this.atvffarqRepository = atvffarqRepository;
        this.atvffmdRepository = atvffmdRepository;
        this.atvffpdoRepository = atvffpdoRepository;
        this.xbknamRepository = xbknamRepository;
        this.xmgregRepository = xmgregRepository;
        this.atvffcaaRepository = atvffcaaRepository;
    }

    @Override
    @Transactional
    public List<Atvffcaa> procesarDatosAnuales(Integer ano) {
        validarAno(ano);

        try {

            // Limpiamos los datos existentes para el año
            eliminarDatosPorAno(ano);

            // Obtenemos todas las regiones
            List<Xmgreg> regiones = xmgregRepository.findAll();
            if (regiones.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron regiones para procesar");
            }

            List<Atvffcaa> resultados = new ArrayList<>();

            // Procesamos cada región
            for (Xmgreg region : regiones) {
                Atvffcaa resultado = procesarRegion(region, ano);
                if (resultado != null) {
                    resultados.add(resultado);
                    atvffcaaRepository.save(resultado);
                }
            }

            return resultados;

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al procesar datos anuales: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al procesar datos anuales para el año {}: {}", ano, e.getMessage(), e);
            throw new RuntimeException("Error al procesar datos anuales: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Atvffcaa> obtenerDatosPorAno(Integer ano) {
        validarAno(ano);

        try {
            logger.info("Consultando datos para el año: {}", ano);
            List<Atvffcaa> resultados = atvffcaaRepository.findByAaanos(ano);

            if (resultados.isEmpty()) {
                logger.warn("No se encontraron datos para el año: {}", ano);
            } else {
                logger.info("Se encontraron {} registros para el año: {}", resultados.size(), ano);
            }

            return resultados;
        } catch (Exception e) {
            logger.error("Error al obtener datos para el año {}: {}", ano, e.getMessage(), e);
            throw new RuntimeException("Error al obtener datos por año: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void eliminarDatosPorAno(Integer ano) {
        validarAno(ano);

        try {
            logger.info("Eliminando datos para el año: {}", ano);

            List<Atvffcaa> registrosAEliminar = atvffcaaRepository.findByAaanos(ano);
            if (!registrosAEliminar.isEmpty()) {
                atvffcaaRepository.deleteAll(registrosAEliminar);
                logger.info("Se eliminaron {} registros para el año: {}", registrosAEliminar.size(), ano);
            } else {
                logger.info("No se encontraron registros para eliminar del año: {}", ano);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar datos para el año {}: {}", ano, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar datos por año: " + e.getMessage(), e);
        }
    }

    // Valida que el año esté dentro del rango permitido
    private void validarAno(Integer ano) {
        if (ano == null || ano < 1900 || ano > 2100) {
            throw new IllegalArgumentException("El año proporcionado no es válido: " + ano);
        }
    }
    // Procesa los datos de una región específica para el año indicado
    private Atvffcaa procesarRegion(Xmgreg region, Integer ano) {
        try {
            String regionCodigo = region.getXmcdmr();


            // Obtenemos las sucursales de la región
            List<Xbknam> sucursales = xbknamRepository.findAll().stream()
                    .filter(s -> regionCodigo.equals(s.getXncdmr()))
                    .collect(Collectors.toList());

            if (sucursales.isEmpty()) {
                logger.warn("No se encontraron sucursales para la región: {}", regionCodigo);
                return null;
            }

            // Creamos el registro de resultados
            Atvffcaa resultado = new Atvffcaa();
            resultado.setAaanos(ano);
            resultado.setAaregion(regionCodigo);
            resultado.setAanombre(region.getXmnmr());

            // Inicializamos los valores mensuales
            inicializarValoresMensuales(resultado);

            // Procesamos cada mes
            int totalMeses = 0;
            int sumaValores = 0;

            LocalDate fechaActual = LocalDate.now();
            int mesActual = fechaActual.getMonthValue();
            int anoActual = fechaActual.getYear();

            // Determinamos hasta qué mes procesar
            int mesLimite = (anoActual == ano) ? mesActual : 12;

            for (int mes = 1; mes <= mesLimite; mes++) {
                int valorMes = calcularValorMes(region, sucursales, ano, mes);
                asignarValorMes(resultado, mes, valorMes);

                if (valorMes > 0) {
                    sumaValores += valorMes;
                    totalMeses++;
                }
            }

            // Calculamos el promedio
            int promedio = (totalMeses > 0) ? sumaValores / totalMeses : 0;
            resultado.setAaproma(promedio);

            return resultado;

        } catch (Exception e) {
            logger.error("Error al procesar región {}: {}", region.getXmcdmr(), e.getMessage(), e);
            return null;
        }
    }

    // Inicializa todos los valores mensuales a cero
    private void inicializarValoresMensuales(Atvffcaa resultado) {
        resultado.setAaenero(0);
        resultado.setAafeb(0);
        resultado.setAamarzo(0);
        resultado.setAaabril(0);
        resultado.setAamayo(0);
        resultado.setAajunio(0);
        resultado.setAajulio(0);
        resultado.setAaagosto(0);
        resultado.setAasep(0);
        resultado.setAaoctubre(0);
        resultado.setAanov(0);
        resultado.setAadic(0);
    }

    // Asigna el valor calculado al mes correspondiente
    private void asignarValorMes(Atvffcaa resultado, int mes, int valor) {
        switch (mes) {
            case 1:
                resultado.setAaenero(valor);
                break;
            case 2:
                resultado.setAafeb(valor);
                break;
            case 3:
                resultado.setAamarzo(valor);
                break;
            case 4:
                resultado.setAaabril(valor);
                break;
            case 5:
                resultado.setAamayo(valor);
                break;
            case 6:
                resultado.setAajunio(valor);
                break;
            case 7:
                resultado.setAajulio(valor);
                break;
            case 8:
                resultado.setAaagosto(valor);
                break;
            case 9:
                resultado.setAasep(valor);
                break;
            case 10:
                resultado.setAaoctubre(valor);
                break;
            case 11:
                resultado.setAanov(valor);
                break;
            case 12:
                resultado.setAadic(valor);
                break;
            default:
                // No hacer nada para meses fuera de rango
                break;
        }
    }

    // Calcula el valor de cumplimiento para un mes específico
    private int calcularValorMes(Xmgreg region, List sucursales, Integer ano, int mes) {
        try {
            int totalProductos = 0;
            int totalCumplimiento = 0;

            // Obtenemos los productos
            List<AtvffPdo> productos = atvffpdoRepository.findAll();

            for (AtvffPdo producto : productos) {
                String codigoOperacion = producto.getXpcopr();
                String codigoDocumento = producto.getXpcodo();

                // Verificamos la frecuencia del producto
                Optional<AtvffFre> frecuenciaOpt = atvfffreRepository.findById_fxCoprAndId_fxCodo(
                        codigoOperacion, codigoDocumento);

                if (frecuenciaOpt.isEmpty()) {
                    continue;
                }

                AtvffFre frecuencia = frecuenciaOpt.get();
                String tipoFrecuencia = frecuencia.getFxFrar();

                // Verificamos si el mes actual aplica según la frecuencia
                if (!aplicaMesSegunFrecuencia(mes, tipoFrecuencia)) {
                    continue;
                }

                totalProductos++;

                // Calculamos el cumplimiento para cada sucursal
                int cumplimientoProducto = calcularCumplimientoProducto(
                        sucursales, codigoOperacion, codigoDocumento, ano, mes, frecuencia);

                totalCumplimiento += cumplimientoProducto;
            }

            // Calculamos el promedio general de cumplimiento para el mes
            return (totalProductos > 0) ? totalCumplimiento / totalProductos : 0;

        } catch (Exception e) {
            logger.error("Error al calcular valor para el mes {}: {}", mes, e.getMessage(), e);
            return 0;
        }
    }

    // Verifica si un mes aplica según el tipo de frecuencia
    private boolean aplicaMesSegunFrecuencia(int mes, String tipoFrecuencia) {
        switch (tipoFrecuencia) {
            case "M": // Mensual
                return true;
            case "T": // Trimestral
                return mes == 3 || mes == 6 || mes == 9 || mes == 12;
            case "S": // Semestral
                return mes == 6 || mes == 12;
            case "A": // Anual
                return mes == 12;
            default:
                return false;
        }
    }

    // Calcula el cumplimiento para un producto específico en todas las sucursales
    private int calcularCumplimientoProducto(
            List<Xbknam> sucursales,
            String codigoOperacion,
            String codigoDocumento,
            Integer ano,
            int mes,
            AtvffFre frecuencia) {

        int cumplimientoTotal = 0;
        int totalSucursales = sucursales.size();

        if (totalSucursales == 0) {
            return 0;
        }

        for (Xbknam sucursal : sucursales) {
            try {
                int codigoSucursal = 0;
                if (sucursal.getXnnmky() >= Integer.MIN_VALUE && sucursal.getXnnmky() <= Integer.MAX_VALUE) {
                    codigoSucursal = (int)(long)sucursal.getXnnmky();
                }

                // Verificamos los arqueos de la sucursal para este producto
                boolean cumple = verificarCumplimientoArqueo(
                        codigoSucursal, codigoOperacion, codigoDocumento, ano, mes, frecuencia);

                if (cumple) {
                    cumplimientoTotal += 100; // 100% de cumplimiento
                }
            } catch (NumberFormatException e) {
                logger.warn("Error al convertir código de sucursal: {}", e.getMessage());
            }
        }

        // Calculamos el promedio de cumplimiento
        return totalSucursales > 0 ? cumplimientoTotal / totalSucursales : 0;
    }

    private boolean verificarCumplimientoArqueo(
            Integer aqcdsu,
            String codigoOperacion,
            String codigoDocumento,
            Integer ano,
            int mes,
            AtvffFre frecuencia) {

        try {
            // Obtenemos la fecha de arqueo esperada para el mes
            Short mesShort = (short) mes;
            Optional<Atvffmd> fechaArqueoOpt = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(
                    codigoOperacion, codigoDocumento, ano, mesShort);

            if (fechaArqueoOpt.isEmpty()) {
                return false;
            }

            Atvffmd fechaArqueo = fechaArqueoOpt.get();
            int diaArqueo = Integer.parseInt(fechaArqueo.getMddia().toString());
            int rangoArqueo = fechaArqueo.getMdrang1();

            // Calculamos el rango de días permitidos para el arqueo
            int diaInicio = Math.max(1, diaArqueo - rangoArqueo);
            int diaFin = diaArqueo;

            // Buscamos arqueos realizados en el rango de fechas
            String fechaInicio = LocalDate.of(ano, mes, diaInicio).toString();
            String fechaFin = LocalDate.of(ano, mes, diaFin).toString();

            // Consultamos los arqueos de la sucursal para este producto en el rango de fechas
            List<Atvffarq> arqueos = atvffarqRepository.findByAqcdsuAndAqcoprAndAqcodoAndAqfearBetween(
                    aqcdsu, codigoOperacion, codigoDocumento, fechaInicio, fechaFin);

            // Verificamos si hay al menos un arqueo cuadrado ('C')
            return arqueos.stream().anyMatch(arqueo -> "C".equals(arqueo.getAqres()));

        } catch (Exception e) {
            logger.error("Error al verificar cumplimiento de arqueo: {}", e.getMessage(), e);
            return false;
        }
    }

    // Método auxiliar para obtener el último día del mes
    private int obtenerUltimoDiaMes(int ano, int mes) {
        return YearMonth.of(ano, mes).lengthOfMonth();
    }

}
