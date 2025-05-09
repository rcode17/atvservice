package com.bancolombia.pocatv.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.model.Atvffoas;
import com.bancolombia.pocatv.model.AtvffoasId;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffFreRepository;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffmdRepository;
import com.bancolombia.pocatv.repository.AtvffoasRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.AtvffPdsService;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.service.AtvffoasService;

import jakarta.transaction.Transactional;
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
    
    @Autowired
    private AtvffPdoRepository atvffpdoRepository;
    
    @Autowired
    private XbknamRepository xbknamlRepository;
    
    @Autowired
    private AtvffFreRepository atvfffreRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    
    
  

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

	@Override
	@Transactional
	public void procesarActualizacion(Integer mes, Integer ano) {
	    if (mes == null || ano == null) {
	        throw new IllegalArgumentException("El mes y el año no pueden ser nulos");
	    }
	    
	    if (mes < 1 || mes > 12) {
	        throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
	    }
	    
	    try {
	        // Eliminar registros existentes
	        eliminarRegistros(mes, ano);
	        
	        // Recorrer sucursales
	        recorrerSucursales(mes, ano);
	    } catch (Exception e) {
	        throw new RuntimeException("Error al procesar la actualización: " + e.getMessage(), e);
	    }
	}

	private void eliminarRegistros(Integer mes, Integer ano) {
	    try {
	        atvffoasRepository.deleteByIdOaanoAndIdOames(ano, mes);
	    } catch (Exception e) {
	        throw new RuntimeException("Error al eliminar registros: " + e.getMessage(), e);
	    }
	}

	private void recorrerSucursales(Integer mes, Integer ano) {
	    List<Xbknam> sucursales;
	    
	    try {
	        sucursales = xbknamlRepository.findAll();
	        
	        if (sucursales.isEmpty()) {
	            throw new IllegalStateException("No se encontraron sucursales para procesar");
	        }
	        
	        for (Xbknam sucursal : sucursales) {
	            Integer suc = sucursal.getXnnmky();
	            
	            if (suc == null) {
	                //logger.warn("Sucursal con código nulo encontrada, se omitirá");
	                continue;
	            }
	            
	            // Buscar productos para esta sucursal
	            buscarProductos(suc, mes, ano, sucursal.getXnname());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error al recorrer sucursales: " + e.getMessage(), e);
	    }
	}

	private void buscarProductos(Integer suc, Integer mes, Integer ano, String nombreSucursal) {
	    if (suc == null) {
	        throw new IllegalArgumentException("El código de sucursal no puede ser nulo");
	    }
	    
	    if (nombreSucursal == null || nombreSucursal.trim().isEmpty()) {
	        //logger.warn("Nombre de sucursal vacío para el código: " + suc);
	        nombreSucursal = "Sucursal " + suc;
	    }
	    
	    try {
	        List<AtvffPds> productos = pdsService.findByIdXsCosu(suc);
	        
	        if (productos.isEmpty()) {
	            //logger.info("No se encontraron productos para la sucursal: " + suc);
	            return;
	        }
	        
	        int tcump = 0;
	        int tcal = 0;
	        int cont = 0;
	        
	        for (AtvffPds producto : productos) {
	            String pro = producto.getXscopr();
	            String doc = producto.getXscodo();
	            
	            if (pro == null || doc == null) {
	                //logger.warn("Producto o documento nulo encontrado, se omitirá");
	                continue;
	            }
	            
	            // Inicializar variables para este producto
	            int arqPro = 0;
	            int cum = 0;
	            int cal = 0;
	            int arqEje = 0;
	            int cuadrado = 0;
	            
	            // Buscar frecuencia
	            int mesIni = mes;
	            int mesFin = mes;
	            
	            try {
	                Optional<AtvffFre> frecuencia = atvfffreRepository.findByFxCoprAndFxCodo(pro, doc);
	                
	                if (frecuencia.isPresent()) {
	                    String tipo = frecuencia.get().getFxFrar();
	                    arqPro = frecuencia.get().getFxDifr();
	                    
	                    // Determinar rango de meses según tipo
	                    switch (tipo) {
	                        case "T": // Trimestral
	                            if (mes >= 1 && mes <= 3) {
	                                mesIni = 1;
	                                mesFin = 3;
	                            } else if (mes >= 4 && mes <= 6) {
	                                mesIni = 4;
	                                mesFin = 6;
	                            } else if (mes >= 7 && mes <= 9) {
	                                mesIni = 7;
	                                mesFin = 9;
	                            } else {
	                                mesIni = 10;
	                                mesFin = 12;
	                            }
	                            break;
	                        case "S": // Semestral
	                            if (mes >= 1 && mes <= 6) {
	                                mesIni = 1;
	                                mesFin = 6;
	                            } else {
	                                mesIni = 7;
	                                mesFin = 12;
	                            }
	                            break;
	                        case "A": // Anual
	                            mesIni = 1;
	                            mesFin = 12;
	                            break;
	                        default:
	                            //logger.warn("Tipo de frecuencia desconocido: " + tipo + " para producto: " + pro + " y documento: " + doc);
	                    }
	                    
	                    // Buscar información de días
	                    Optional<Atvffmd> mdEntity = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(pro, doc, ano, (short) mesFin);
	                    
	                    if (mdEntity.isPresent()) {
	                        int diaFin = mdEntity.get().getMddia();
	                        int rangoDiaI = (diaFin - mdEntity.get().getMdrang1()) + 1;
	                        
	                        // Buscar arqueos realizados
	                        List<Atvffarq> arqueos = atvffarqRepository.findBySucursalAndCoprAndCodo(suc, pro, doc);
	                        
	                        if (!arqueos.isEmpty()) {
	                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                            
	                            for (Atvffarq arqueo : arqueos) {
	                                try {
	                                    String fechaArqueoStr = arqueo.getAqfear();
	                                    
	                                    if (fechaArqueoStr == null || fechaArqueoStr.trim().isEmpty()) {
	                                        //logger.warn("Fecha de arqueo vacía, se omitirá");
	                                        continue;
	                                    }
	                                    
	                                    LocalDate fechaArqueo = LocalDate.parse(fechaArqueoStr, formatter);
	                                    int anoArqueo = fechaArqueo.getYear();
	                                    int mesArqueo = fechaArqueo.getMonthValue();
	                                    
	                                    if (anoArqueo == ano && mesArqueo >= mesIni && mesArqueo <= mesFin) {
	                                        // Contar arqueo
	                                        arqEje++;
	                                        
	                                        // Verificar si está cuadrado
	                                        if ("C".equals(arqueo.getAqres()) || "S".equals(arqueo.getAqjust())) {
	                                            cuadrado++;
	                                        }
	                                    }
	                                } catch (DateTimeParseException e) {
	                                    //logger.error("Error al parsear fecha de arqueo: " + arqueo.getAqfear(), e);
	                                }
	                            }
	                        } else {
	                            //logger.info("No se encontraron arqueos para sucursal: " + suc + ", producto: " + pro + " y documento: " + doc);
	                        }
	                        
	                        // Verificar producto final
	                        AtvffPdo pdoEntity = atvffpdoRepository.findByXpcoprAndXpcodo(pro, doc);
	                        
	                        if (pdoEntity != null) {
	                            // Calcular porcentajes
	                            if (arqPro > 0) {
	                                cum = (int) Math.round((double) arqEje / arqPro * 100);
	                            }
	                            
	                            if (arqEje > 0) {
	                                cal = (int) Math.round((double) cuadrado / arqEje * 100);
	                            }
	                        } else {
	                            //logger.warn("No se encontró información de producto final para producto: " + pro + " y documento: " + doc);
	                        }
	                    } else {
	                        //logger.warn("No se encontró información de días para producto: " + pro + ", documento: " + doc + ", año: " + ano + " y mes: " + mesFin);
	                    }
	                } else {
	                    //logger.info("No se encontró frecuencia para producto: " + pro + " y documento: " + doc);
	                }
	            } catch (Exception e) {
	                //logger.error("Error al procesar producto: " + pro + " y documento: " + doc, e);
	                // Continuamos con el siguiente producto
	                continue;
	            }
	            
	            // Acumular resultados
	            tcump += cum;
	            tcal += cal;
	            cont++;
	        }
	        
	        // Calcular totales
	        int totCum = 0;
	        int totCal = 0;
	        
	        if (cont > 0) {
	            totCum = tcump / cont;
	            totCal = tcal / cont;
	        } else {
	            //logger.warn("No se procesaron productos válidos para la sucursal: " + suc);
	        }
	        
	        // Guardar resultados
	        try {
	            Atvffoas oasEntity = new Atvffoas();
	            AtvffoasId id = new AtvffoasId(mes, ano, suc);
	            oasEntity.setId(id);
	            oasEntity.setOanombre(nombreSucursal);
	            oasEntity.setOacumpli(totCum);
	            oasEntity.setOacalid(totCal);
	            
	            atvffoasRepository.save(oasEntity);
	            //logger.info("Guardado exitoso para sucursal: " + suc + ", mes: " + mes + ", año: " + ano);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al guardar resultados para sucursal: " + suc, e);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error al buscar productos para sucursal: " + suc, e);
	    }
	}
    
}
    

