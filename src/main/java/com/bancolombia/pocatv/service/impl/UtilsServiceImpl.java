package com.bancolombia.pocatv.service.impl;

import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.FechaMesAnioRequestDTO;
import com.bancolombia.pocatv.dto.FechaResponse;
import com.bancolombia.pocatv.dto.FechaTransformResponseDTO;
import com.bancolombia.pocatv.service.UtilsService;
import com.bancolombia.pocatv.utils.ValidateFechaUtil;
import com.bancolombia.pocatv.utils.DataTransformationUtil;

@Service
public class UtilsServiceImpl implements UtilsService{

	@Override
	public FechaResponse procesarFecha(FechaMesAnioRequestDTO request) {
		FechaResponse response = new FechaResponse();
        
        // Utilizar la utilidad para validar mes y año.
        String mensajeValidacion = ValidateFechaUtil.validarMesYAnio(request.getMes(), request.getAnio());
        if (!mensajeValidacion.isEmpty()) {
            response.setMensajeError(mensajeValidacion);
            return response;
        }
        
        // Aquí se puede agregar la lógica adicional en caso de ser válida.
        response.setResultado("Fecha válida. Procesado correctamente para el usuario " + request.getUsuario());
        
        return response;
    }

	@Override
	public FechaTransformResponseDTO restarUnDia(String fecha) {
		return DataTransformationUtil.restarUnDia(fecha);
	}
	

}
