package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Atvffcharp;
import com.bancolombia.pocatv.model.Atvffcharq;
import com.bancolombia.pocatv.repository.AtvffcharpRepository;
import com.bancolombia.pocatv.repository.AtvffcharqRepository;
import com.bancolombia.pocatv.service.AtvrchplaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AtvrchplaaServiceImpl implements AtvrchplaaService {

    @Autowired
    private AtvffcharpRepository atvffcharpRepository;

    @Autowired
    private AtvffcharqRepository arqueoRepository;

    @Override
    public void procesarArqueos() {
        try {
            // Leer todos los registros de la tabla de entrada
            List<Atvffcharp> detalles = atvffcharpRepository.findAll();

            // Procesar cada registro
            for (Atvffcharp detalle : detalles) {
                // Crear una nueva instancia de la entidad de salida
                Atvffcharq arqueo = new Atvffcharq();

                // Mapear los datos del registro de entrada al registro de salida
                arqueo.setFechaArqueo(extractFecha(detalle.getPlano())); // Asignar fecha
                arqueo.setSucursal(extractSucursal(detalle.getPlano())); // Asignar sucursal
                arqueo.setCodigoSucursal(extractCodigoSucursal(detalle.getPlano())); // Asignar código de sucursal
                arqueo.setResponsableCustodia(extractResponsableCustodia(detalle.getPlano())); // Asignar responsable de custodia
                arqueo.setSaldoFisicoArqueo(extractSaldoFisico(detalle.getPlano())); // Asignar saldo físico
                arqueo.setDiferencia(extractDiferencia(detalle.getPlano())); // Asignar diferencia
                arqueo.setObservaciones(extractObservaciones(detalle.getPlano())); // Asignar observaciones
                arqueo.setRqobso(extractObservacionesOtros(detalle.getPlano())); // Asignar observaciones otros

                // Asignar rangos y códigos
                arqueo.setRqrain1(extractRangoInicial(detalle.getPlano(), 1)); // Rango inicial 1
                arqueo.setRqrafn1(extractRangoFinal(detalle.getPlano(), 1)); // Rango final 1
                arqueo.setRqcocu1(extractCodigoCuenta(detalle.getPlano(), 1)); // Código cuenta 1

                // Repetir para los rangos 2 a 13
                arqueo.setRqrain2(extractRangoInicial(detalle.getPlano(), 2));
                arqueo.setRqrafn2(extractRangoFinal(detalle.getPlano(), 2));
                arqueo.setRqcocu2(extractCodigoCuenta(detalle.getPlano(), 2));

                arqueo.setRqrain3(extractRangoInicial(detalle.getPlano(), 3));
                arqueo.setRqrafn3(extractRangoFinal(detalle.getPlano(), 3));
                arqueo.setRqcocu3(extractCodigoCuenta(detalle.getPlano(), 3));

                arqueo.setRqrain4(extractRangoInicial(detalle.getPlano(), 4));
                arqueo.setRqrafn4(extractRangoFinal(detalle.getPlano(), 4));
                arqueo.setRqcocu4(extractCodigoCuenta(detalle.getPlano(), 4));

                arqueo.setRqrain5(extractRangoInicial(detalle.getPlano(), 5));
                arqueo.setRqrafn5(extractRangoFinal(detalle.getPlano(), 5));
                arqueo.setRqcocu5(extractCodigoCuenta(detalle.getPlano(), 5));

                arqueo.setRqrain6(extractRangoInicial(detalle.getPlano(), 6));
                arqueo.setRqrafn6(extractRangoFinal(detalle.getPlano(), 6));
                arqueo.setRqcocu6(extractCodigoCuenta(detalle.getPlano(), 6));

                arqueo.setRqrain7(extractRangoInicial(detalle.getPlano(), 7));
                arqueo.setRqrafn7(extractRangoFinal(detalle.getPlano(), 7));
                arqueo.setRqcocu7(extractCodigoCuenta(detalle.getPlano(), 7));

                arqueo.setRqrain8(extractRangoInicial(detalle.getPlano(), 8));
                arqueo.setRqrafn8(extractRangoFinal(detalle.getPlano(), 8));
                arqueo.setRqcocu8(extractCodigoCuenta(detalle.getPlano(), 8));

                arqueo.setRqrain9(extractRangoInicial(detalle.getPlano(), 9));
                arqueo.setRqrafn9(extractRangoFinal(detalle.getPlano(), 9));
                arqueo.setRqcocu9(extractCodigoCuenta(detalle.getPlano(), 9));

                arqueo.setRqrain10(extractRangoInicial(detalle.getPlano(), 10));
                arqueo.setRqrafn10(extractRangoFinal(detalle.getPlano(), 10));
                arqueo.setRqcocu10(extractCodigoCuenta(detalle.getPlano(), 10));

                arqueo.setRqrain11(extractRangoInicial(detalle.getPlano(), 11));
                arqueo.setRqrafn11(extractRangoFinal(detalle.getPlano(), 11));
                arqueo.setRqcocu11(extractCodigoCuenta(detalle.getPlano(), 11));

                arqueo.setRqrain12(extractRangoInicial(detalle.getPlano(), 12));
                arqueo.setRqrafn12(extractRangoFinal(detalle.getPlano(), 12));
                arqueo.setRqcocu12(extractCodigoCuenta(detalle.getPlano(), 12));

                arqueo.setRqrain13(extractRangoInicial(detalle.getPlano(), 13));
                arqueo.setRqrafn13(extractRangoFinal(detalle.getPlano(), 13));
                arqueo.setRqcocu13(extractCodigoCuenta(detalle.getPlano(), 13));

                // Asignar campos de usuario
                arqueo.setRqusu1(""); // Valor en blanco o extraído
                arqueo.setRqusu2(""); // Valor en blanco o extraído
                arqueo.setRqusu3(""); // Valor en blanco o extraído
                arqueo.setRqusu4(BigDecimal.ZERO); // Valor por defecto
                arqueo.setRqusu5(BigDecimal.ZERO); // Valor por defecto

                // Guardar el registro procesado en la tabla de salida
                arqueoRepository.save(arqueo);
            }
        } catch (Exception ex) {
            System.out.println("Ocurrió un error al procesar los registros: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String extractRangoInicial(String plano, int rango) {
        int inicio = 672 + (rango - 1) * 16;
        int fin = inicio + 16;
        return plano.substring(inicio, fin).trim(); // Ajustar las posiciones según el formato RPG
    }

    private String extractRangoFinal(String plano, int rango) {
        int inicio = 688 + (rango - 1) * 16;
        int fin = inicio + 16;
        return plano.substring(inicio, fin).trim(); // Ajustar las posiciones según el formato RPG
    }

    private String extractCodigoCuenta(String plano, int rango) {
        int inicio = 704 + (rango - 1) * 16;
        int fin = inicio + 16;
        return plano.substring(inicio, fin).trim(); // Ajustar las posiciones según el formato RPG
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
        String saldoString = plano.substring(138, 153).trim(); // Extraer caracteres 139-153
        saldoString = saldoString.replaceAll("\\s+", ""); // Eliminar espacios en blanco
        try {
            return Double.parseDouble(saldoString);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el saldo a Double: " + e.getMessage());
            return 0.0; // Valor por defecto para evitar null
        }
    }

    private Double extractDiferencia(String plano) {
        String diferenciaString = plano.substring(155, 169).trim(); // Extraer caracteres 155-169
        diferenciaString = diferenciaString.replaceAll("\\s+", ""); // Eliminar espacios en blanco
        try {
            return Double.parseDouble(diferenciaString);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir la diferencia a Double: " + e.getMessage());
            return 0.0; // Valor por defecto para evitar null
        }
    }

    private String extractObservaciones(String plano) {
        String observaciones = plano.substring(172, 421).trim(); // Extraer caracteres 172-421
        return observaciones.isEmpty() ? "Sin Observaciones" : observaciones; // Valor por defecto
    }

    private String extractObservacionesOtros(String plano) {
        // Implementa la lógica para extraer "observaciones otros" si es necesario
        return ""; // Cambia esto por la lógica real
    }
}