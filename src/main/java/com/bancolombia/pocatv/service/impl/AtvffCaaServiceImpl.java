package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffCaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AtvffCaaServiceImpl implements AtvffCaaService {

    @Autowired
    private AtvffcaaRepository atvffCaaRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffFreRepository atvffFreRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffPdoRepository atvffPdoRepository;
    
    @Autowired
    private AtvffPdsRepository atvffPdsRepository;
    
    @Autowired
    private XbknamRepository xbknamRepository;
    
    @Autowired
    private XmgregRepository xmgregRepository;

    @Override
    @Transactional
    public List<Atvffcaa> generarReporteCumplimiento(Integer ano, String fecha) {
        // Eliminar registros existentes para el año
        eliminarReportePorAno(ano);
        
        // Obtener fecha actual si no se proporciona
        LocalDate fechaActual;
        if (fecha != null && !fecha.isEmpty()) {
            fechaActual = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyyMMdd"));
             } else {
            fechaActual = LocalDate.now();
        }
        
        int year = fechaActual.getYear();
        int month = fechaActual.getMonthValue();
        
        // Obtener todas las regiones
        List<Xmgreg> regiones = xmgregRepository.findAllOrderByXmcdmr();
        List<Atvffcaa> resultados = new ArrayList<>();
        
        for (Xmgreg region : regiones) {
            // Obtener sucursales de la región
            List<Xbknam> sucursales = xbknamRepository.findByXncdmr(region.getXmcdmr());
            
            if (sucursales.isEmpty()) {
                continue;
            }
            
            // Inicializar objeto de resultado
            Atvffcaa resultado = new Atvffcaa();
            resultado.setAaanos(ano);
            resultado.setAaregion(region.getXmcdmr());
            resultado.setAanombre(region.getXmnmr());
            
            // Inicializar valores mensuales
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
            resultado.setAaproma(0);
            
            // Procesar cada mes hasta el mes actual
            int mesLimite = (year == ano) ? month : 12;
            int totalMeses = 0;
            int sumaTotal = 0;
            
            for (int mes = 1; mes <= mesLimite; mes++) {
                int porcentajeCumplimiento = calcularPorcentajeCumplimiento(region.getXmcdmr(), ano, mes, sucursales);
                
                // Asignar porcentaje al mes correspondiente
                switch (mes) {
                    case 1: resultado.setAaenero(porcentajeCumplimiento); break;
                    case 2: resultado.setAafeb(porcentajeCumplimiento); break;
                    case 3: resultado.setAamarzo(porcentajeCumplimiento); break;
                    case 4: resultado.setAaabril(porcentajeCumplimiento); break;
                    case 5: resultado.setAamayo(porcentajeCumplimiento); break;
                    case 6: resultado.setAajunio(porcentajeCumplimiento); break;
                    case 7: resultado.setAajulio(porcentajeCumplimiento); break;
                    case 8: resultado.setAaagosto(porcentajeCumplimiento); break;
                    case 9: resultado.setAasep(porcentajeCumplimiento); break;
                    case 10: resultado.setAaoctubre(porcentajeCumplimiento); break;
                    case 11: resultado.setAanov(porcentajeCumplimiento); break;
                    case 12: resultado.setAadic(porcentajeCumplimiento); break;
                }
                
                if (porcentajeCumplimiento > 0) {
                    sumaTotal += porcentajeCumplimiento;
                    totalMeses++;
                }
            }
            
            // Calcular promedio
            if (totalMeses > 0) {
                resultado.setAaproma(sumaTotal / totalMeses);
            }
            
            // Guardar resultado
            atvffCaaRepository.save(resultado);
            resultados.add(resultado);
        }
        
        return resultados;
    }
    
	private int calcularPorcentajeCumplimiento(String regionId, Integer ano, int mes, List<Xbknam> sucursales) {
		int totalArqueosEjecutados = 0;
		int totalArqueosRequeridos = 0;
		int totalProductos = 0;

		// Obtener todos los productos
		List<AtvffPdo> productos = atvffPdoRepository.findAll();

		for (AtvffPdo producto : productos) {
			String copr = producto.getXpcopr();
			String codo = producto.getXpcodo();

			// Obtener frecuencia del producto
			Optional<AtvffFre> frecuenciaOpt = atvffFreRepository.findById_fxCoprAndId_fxCodo(copr, codo);
			if (frecuenciaOpt.isEmpty())
				continue;

			AtvffFre frecuencia = frecuenciaOpt.get();

			// Obtener sucursales que tienen este producto
			List<AtvffPds> sucursalesProducto = atvffPdsRepository.findByXscoprAndXscodo(copr, codo);
			  if (sucursalesProducto.isEmpty()) continue;

			for (AtvffPds sucursalProducto : sucursalesProducto) {

				// Verificar si la sucursal pertenece a la región
				boolean sucursalEnRegion = false;
				Integer sucursalId = sucursalProducto.getXscosu();

				for (Xbknam sucursal : sucursales) {
					if (sucursal.getXnnmky().equals(sucursalId)) {
						sucursalEnRegion = true;
						break;
					}
				}

				if (!sucursalEnRegion)
					continue;

				// Determinar arqueos requeridos según frecuencia
				int arqueosRequeridos = determinarArqueosRequeridos(frecuencia.getFxFrar(), mes);
				if (arqueosRequeridos == 0)
					continue;

				totalArqueosRequeridos += arqueosRequeridos;
				totalProductos++;

				// Contar arqueos ejecutados
				List<Atvffarq> arqueos = atvffarqRepository.findBySucursalAndCoprAndCodo(sucursalId, copr, codo);

				int arqueosEjecutados = contarArqueosEjecutados(arqueos, ano, mes);
				totalArqueosEjecutados += arqueosEjecutados;
			}
		}

		// Calcular porcentaje de cumplimiento
		if (totalArqueosRequeridos > 0) {
			return (totalArqueosEjecutados * 100) / totalArqueosRequeridos;
		}

		return 0;

	}
    
    private int determinarArqueosRequeridos(String frecuencia, int mes) {
        switch (frecuencia) {
            case "M": // Mensual
                return 1;
            case "T": // Trimestral
                return (mes % 3 == 0) ? 1 : 0;
            case "S": // Semestral
                return (mes == 6 || mes == 12) ? 1 : 0;
            case "A": // Anual
                return (mes == 12) ? 1 : 0;
            default:
                return 0;
        }
    }
    
    private int contarArqueosEjecutados(List<Atvffarq> arqueos, Integer ano, int mes) {
        int count = 0;
        String anoStr = String.valueOf(ano);
        String mesStr = String.format("%02d", mes);
        
        for (Atvffarq arqueo : arqueos) {
            String fecha = arqueo.getAqfear();
            if (fecha.startsWith(anoStr) && fecha.substring(5, 7).equals(mesStr)) {
                count++;
                
                // Verificar si está cuadrado
                if ("C".equals(arqueo.getAqres())) {
                    // Aquí podríamos contar los arqueos cuadrados si fuera necesario
                }
            }
        }
        
        return count;
    }

    @Override
    public List<Atvffcaa> obtenerReportePorAno(Integer ano) {
        return atvffCaaRepository.findByAaanos(ano);
    }

    @Override
    @Transactional
    public void eliminarReportePorAno(Integer ano) {
        atvffCaaRepository.deleteByAaanos(ano);
    }
}