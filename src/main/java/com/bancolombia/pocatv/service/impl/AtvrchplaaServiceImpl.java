package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvrchplaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class AtvrchplaaServiceImpl implements AtvrchplaaService {

    @Autowired
    private AtvffcharpRepository AtvffcharpRepository;

    @Autowired
    private AtvffcharqRepository arqueoRepository;

    public void procesarArqueos() {
        // Leer todos los registros de la tabla de entrada
        List<Atvffcharp> detalles = AtvffcharpRepository.findAll();

        // Procesar cada registro
        for (Atvffcharp detalle : detalles) {
            // Crear una nueva instancia de la entidad de salida
            Atvffcharq arqueo = new Atvffcharq();

            // Mapear los datos del registro de entrada al registro de salida
            arqueo.setFechaArqueo(extractFecha(detalle.getPlano()));
            arqueo.setSucursal(extractSucursal(detalle.getPlano()));
            arqueo.setCodigoSucursal(extractCodigoSucursal(detalle.getPlano()));
            arqueo.setResponsableCustodia(extractResponsableCustodia(detalle.getPlano()));
            arqueo.setSaldoFisicoArqueo(extractSaldoFisico(detalle.getPlano()));
            arqueo.setDiferencia(extractDiferencia(detalle.getPlano()));
            arqueo.setObservaciones(extractObservaciones(detalle.getPlano()));

            // Guardar el registro procesado en la tabla de salida
            arqueoRepository.save(arqueo);
        }
    }

    // Métodos auxiliares para extraer datos del campo "PLANO"
    private Integer extractFecha(String plano) {
        return Integer.parseInt(plano.substring(0, 8)); // Ejemplo: extraer los primeros 8 caracteres
    }

    private String extractSucursal(String plano) {
        return plano.substring(8, 28).trim(); // Ejemplo: extraer caracteres 9-28
    }

    private Integer extractCodigoSucursal(String plano) {
        return Integer.parseInt(plano.substring(28, 33).trim()); // Ejemplo: extraer caracteres 29-33
    }

    private String extractResponsableCustodia(String plano) {
        return plano.substring(39, 58).trim(); // Ejemplo: extraer caracteres 39-58
    }

    private Double extractSaldoFisico(String plano) {
        return Double.parseDouble(plano.substring(138, 153).trim()); // Ejemplo: extraer caracteres 139-153
    }

    private Double extractDiferencia(String plano) {
        return Double.parseDouble(plano.substring(155, 169).trim()); // Ejemplo: extraer caracteres 155-169
    }

    private String extractObservaciones(String plano) {
        return plano.substring(172, 421).trim(); // Ejemplo: extraer caracteres 172-421
    }

}
