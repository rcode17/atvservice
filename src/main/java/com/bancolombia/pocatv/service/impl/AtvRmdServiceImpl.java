package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.service.AtvRmdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AtvRmdServiceImpl implements AtvRmdService{

    private static final Logger logger = LoggerFactory.getLogger(AtvRmdServiceImpl.class);

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

            // Aquí iría la lógica equivalente a la llamada a ATVRMME
            // Simulamos la llamada a otro servicio que podría fallar
            return llamarServicioExterno(anio);
        } catch (IllegalArgumentException e) {
            // Propagamos excepciones de validación
            throw e;
        } catch (Exception e) {
            logger.error("Error al procesar el año: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el año", e);
        }
    }

    private Integer llamarServicioExterno(Integer anio) {
        try {
            // Simulación de llamada a servicio externo (equivalente a CALL ATVRMME)
            logger.info("Llamando a servicio externo con año: {}", anio);
            // Aquí podría ir una llamada a otro servicio o microservicio
            return 1; // Equivalente a SALIDA = 1
        } catch (Exception e) {
            logger.error("Error al llamar al servicio externo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al comunicarse con el sistema externo", e);
        }
    }
	
}
