package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.model.AtvffmdId;
import com.bancolombia.pocatv.repository.AtvffmdRepository;
import com.bancolombia.pocatv.repository.ProductoRepository;
import com.bancolombia.pocatv.service.AtvrmmeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AtvrmmeServiceImpl implements AtvrmmeService {

    private static final Logger logger = LoggerFactory.getLogger(AtvrmmeServiceImpl.class);

    @Autowired
    private AtvffmdRepository atvffmdRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Object> consultarPorProductoDocumentoAno(String codpro, String coddoc, Integer ano) {
        logger.info("Consultando registros para producto: {}, documento: {}, año: {}", codpro, coddoc, ano);

        if (codpro == null || codpro.trim().isEmpty() || coddoc == null || coddoc.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de producto y documento son obligatorios");
        }

        return atvffmdRepository.findByMdcoprAndMdcodoAndMdano(codpro, coddoc, ano);
    }

    @Override
    public Map<String, Object> obtenerDiasPorMes(String codpro, String coddoc, Integer ano) {
        logger.info("Obteniendo días por mes para producto: {}, documento: {}, año: {}", codpro, coddoc, ano);

        List<Object> registros = consultarPorProductoDocumentoAno(codpro, coddoc, ano);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("codpro", codpro);
        resultado.put("coddoc", coddoc);
        resultado.put("ano", ano);

        Map<Integer, Integer> diasPorMes = new HashMap<>();
        Integer rango1 = null;
        Integer rango2 = null;

        for (Object obj : registros) {
            Atvffmd registro = (Atvffmd) obj;
            diasPorMes.put(registro.getMdmes().intValue(), registro.getMddia().intValue());

            // Tomamos el rango del primer registro (todos deberían tener el mismo)
            if (rango1 == null) {
                rango1 = registro.getMdrang1() != null ? registro.getMdrang1().intValue() : null;
                rango2 = registro.getMdrang2() != null ? registro.getMdrang2().intValue() : null;
            }
        }

        resultado.put("diasPorMes", diasPorMes);
        resultado.put("rango1", rango1 != null ? rango1 : 0);
        resultado.put("rango2", rango2 != null ? rango2 : 0);

        return resultado;
    }

    @Override
    @Transactional
    public void actualizarDiasMes(String codpro, String coddoc, Integer ano,
                                  Map<Integer, Integer> diasPorMes, Integer rango1, Integer rango2) {
        logger.info("Actualizando días por mes para producto: {}, documento: {}, año: {}", codpro, coddoc, ano);

        if (codpro == null || codpro.trim().isEmpty() || coddoc == null || coddoc.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de producto y documento son obligatorios");
        }

        // Validar días por mes
        validarDiasPorMes(diasPorMes, ano);

        // Actualizar o crear registros para cada mes
        for (Map.Entry<Integer, Integer> entry : diasPorMes.entrySet()) {
            Integer mes = entry.getKey();
            Integer dia = entry.getValue();

            AtvffmdId id = new AtvffmdId(codpro, coddoc, ano, mes != null ? mes.shortValue() : null);
            Optional<Atvffmd> registroExistente = atvffmdRepository.findById(id);

            Atvffmd registro;
            if (registroExistente.isPresent()) {
                registro = registroExistente.get();
                registro.setMddia(dia != null ? dia.shortValue() : null);
                registro.setMdrang1(rango1 != null ? rango1.shortValue() : null);
                registro.setMdrang2(rango2 != null ? rango2.shortValue() : null);
                logger.debug("Actualizando registro existente para mes: {}", mes);
            } else {
                registro = new Atvffmd();
                registro.setMdcopr(codpro);
                registro.setMdcodo(coddoc);
                registro.setMdano(ano);
                registro.setMdmes(mes != null ? mes.shortValue() : null);
                registro.setMddia(dia != null ? dia.shortValue() : null);
                registro.setMdrang1(rango1 != null ? rango1.shortValue() : null);
                registro.setMdrang2(rango2 != null ? rango2.shortValue() : null);
                logger.debug("Creando nuevo registro para mes: {}", mes);
            }

            atvffmdRepository.save(registro);
        }

        logger.info("Actualización completada exitosamente");
    }

    @Override
    @Transactional
    public void duplicarParaTodosProductos(String codproOrigen, String coddocOrigen, Integer ano,
                                           Map<Integer, Integer> diasPorMes, Integer rango1, Integer rango2) {
        logger.info("Duplicando configuración para todos los productos desde producto: {}, documento: {}, año: {}",
                codproOrigen, coddocOrigen, ano);

        // Validar días por mes
        validarDiasPorMes(diasPorMes, ano);

        // Obtener todos los productos distintos
        List<Object[]> productos = productoRepository.findAllProductosDocumentos();

        int contadorActualizados = 0;

        for (Object[] producto : productos) {
            String codpro = (String) producto[0];
            String coddoc = (String) producto[1];

            // No duplicar para el producto origen
            if (codpro.equals(codproOrigen) && coddoc.equals(coddocOrigen)) {
                continue;
            }

            try {
                actualizarDiasMes(codpro, coddoc, ano, diasPorMes, rango1, rango2);
                contadorActualizados++;
            } catch (Exception e) {
                logger.error("Error al duplicar para producto: {}, documento: {}: {}",
                        codpro, coddoc, e.getMessage(), e);
                // Continuamos con el siguiente producto
            }
        }

        logger.info("Duplicación completada. Productos actualizados: {}", contadorActualizados);
    }

    @Override
    public boolean validarAno(Integer ano) {
        logger.info("Validando año: {}", ano);

        if (ano == null) {
            logger.warn("Año nulo");
            return false;
        }

        int anoActual = LocalDate.now().getYear();

        // Validar que el año esté en el rango permitido y no sea posterior al actual
        boolean esValido = ano >= 2000 && ano <= 3000 && ano <= anoActual;

        logger.info("Año {} es válido: {}", ano, esValido);
        return esValido;
    }

    /**
     * Valida que los días por mes sean correctos según el mes y si el año es bisiesto
     * @param diasPorMes Mapa con los días por mes
     * @param ano Año para determinar si es bisiesto
     */
    private void validarDiasPorMes(Map<Integer, Integer> diasPorMes, Integer ano) {
        boolean esBisiesto = (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
        logger.debug("Año {}: {}", ano, esBisiesto ? "es bisiesto" : "no es bisiesto");

        for (Map.Entry<Integer, Integer> entry : diasPorMes.entrySet()) {
            Integer mes = entry.getKey();
            Integer dia = entry.getValue();

            if (mes < 1 || mes > 12) {
                throw new IllegalArgumentException("Mes inválido: " + mes);
            }

            // Validar días según el mes
            switch (mes) {
                case 2: // Febrero
                    int maxDias = esBisiesto ? 29 : 28;
                    if (dia < 1 || dia > maxDias) {
                        throw new IllegalArgumentException("Día inválido para febrero: " + dia +
                                ". Máximo permitido: " + maxDias);
                    }
                    break;
                case 4: case 6: case 9: case 11: // Meses de 30 días
                    if (dia < 1 || dia > 30) {
                        throw new IllegalArgumentException("Día inválido para el mes " + mes + ": " + dia +
                                ". Máximo permitido: 30");
                    }
                    break;
                default: // Meses de 31 días
                    if (dia < 1 || dia > 31) {
                        throw new IllegalArgumentException("Día inválido para el mes " + mes + ": " + dia +
                                ". Máximo permitido: 31");
                    }
                    break;
            }
        }
    }
}