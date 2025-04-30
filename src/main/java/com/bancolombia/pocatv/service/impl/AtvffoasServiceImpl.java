package com.bancolombia.pocatv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.GrupoResponseDTO;
import com.bancolombia.pocatv.dto.MesResponseDTO;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Atvffoas;
import com.bancolombia.pocatv.model.AtvffoasId;
import com.bancolombia.pocatv.repository.AtvffoasRepository;
import com.bancolombia.pocatv.service.AtvffPdsService;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.service.AtvffoasService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtvffoasServiceImpl implements AtvffoasService {

    @Autowired
    private AtvffoasRepository atvffoasRepository;
    
    @Autowired
    private AtvffUserService userService;
    
    @Autowired
    private AtvffPdsService pdsService;

    @Override
    public Page<Atvffoas> findAll(Pageable pageable) {
        return atvffoasRepository.findAll(pageable);
    }

    @Override
    public Page<Atvffoas> findByAnoMes(Integer oaano, Integer oames, Pageable pageable) {
        return atvffoasRepository.findByIdOaanoAndIdOames(oaano, oames, pageable);
    }

    @Override
    public Page<Atvffoas> getRegistrosArchivoProducto(String xuUser, Integer oaano, Integer oames, Integer oaxnnmky, Pageable pageable) {
        
    	Optional<AtvffUser> usuario = userService.findById(xuUser);
    	
    	
    	pdsService.findByIdXsCosu(oaxnnmky);
    	
    	if (!usuario.isPresent()) {
            usuario.get().getXuDom();
            usuario.get().getXuArea();
           
        }
    	
    	// Si se proporciona el valor de oaxnnmky, se consulta por año, mes y oaxnnmky
        if (oaxnnmky != null) {
            return atvffoasRepository.findByIdOaanoAndIdOamesAndIdOaxnnmky(oaano, oames, oaxnnmky, pageable);
        } else {
            // En caso contrario se devuelve solo la lista filtrada por año y mes
            return atvffoasRepository.findByIdOaanoAndIdOames(oaano, oames, pageable);
        }
    }
    

    @Override
    public Page<Atvffoas> buscarPorUsuarioAnoMes(String usuario, Integer ano, Integer mes, Pageable pageable) {
        return atvffoasRepository.findPorUsuarioAnoMes(usuario, ano, mes, pageable);
    }


	@Override
	public Page<GrupoResponseDTO> obtenerDatosPorAnoUserDominio(Integer anno, String fuser, String dominio,
			Pageable pageable) {
		
		List<Atvffoas> registros = new ArrayList<>();
		if(dominio.equals("03")) {
			registros = atvffoasRepository.findAnnoUser(anno,fuser);
		} else {
			registros = atvffoasRepository.findAnno(anno);
		}
		
		Map<Integer, List<Atvffoas>> agrupados = registros.stream()
	            .collect(Collectors.groupingBy(reg -> reg.getId().getOaxnnmky()));
		
		// Para cada grupo, consolidar la información por mes y calcular el promedio anual
	    List<GrupoResponseDTO> listaGrupos = agrupados.entrySet().stream().map(entry -> {
	        Integer oaxnnmky = entry.getKey();
	        List<Atvffoas> lista = entry.getValue();

	        // Consolidar la información por mes en un Map (si existen dos registros para el mismo mes se toma el último)
	        Map<Integer, Atvffoas> mesMap = lista.stream()
	                .collect(Collectors.toMap(
	                        rec -> rec.getId().getOames(),
	                        rec -> rec,
	                        (registroExistente, nuevoRegistro) -> nuevoRegistro
	                ));

	        // Calcular la suma total de los 12 meses, considerando 0 para los meses faltantes
	        int sumaTotalCum = 0;
	        int sumaTotalCal = 0;
	        for (int mes = 1; mes <= 12; mes++) {
	            if (mesMap.containsKey(mes)) {
	                sumaTotalCum += mesMap.get(mes).getOacumpli();
	                sumaTotalCal += mesMap.get(mes).getOacalid();
	            }
	        }
	        // Calcular el promedio anual dividiendo la suma total entre 12
	        double promedioAnualCum = sumaTotalCum / 12.0;
	        double promedioAnualCal = sumaTotalCal / 12.0;

	        List<MesResponseDTO> meses = mesMap.entrySet().stream()
	                .sorted(Map.Entry.comparingByKey())
	                .map(e -> {
	                    Atvffoas registro = e.getValue();
	                    return new MesResponseDTO(e.getKey(), registro.getOacumpli(), registro.getOacalid());
	                })
	                .collect(Collectors.toList());

	        // Se asume que GrupoResponseDTO fue actualizado para recibir los dos promedios y la lista de meses
	        return new GrupoResponseDTO(oaxnnmky, promedioAnualCum, promedioAnualCal, meses);
	    }).collect(Collectors.toList());
	    
	    // Paginar el resultado "listaGrupos"
	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), listaGrupos.size());
	    List<GrupoResponseDTO> subList = listaGrupos.subList(start, end);

	    return new PageImpl<>(subList, pageable, listaGrupos.size());
	}
    
    


}