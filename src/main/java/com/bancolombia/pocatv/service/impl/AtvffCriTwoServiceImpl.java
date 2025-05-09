package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.AtvffCri;
import com.bancolombia.pocatv.model.AtvffCriId;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffcriRepository;
import com.bancolombia.pocatv.service.AtvffCriTwoService;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Service
public class AtvffCriTwoServiceImpl implements AtvffCriTwoService {
    
	@Autowired
    private AtvffarqRepository atvffarqRepository;

    @Autowired
    private AtvffPdsRepository atvffPdsRepository;

    @Autowired
    private AtvffcriRepository atvffcriRepository;

    @Override
    @Transactional
    public int generarArchivoRangosInconsistentes(int mes, int anno, int dia) {
    	
    	// Convertir año y mes a cadenas para la consulta
        String year = String.valueOf(anno);
        String month = String.format("%02d", mes);
        // Obtener registros de arqueos para el mes y año especificados
        List<Atvffarq> arqueos = atvffarqRepository.findByYearAndMonthAndResult(year, month);

        if (arqueos.isEmpty()) {
            throw new IllegalArgumentException("No hay registros de arqueos para el mes " + mes + " y año " + anno);
        }

        int registrosProcesados = 0;

        for (Atvffarq arqueo : arqueos) {
            // Calcular el número de rangos con información
            int diferencia = contarRangosConInformacion(arqueo);

            // Verificar si existe el registro en la tabla de sucursal/producto/documento
            boolean existeEnPds = atvffPdsRepository.existsByXscosuAndXscoprAndXscodo(
                    arqueo.getAqcdsu(), arqueo.getAqcopr(), arqueo.getAqcodo());

            if (existeEnPds) {
                // Crear registro de inconsistencia
                AtvffCri inconsistencia = new AtvffCri();
                AtvffCriId inconsistenciaId = new AtvffCriId();

                // Asignar valores a la clave primaria compuesta
                inconsistenciaId.setCrano(anno);
                inconsistenciaId.setCrmes(mes);
                inconsistenciaId.setCrcodsuc(arqueo.getAqcdsu());
                inconsistenciaId.setCrcopr(arqueo.getAqcopr());
                inconsistenciaId.setCrcodo(arqueo.getAqcodo());
                
                LocalDate fecha = LocalDate.parse(arqueo.getAqfear());
                inconsistenciaId.setCrfear(fecha);

                // Asociar la clave primaria a la entidad
                inconsistencia.setId(inconsistenciaId);

                // Asignar valores a los demás atributos
                inconsistencia.setCrcon(dia > 0 ? dia : LocalDate.now().getDayOfMonth());
                inconsistencia.setCrnomsuc(arqueo.getAqsuc());
                inconsistencia.setCrdifer(BigDecimal.valueOf(diferencia));
                inconsistencia.setCrcedan(arqueo.getAqcedan());
                inconsistencia.setCrres(arqueo.getAqres());
                inconsistencia.setCrdif(arqueo.getAqdif());

                // Verificar si ya existe el registro
                boolean registroExiste = atvffcriRepository.findByIdCranoAndIdCrmesAndCrconAndIdCrcodsucAndIdCrcoprAndIdCrcodo(
                        inconsistenciaId.getCrano(),
                        inconsistenciaId.getCrmes(),
                        inconsistencia.getCrcon(),
                        inconsistenciaId.getCrcodsuc(),
                        inconsistenciaId.getCrcopr(),
                        inconsistenciaId.getCrcodo()
                ).isPresent();

                // Guardar solo si no existe o si el día coincide con el día actual
                if (!registroExiste || inconsistencia.getCrcon() == LocalDate.now().getDayOfMonth()) {
                    atvffcriRepository.save(inconsistencia);
                    registrosProcesados++;
                }
            }
        }

        return registrosProcesados;
    }

    /**
     * Cuenta cuántos campos de rango (AQRAIN1-AQRAIN14) tienen información
     * @param arqueo Registro de arqueo a analizar
     * @return Número de rangos con información
     */
    private int contarRangosConInformacion(Atvffarq arqueo) {
        int contador = 0;

        if (arqueo.getAqrain1() != null && !arqueo.getAqrain1().trim().isEmpty()) contador++;
        if (arqueo.getAqrain2() != null && !arqueo.getAqrain2().trim().isEmpty()) contador++;
        if (arqueo.getAqrain3() != null && !arqueo.getAqrain3().trim().isEmpty()) contador++;
        if (arqueo.getAqrain4() != null && !arqueo.getAqrain4().trim().isEmpty()) contador++;
        if (arqueo.getAqrain5() != null && !arqueo.getAqrain5().trim().isEmpty()) contador++;
        if (arqueo.getAqrain6() != null && !arqueo.getAqrain6().trim().isEmpty()) contador++;
        if (arqueo.getAqrain7() != null && !arqueo.getAqrain7().trim().isEmpty()) contador++;
        if (arqueo.getAqrain8() != null && !arqueo.getAqrain8().trim().isEmpty()) contador++;
        if (arqueo.getAqrain9() != null && !arqueo.getAqrain9().trim().isEmpty()) contador++;
        if (arqueo.getAqrain10() != null && !arqueo.getAqrain10().trim().isEmpty()) contador++;
        if (arqueo.getAqrain11() != null && !arqueo.getAqrain11().trim().isEmpty()) contador++;
        if (arqueo.getAqrain12() != null && !arqueo.getAqrain12().trim().isEmpty()) contador++;
        if (arqueo.getAqrain13() != null && !arqueo.getAqrain13().trim().isEmpty()) contador++;
        if (arqueo.getAqrain14() != null && !arqueo.getAqrain14().trim().isEmpty()) contador++;

        return contador;
    }
	
}
