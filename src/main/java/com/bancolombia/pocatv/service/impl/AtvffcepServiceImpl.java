package com.bancolombia.pocatv.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.ConsultaEspecificaDTO;
import com.bancolombia.pocatv.dto.atvffcepfechaResponseDTO;
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.Atvffcep;
import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.repository.AtvffFreRepository;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffcepRepository;
import com.bancolombia.pocatv.repository.AtvffmdRepository;
import com.bancolombia.pocatv.service.AtvffcepService;

import jakarta.transaction.Transactional;

@Service
public class AtvffcepServiceImpl implements AtvffcepService{
	
    @Autowired
    private AtvffcepRepository repository;
    
    @Autowired
    private AtvffFreRepository atvffFreRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffPdoRepository atvffPdoRepository;

	@Override
	public List<atvffcepfechaResponseDTO> findDescriptionsByYearAndMonth(Integer ano, Integer mes) {
		
		return repository.findDescriptionsByYearAndMonth(ano, mes);
	}
	
	 @Override
	    public List<ConsultaEspecificaDTO> consultarEspecificaPorProducto(Integer mes, Integer ano) {
	        List<Atvffcep> resultados = repository.findByCpmesAndCpano(mes, ano);
	        
	        return resultados.stream()
	                .map(this::mapToDTO)
	                .collect(Collectors.toList());
	    }

	    private ConsultaEspecificaDTO mapToDTO(Atvffcep atvffcep) {
	        ConsultaEspecificaDTO dto = new ConsultaEspecificaDTO();
	        dto.setMes(atvffcep.getCpmes());
	        dto.setAno(atvffcep.getCpano());
	        dto.setCumpli(atvffcep.getCpcumpli());
	        dto.setCalid(atvffcep.getCpcalid());
	        dto.setCopr(atvffcep.getCpcopr());
	        dto.setCodo(atvffcep.getCpcodo());
	        dto.setDsdo(atvffcep.getCpdsdo());
	        return dto;
	    }

	    @Override
	    @Transactional
	    public boolean procesarConsultaEspecifica(Integer mes, Integer ano) {
	        try {
	            // Paso 1: Limpiar registros existentes (equivalente a LIMPIAR en RPG)
	            limpiarRegistros(mes, ano);
	            
	            // Paso 2: Procesar todos los productos
	            List<AtvffPdo> productos = atvffPdoRepository.findAll();
	            
	            for (AtvffPdo producto : productos) {
	                procesarProducto(producto, mes, ano);
	            }
	            
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    private void limpiarRegistros(Integer mes, Integer ano) {
	        repository.deleteByCpmesAndCpano(mes, ano);
	    }
	    
	    private void procesarProducto(AtvffPdo producto, Integer mes, Integer ano) {
	        String copr = producto.getXpcopr();
	        String codo = producto.getXpcodo();
	        String dsdo = producto.getXpdsdo();
	        String feca = producto.getXpfeca();
	        
	        // Obtener la frecuencia del producto
	        AtvffFre frecuencia = atvffFreRepository.findById_FxCoprAndId_FxCodo(copr, codo);
	        if (frecuencia == null) {
	            return; // No hay frecuencia definida para este producto
	        }
	        
	        // Obtener el día de arqueo del mes
	        Optional<Atvffmd> mesDataOpt = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(
	                copr, codo, ano, mes.shortValue());
	        
	        if (mesDataOpt.isEmpty()) {
	            return; // No hay configuración para este mes/año
	        }
	        
	        Atvffmd mesData = mesDataOpt.get();
	        
	        // Calcular porcentajes de cumplimiento y calidad
	        Map<String, Integer> resultados = calcularPorcentajes(
	                copr, codo, ano, mes, frecuencia.getFxDifr(), 
	                mesData.getMddia(), mesData.getMdrang1(), feca);
	        
	        if (resultados.get("cumpli") > 0 || resultados.get("calid") > 0) {
	            // Guardar resultados
	            Atvffcep registro = new Atvffcep();
	            registro.setCpmes(mes);
	            registro.setCpano(ano);
	            registro.setCpcumpli(resultados.get("cumpli"));
	            registro.setCpcalid(resultados.get("calid"));
	            registro.setCpcopr(copr);
	            registro.setCpcodo(codo);
	            registro.setCpdsdo(dsdo);
	            
	            repository.save(registro);
	        }
	    }
	    
	    private Map<String, Integer> calcularPorcentajes(String copr, String codo, Integer ano, 
	            Integer mes, Integer frecuencia, Short diaArqueo, Short rango, String feca) {
	        
	        Map<String, Integer> resultados = new HashMap<>();
	        resultados.put("cumpli", 0);
	        resultados.put("calid", 0);
	        
	        // Obtener arqueos para este producto en el mes/año
	        String anoStr = String.valueOf(ano);
	        String mesStr = String.format("%02d", mes);
	        
	        List<Atvffarq> arqueos = atvffarqRepository.findByAqcoprAndAqcodoAndAno(copr, codo, anoStr);
	        
	        if (arqueos.isEmpty()) {
	            return resultados;
	        }
	        
	        // Filtrar arqueos por mes
	        List<Atvffarq> arqueosMes = arqueos.stream()
	                .filter(a -> a.getAqfear().substring(5, 7).equals(mesStr))
	                .collect(Collectors.toList());
	        
	        if (arqueosMes.isEmpty()) {
	            return resultados;
	        }
	        
	        // Contar arqueos ejecutados y cuadrados
	        int arqEje = arqueosMes.size();
	        int cuadrados = (int) arqueosMes.stream()
	                .filter(a -> "C".equals(a.getAqres()) || "S".equals(a.getAqjust()))
	                .count();
	        
	        // Calcular porcentaje de cumplimiento
	        if (frecuencia > 0) {
	            int cumpli = (int) ((double) arqEje / frecuencia * 100);
	            resultados.put("cumpli", Math.min(cumpli, 100)); // No debe superar 100%
	        }
	        
	        // Calcular porcentaje de calidad
	        if (arqEje > 0) {
	            int calid = (int) ((double) cuadrados / arqEje * 100);
	            resultados.put("calid", calid);
	        }
	        
	        return resultados;
	    }

}
