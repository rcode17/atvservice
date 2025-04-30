package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.ResumenMetricasDTO;
import com.bancolombia.pocatv.dto.SucursalMetricaDTO;
import com.bancolombia.pocatv.model.Atvffega;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.repository.AtvffegaRepository;
import com.bancolombia.pocatv.service.AtvffegaService;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class AtvffegaServiceImpl implements AtvffegaService{
	
	@Autowired
	private AtvffegaRepository atvffegaRepository;

	@Autowired
	private AtvffUserRepository atvffuserRepository;
	
	public ResumenMetricasDTO obtenerMetricasSucursales(String usuario) {
	    // Obtener usuario
		AtvffUser user = atvffuserRepository.findByXuUser(usuario);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + usuario);
        }
	    
	    // Obtener mes y año actual
	    LocalDate fecha = LocalDate.now();
	    Integer mes = (int) (fecha.getMonthValue() - 1); // Mes anterior como en RPG
	    Integer anio = (int) fecha.getYear();
	    
	    // Obtener datos según filtro
	    List<Atvffega> datos;
	    if (user != null) {
	        datos = atvffegaRepository.findByMesAnioAndUsuario(mes, anio, usuario);
	    } else {
	        datos = atvffegaRepository.findByMesAndAnio(mes, anio);
	    }
	    
	    if (datos.isEmpty()) {
	        throw new IllegalArgumentException("No hay registros con datos válidos para los parámetros proporcionados.");
	    }
	    
	    // Filtrar datos según dominio y otras condiciones
	    List<Atvffega> datosFiltrados = datos.stream()
	            .filter(d -> !"1".equals(d.getEgind07()))
	            .filter(d -> !d.getEgnombre().startsWith("REGION") && !d.getEgnombre().startsWith("CONSOLIDADORA"))
	            .collect(Collectors.toList());
	    
	    // Convertir a DTOs
	    List<SucursalMetricaDTO> sucursales = datosFiltrados.stream()
	            .map(d -> new SucursalMetricaDTO(
	                    d.getEgsucursal(),
	                    d.getEgnombre(),
	                    d.getEgcump11(),
	                    d.getEgcump22(),
	                    d.getEgvarc(),
	                    d.getEginf11(),
	                    d.getEginf22(),
	                    d.getEgvari()
	            ))
	            .collect(Collectors.toList());
	    
	    // Calcular promedios
	    int count = sucursales.size();
	    
	    OptionalDouble promCump11 = sucursales.stream().mapToInt(s -> s.getCump11()).average();
	    OptionalDouble promCump22 = sucursales.stream().mapToInt(s -> s.getCump22()).average();
	    OptionalDouble promInf11 = sucursales.stream().mapToInt(s -> s.getInf11()).average();
	    OptionalDouble promInf22 = sucursales.stream().mapToInt(s -> s.getInf22()).average();
	    
	    // Obtener datos de cabecera (primer registro para simplificar)
	    Atvffega primerRegistro = datos.isEmpty() ? null : datos.get(0);
	    
	    ResumenMetricasDTO resumen = new ResumenMetricasDTO();
	    resumen.setSucursales(sucursales);
	    
	    // Asignar promedios
	    resumen.setTot1(promCump11.isPresent() ? (short)Math.round(promCump11.getAsDouble()) : 0);
	    resumen.setTot2(promCump22.isPresent() ? (short)Math.round(promCump22.getAsDouble()) : 0);
	    resumen.setTot4(promInf11.isPresent() ? (short)Math.round(promInf11.getAsDouble()) : 0);
	    resumen.setTot5(promInf22.isPresent() ? (short)Math.round(promInf22.getAsDouble()) : 0);
	    
	    // Asignar datos de cabecera
        if (primerRegistro != null) {
            resumen.setAntc(primerRegistro.getEgantc());
            resumen.setActc(primerRegistro.getEgactc());
            resumen.setAnti(primerRegistro.getEganti());
            resumen.setActi(primerRegistro.getEgacti());
            resumen.setAnonc(primerRegistro.getEganonc());
            resumen.setAnocc(primerRegistro.getEganocc());
            resumen.setAnoni(primerRegistro.getEganoni());
            resumen.setAnoci(primerRegistro.getEganoci());
        }
        
        return resumen;
    }
}
