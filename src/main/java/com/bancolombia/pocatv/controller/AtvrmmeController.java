package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.service.AtvrmmeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atvrmme")
public class AtvrmmeController {

    private static final Logger logger = LoggerFactory.getLogger(AtvrmmeController.class);

    @Autowired
    private AtvrmmeService atvrmmeService;

    /**
     * Endpoint para validar si un año es válido según las reglas de negocio
     * @param ano Año a validar
     * @return Respuesta con indicador de validez
     */
    @GetMapping("/validar-ano/{ano}")
    public ResponseEntity<Map<String, Object>> validarAno(@PathVariable Integer ano) {
        logger.info("Validando año: {}", ano);
        Map<String, Object> respuesta = new HashMap<>();

        try {
            boolean esValido = atvrmmeService.validarAno(ano);
            respuesta.put("valido", esValido);

            if (!esValido) {
                respuesta.put("mensaje", "Año inválido o posterior al actual");
            }

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error al validar año: {}", e.getMessage(), e);
            respuesta.put("valido", false);
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Endpoint para obtener los días de mantenimiento por mes para un producto, documento y año específicos
     * @param codpro Código de producto
     * @param coddoc Código de documento
     * @param ano Año
     * @return Datos de mantenimiento por mes
     */
    @GetMapping("/{codpro}/{coddoc}/{ano}")
    public ResponseEntity<Map<String, Object>> obtenerDiasPorMes(
            @PathVariable String codpro,
            @PathVariable String coddoc,
            @PathVariable Integer ano) {

        logger.info("Obteniendo días por mes para producto: {}, documento: {}, año: {}", codpro, coddoc, ano);

        try {
            // Validar año
            if (!atvrmmeService.validarAno(ano)) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Año inválido o posterior al actual");
                return ResponseEntity.badRequest().body(error);
            }

            Map<String, Object> resultado = atvrmmeService.obtenerDiasPorMes(codpro, coddoc, ano);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación: {}", e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Error al obtener días por mes: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Endpoint para actualizar los días de mantenimiento por mes
     * @param codpro Código de producto
     * @param coddoc Código de documento
     * @param ano Año
     * @param datos Datos a actualizar
     * @return Respuesta con resultado de la operación
     */
    @PostMapping("/{codpro}/{coddoc}/{ano}")
    public ResponseEntity<Map<String, Object>> actualizarDiasMes(
            @PathVariable String codpro,
            @PathVariable String coddoc,
            @PathVariable Integer ano,
            @RequestBody Map<String, Object> datos) {

        logger.info("Actualizando días por mes para producto: {}, documento: {}, año: {}", codpro, coddoc, ano);

        try {
            // Validar año
            if (!atvrmmeService.validarAno(ano)) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Año inválido o posterior al actual");
                return ResponseEntity.badRequest().body(error);
            }

            @SuppressWarnings("unchecked")
            Map<String, Integer> diasPorMesStr = (Map<String, Integer>) datos.get("diasPorMes");

            // Convertir claves de String a Integer
            Map<Integer, Integer> diasPorMes = new HashMap<>();
            for (Map.Entry<String, Integer> entry : diasPorMesStr.entrySet()) {
                diasPorMes.put(Integer.parseInt(entry.getKey()), entry.getValue());
            }

            Integer rango1 = (Integer) datos.get("rango1");
            Integer rango2 = (Integer) datos.get("rango2");

            atvrmmeService.actualizarDiasMes(codpro, coddoc, ano, diasPorMes, rango1, rango2);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Datos actualizados correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación: {}", e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Error al actualizar días por mes: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Endpoint para duplicar la configuración para todos los productos
     * @param codpro Código de producto origen
     * @param coddoc Código de documento origen
     * @param ano Año
     * @param datos Datos a duplicar
     * @return Respuesta con resultado de la operación
     */
    @PostMapping("/duplicar/{codpro}/{coddoc}/{ano}")
    public ResponseEntity<Map<String, Object>> duplicarParaTodosProductos(
            @PathVariable String codpro,
            @PathVariable String coddoc,
            @PathVariable Integer ano,
            @RequestBody Map<String, Object> datos) {

        logger.info("Duplicando configuración para todos los productos desde producto: {}, documento: {}, año: {}",
                codpro, coddoc, ano);

        try {
            // Validar año
            if (!atvrmmeService.validarAno(ano)) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Año inválido o posterior al actual");
                return ResponseEntity.badRequest().body(error);
            }

            @SuppressWarnings("unchecked")
            Map<String, Integer> diasPorMesStr = (Map<String, Integer>) datos.get("diasPorMes");

            // Convertir claves de String a Integer
            Map<Integer, Integer> diasPorMes = new HashMap<>();
            for (Map.Entry<String, Integer> entry : diasPorMesStr.entrySet()) {
                diasPorMes.put(Integer.parseInt(entry.getKey()), entry.getValue());
            }

            Integer rango1 = (Integer) datos.get("rango1");
            Integer rango2 = (Integer) datos.get("rango2");

            atvrmmeService.duplicarParaTodosProductos(codpro, coddoc, ano, diasPorMes, rango1, rango2);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Datos duplicados correctamente para todos los productos");
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación: {}", e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Error al duplicar configuración: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}