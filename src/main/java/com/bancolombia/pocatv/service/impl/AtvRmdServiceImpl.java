package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.service.AtvRmdService;
import com.bancolombia.pocatv.service.AtvrmmeService; // Servicio de ATVRMME
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AtvRmdServiceImpl implements AtvRmdService {

    private static final Logger logger = LoggerFactory.getLogger(AtvRmdServiceImpl.class);

    @Autowired
    private AtvrmmeService atvrmmeService; // Inyectamos el servicio de ATVRMME

    @Override
    public boolean validarAnio(Integer anio) {
        try {
            int anioActual = LocalDate.now().getYear();

            // Validar que el año no sea nulo
            if (anio == null) {
                throw new IllegalArgumentException("El año no puede ser nulo.");
            }

            // Validar que el año esté entre 2000 y 3000
            if (anio < 2000 || anio > 3000) {
                throw new IllegalArgumentException("Fecha inválida. El año debe estar entre 2000 y 3000.");
            }

            // Validar que el año no sea posterior al actual
            if (anio > anioActual) {
                throw new IllegalArgumentException("Año posterior al actual.");
            }

            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Error en la validación del año: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al validar el año: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar la validación del año", e);
        }
    }

    @Override
    public Integer procesarAnio(Integer anio) {
        try {
            // Validar el año
            validarAnio(anio);

            // Llamar al servicio de ATVRMME
            return llamarServicioAtvrmme(anio);
        } catch (IllegalArgumentException e) {
            // Propagamos excepciones de validación
            throw e;
        } catch (Exception e) {
            logger.error("Error al procesar el año: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el año", e);
        }
    }

    private Integer llamarServicioAtvrmme(Integer anio) {
        try {
            // Llamada real al servicio ATVRMME
            logger.info("Llamando al servicio ATVRMME con año: {}", anio);

            // Verificamos si el año es válido según las reglas de ATVRMME
            boolean esValido = atvrmmeService.validarAno(anio);

            if (!esValido) {
                throw new IllegalArgumentException("Año inválido según criterios de ATVRMME");
            }

            // Aquí podríamos realizar otras operaciones necesarias

            return 1; // Equivalente a SALIDA = 1 (éxito)
        } catch (Exception e) {
            logger.error("Error al llamar al servicio ATVRMME: {}", e.getMessage(), e);
            throw new RuntimeException("Error al comunicarse con el servicio ATVRMME", e);
        }
    }
}