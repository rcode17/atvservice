package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.dto.ArqueoDTO;
import com.bancolombia.pocatv.dto.ArqueoDescuadradoDTO;
import com.bancolombia.pocatv.dto.IncumplimientoDTO;
import com.bancolombia.pocatv.dto.ResponseIncumplimientoDTO;
import com.bancolombia.pocatv.dto.ArqueoResumenDTO;
import com.bancolombia.pocatv.dto.ArqueoTotalesDTO;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffPdoId;
import com.bancolombia.pocatv.model.AtvffPds;
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Atvffapa;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.Atvffcrd;
import com.bancolombia.pocatv.model.AtvffcrdId;
import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.model.AtvffmdId;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffFreRepository;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import com.bancolombia.pocatv.repository.AtvffapaRepository;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffcrdRepository;
import com.bancolombia.pocatv.repository.AtvffmdRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.AtvffarqService;

import com.bancolombia.pocatv.utils.CalcularPorcentajes;

import jakarta.transaction.Transactional;

@Service
public class AtvffarqServiceImpl implements AtvffarqService {

	@Autowired
	private AtvffcrdRepository atvffcrdRepository;

	@Autowired
	private AtvffarqRepository atvffarqRepository;

	@Autowired
	private AtvffFreRepository atvffFreRepository;
	
	@Autowired
	private AtvffPdoRepository atvffpdoRepository;

	@Autowired
	private AtvffPdsRepository atvffpdsRepository;

	@Autowired
	private AtvffUserRepository atvffuserRepository;
	
	 @Autowired
	 private AtvffmdRepository atvffmdRepository;
	  
	 @Autowired
	 private XbknamRepository xbknamRepository;
	 
	 @Autowired
	 private AtvffapaRepository atvffapaRepository;
	
	@Autowired
	private CalcularPorcentajes calcularPorcentajes;

	@Autowired
	public AtvffarqServiceImpl(AtvffarqRepository atvffarqRepository,AtvffcrdRepository atvffcrdRepository) {
		this.atvffarqRepository = atvffarqRepository;
		this.atvffcrdRepository = atvffcrdRepository;
	}
	
    

	@Override
	public Optional<Atvffarq> findByFields(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear) {
		return atvffarqRepository.findByAqcdsuAndAqcoprAndAqcodoAndAqfear(aqcdsu, aqcopr, aqcodo, aqfear);
	}

	@Override
	public List<Atvffarq> buscarRangosInconsistentes(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear,
			String aqcedan) {
		List<Atvffarq> results = atvffarqRepository.findByAqcdsuAndAqcoprAndAqcodoAndAqfearAndAqcedan(aqcdsu, aqcopr,
				aqcodo, aqfear, aqcedan);

		List<Atvffarq> validResults = results.stream().filter(Atvffarq::hasDataInAqrain).toList();

		if (validResults.isEmpty()) {
			// Si no hay resultados válidos, puedes lanzar una excepción o devolver una
			// lista vacía
			throw new IllegalArgumentException(
					"No hay registros con datos válidos en los campos aqrain para los parámetros proporcionados.");
		}

		return validResults;
	}

	@Override
	public List<ArqueoDescuadradoDTO> consultarArqueosDescuadrados(String usuario, Integer mes, Integer anno,
			String dom, Integer codArea) {
		List<ArqueoDescuadradoDTO> resultado = new ArrayList<>();

		// Verificar usuario
		AtvffUser usuarioOpt = atvffuserRepository.findByXuUser(usuario);
		if (usuarioOpt == null) {
			throw new IllegalStateException("Usuario no encontrado");
		}

		AtvffUser user = usuarioOpt;
		Integer tipoAcceso = user.getXuAcce().intValue();

		// Verificar dominio si es diferente de "00"
		if (!"00".equals(dom) && !dom.equals(user.getXuDom())) {
			return resultado; // Retorna lista vacía si el dominio no coincide
		}

		List<Atvffcrd> arqueos;

		// Filtrar por código de área si se proporciona
		if (codArea != null && codArea > 0) {
			arqueos = atvffcrdRepository.findByAnnoAndMesAndCodsucGreaterThanEqual(anno, mes, codArea);
		} else {
			arqueos = atvffcrdRepository.findByAnnoAndMes(anno, mes);
		}

		// Procesar cada arqueo encontrado
		for (Atvffcrd arqueo : arqueos) {
			ArqueoDescuadradoDTO dto = new ArqueoDescuadradoDTO();

			// Datos básicos del arqueo
			dto.setCodsuc(arqueo.getId().getCdcodsuc());
			dto.setSucurs(arqueo.getCdnomsuc());
			dto.setCodpro(arqueo.getId().getCdcopr());
			dto.setCoddoc(arqueo.getId().getCdcodo());
			dto.setAqfear(arqueo.getId().getCdfear());
			dto.setAqdif(arqueo.getCddif());
			dto.setAqres(arqueo.getCdres());
			dto.setShceda(arqueo.getCdcedan());

			// Buscar información del producto
			AtvffPdo productoOpt = atvffpdoRepository.findByXpcoprAndXpcodo(arqueo.getId().getCdcopr(),
					arqueo.getId().getCdcodo());

			if (productoOpt != null) {
				dto.setProducto(productoOpt.getXpdsdo());
			}

			// Verificar si existe en ATVFFPDS
			Optional<AtvffPds> pdsOpt = atvffpdsRepository.findByXscosuAndXscoprAndXscodo(arqueo.getId().getCdcodsuc(),
					arqueo.getId().getCdcopr(), arqueo.getId().getCdcodo());

			if (pdsOpt.isEmpty()) {
				continue; // Si no existe en ATVFFPDS, no incluir en resultados
			}

			// Buscar información detallada del arqueo
			List<Atvffarq> arqueoDetalleOpt = atvffarqRepository.findByAqcdsuAndAqcoprAndAqcodoAndAqfearAndAqcedan(
					arqueo.getId().getCdcodsuc(), arqueo.getId().getCdcopr(), arqueo.getId().getCdcodo(),
					arqueo.getId().getCdfear().toString(), arqueo.getCdcedan());

			// Verificar si la lista no está vacía y asignar los valores del primer elemento
			// al DTO
			if (arqueoDetalleOpt != null && !arqueoDetalleOpt.isEmpty()) {
				Atvffarq arqueoDetalle = arqueoDetalleOpt.get(0);
				dto.setAqsfar(arqueoDetalle.getAqsfar());
				dto.setAqprcu(arqueoDetalle.getAqprcu());
				dto.setAqpear(arqueoDetalle.getAqpear());
				dto.setAqobsn(arqueoDetalle.getAqobsn());
				dto.setVaqgest(arqueoDetalle.getAqgest());
				dto.setVdjust(arqueoDetalle.getAqjust());
			}

			// Configurar acceso según el tipo de usuario
			dto.setAccesso(tipoAcceso.toString());

			resultado.add(dto);
		}

		return resultado;
	}

	@Override
	@Transactional
	public void gestionarArqueo(Integer codsuc, String codpro, String coddoc, String fechaStr, Double diferencia,
			String acceso) {
		// Convertir fecha de String a LocalDate
		LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);

		// Validar acceso
		if (!"1".equals(acceso)) {

			throw new IllegalStateException("Usuario no autorizado para gestionar arqueos");
		}

		// Buscar el arqueo
		Optional<Atvffarq> arqueoOpt = buscarArqueo(codsuc, codpro, coddoc, fecha, diferencia);

		if (arqueoOpt.isPresent()) {
			Atvffarq arqueo = arqueoOpt.get();

			// Marcar como gestionado
			arqueo.setAqgest("G");

			// Guardar cambios
			atvffarqRepository.save(arqueo);
		} else {

			throw new IllegalStateException("No se encontró el arqueo especificado");
		}
	}

	@Override
	@Transactional
	public void justificarArqueo(Integer codsuc, String codpro, String coddoc, String fechaStr, Double diferencia,
			String acceso) {
		// Convertir fecha de String a LocalDate
		LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);

		// Validar acceso
		if (!"1".equals(acceso)) {
			throw new IllegalArgumentException("Usuario no autorizado para justificar arqueos");
		}

		// Buscar el arqueo
		Optional<Atvffarq> arqueoOpt = buscarArqueo(codsuc, codpro, coddoc, fecha, diferencia);

		if (arqueoOpt.isPresent()) {
			Atvffarq arqueo = arqueoOpt.get();

			// Verificar si está gestionado
			if (!"G".equals(arqueo.getAqgest())) {
				throw new IllegalArgumentException("Gestione antes de justificar");
			}

			// Marcar como justificado
			arqueo.setAqjust("S");

			// Guardar cambios
			atvffarqRepository.save(arqueo);
		} else {
			throw new IllegalArgumentException("No se encontró el arqueo especificado");
		}
	}

	@Override
	@Transactional
	public void quitarJustificacion(Integer codsuc, String codpro, String coddoc, String fechaStr, Double diferencia,
			String acceso) {
		// Convertir fecha de String a LocalDate
		LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);

		// Validar acceso
		if (!"1".equals(acceso)) {
			throw new IllegalArgumentException("Usuario no autorizado para quitar justificación");
		}

		// Buscar el arqueo
		Optional<Atvffarq> arqueoOpt = buscarArqueo(codsuc, codpro, coddoc, fecha, diferencia);

		if (arqueoOpt.isPresent()) {
			Atvffarq arqueo = arqueoOpt.get();

			// Verificar si está justificado
			if (!"S".equals(arqueo.getAqjust())) {
				throw new IllegalArgumentException("El arqueo no está justificado");
			}

			// Quitar justificación
			arqueo.setAqjust("");

			// Guardar cambios
			atvffarqRepository.save(arqueo);
		} else {
			throw new IllegalArgumentException("No se encontró el arqueo especificado");
		}
	}

	private Optional<Atvffarq> buscarArqueo(Integer codsuc, String codpro, String coddoc, LocalDate fecha,
			Double diferencia) {
		// Buscar todos los arqueos que coincidan con los criterios principales
		List<Atvffarq> arqueos = atvffarqRepository.findAll();

		// Imprimir información de depuración
		System.out.println("Buscando arqueo con: codsuc=" + codsuc + ", codpro=" + codpro + ", coddoc=" + coddoc
				+ ", fecha=" + fecha + ", diferencia=" + diferencia);

		// Convertir el Double a BigDecimal para la comparación
		BigDecimal diferenciaDecimal = BigDecimal.valueOf(diferencia);

		// Usar un enfoque más flexible para la comparación
		for (Atvffarq arqueo : arqueos) {
			// Imprimir cada arqueo para depuración
			System.out.println("Evaluando: " + arqueo.getAqcdsu() + ", " + arqueo.getAqcopr() + ", "
					+ arqueo.getAqcodo() + ", " + arqueo.getAqfear() + ", " + arqueo.getAqdif());

			// Verificar cada condición por separado para facilitar la depuración
			boolean coincideSucursal = arqueo.getAqcdsu().equals(codsuc);
			boolean coincideProducto = arqueo.getAqcopr().equals(codpro);
			boolean coincideDocumento = arqueo.getAqcodo().equals(coddoc);

			// Para la fecha, usar comparación de cadenas si es necesario
			boolean coincideFecha = false;
			if (arqueo.getAqfear() != null && fecha != null) {
				coincideFecha = arqueo.getAqfear().equals(fecha.toString());
				// Alternativa: comparar como cadenas
				// coincideFecha = arqueo.getAqfear().toString().equals(fecha.toString());
			}

			// Para la diferencia, usar un margen de error más amplio
			boolean coincideDiferencia = false;
			if (arqueo.getAqdif() != null && diferenciaDecimal != null) {
				BigDecimal diferenciaBD = arqueo.getAqdif();
				BigDecimal margenError = new BigDecimal("0.1"); // Aumentar el margen de error
				coincideDiferencia = diferenciaBD.subtract(diferenciaDecimal).abs().compareTo(margenError) <= 0;
			}

			// Imprimir resultados de cada comparación
			System.out.println(
					"Coincidencias: sucursal=" + coincideSucursal + ", producto=" + coincideProducto + ", documento="
							+ coincideDocumento + ", fecha=" + coincideFecha + ", diferencia=" + coincideDiferencia);

			// Si todas las condiciones coinciden, devolver este arqueo
			if (coincideSucursal && coincideProducto && coincideDocumento && coincideFecha && coincideDiferencia) {
				return Optional.of(arqueo);
			}
		}

		// Si no se encontró ninguna coincidencia
		System.out.println("No se encontró ningún arqueo que coincida con los criterios");
		return Optional.empty();
	}
	
	@Override
    @Transactional
    public List<Atvffcrd> procesarArqueosDescuadrados() {
        // Fecha por defecto como en el código RPG original (20000101)
        LocalDate fechaInicio = LocalDate.of(2023,1,15);
        return procesarArqueosDescuadradosDesdeFecha(fechaInicio);
    }
    
    @Override
    @Transactional
    public List<Atvffcrd> procesarArqueosDescuadradosDesdeFecha(LocalDate fechaInicio) {
        // Convertir LocalDate a String en formato yyyyMMdd para la consulta
        String fechaInicioStr = fechaInicio.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        // Buscar arqueos con resultado 'D' (descuadrados) desde la fecha indicada
        List<Atvffarq> arqueos = atvffarqRepository.findByAqresAndAqfearGreaterThanEqual("D", fechaInicioStr);
        
        if (arqueos.isEmpty()) {
            throw new IllegalArgumentException("No hay registros con datos válidos en los campos aqrain para los parámetros proporcionados.");
        }
        
        List<Atvffcrd> rangosGuardados = new ArrayList<>();
        
        // Procesar cada arqueo y convertirlo a rango descuadrado
        for (Atvffarq arqueo : arqueos) {
        	Atvffcrd rangoDescuadrado = convertirArqueoARangoDescuadrado(arqueo);
            rangosGuardados.add(atvffcrdRepository.save(rangoDescuadrado));
        }
        
        return rangosGuardados;
    }
    
    /**
     * Convierte un objeto ATVFFARQ a un objeto ATVFFCRD
     */
    private Atvffcrd convertirArqueoARangoDescuadrado(Atvffarq arqueo) {
    	Atvffcrd rangoDescuadrado = new Atvffcrd();
        
     
        // Convertir el String de fecha a LocalDate
    	LocalDate fechaArqueo = LocalDate.parse(arqueo.getAqfear());
        
        // Crear el ID compuesto para Atvffcrd
        AtvffcrdId atvffcrdId = new AtvffcrdId();
        atvffcrdId.setCdano(fechaArqueo.getYear());
        atvffcrdId.setCdmes(fechaArqueo.getMonthValue());
        atvffcrdId.setCdcodsuc(arqueo.getAqcdsu());
        atvffcrdId.setCdcopr(arqueo.getAqcopr());
        atvffcrdId.setCdcodo(arqueo.getAqcodo());
        atvffcrdId.setCdfear(fechaArqueo);
        
        rangoDescuadrado.setId(atvffcrdId);
        
        
        // Establecer el resto de campos
        rangoDescuadrado.setCdcon(fechaArqueo.getDayOfMonth());
        rangoDescuadrado.setCdnomsuc(arqueo.getAqsuc());
        rangoDescuadrado.setCddif(arqueo.getAqdif());
        rangoDescuadrado.setCdcedan(arqueo.getAqcedan());
        rangoDescuadrado.setCdsfar(arqueo.getAqsfar());
        rangoDescuadrado.setCdres(arqueo.getAqres());
        
        return rangoDescuadrado;
    }



	@Override
	public ResponseIncumplimientoDTO<List<IncumplimientoDTO>> obtenerIncumplimientosUltimoDiaHabil(String usuario) {
	       try {
	            // Obtener mes y año actual
	            LocalDate fechaActual = LocalDate.now();
	            int mesActual = fechaActual.getMonthValue();
	            int anoActual = fechaActual.getYear();
	            
	            // Si estamos en el primer mes del año, consultamos el mes 12 del año anterior
	            if (mesActual == 1) {
	                return obtenerIncumplimientosPorMesAno(12, anoActual - 1, usuario);
	            } else {
	                // Consultamos el mes anterior
	                return obtenerIncumplimientosPorMesAno(mesActual - 1, anoActual, usuario);
	            }
	        } catch (Exception e) {
	            return ResponseIncumplimientoDTO.error("01", "Error al obtener incumplimientos: " + e.getMessage());
	        }
		
	}



	@Override
	public ResponseIncumplimientoDTO<List<IncumplimientoDTO>> obtenerIncumplimientosPorMesAno(Integer mes, Integer ano,
			String usuario) {
		try {
            List<IncumplimientoDTO> incumplimientos = new ArrayList<>();
            
            // Obtener el último día del mes
            YearMonth yearMonth = YearMonth.of(ano, mes);
            LocalDate ultimoDiaMes = yearMonth.atEndOfMonth();
            
            // Obtener todas las sucursales con productos y documentos
            List<AtvffPds> sucursalesProductos = atvffpdsRepository.findAll();
            
            for (AtvffPds sucursalProducto : sucursalesProductos) {
                String codigoProducto = sucursalProducto.getXscopr();
                String codigoDocumento = sucursalProducto.getXscodo();
                Integer codigoSucursal = sucursalProducto.getXscosu();
                
                // Verificar si el producto requiere arqueo en el último día hábil
                Optional<AtvffPdo> productoOpt = atvffpdoRepository.findById(
                    new AtvffPdoId(codigoProducto, codigoDocumento)
                );
                
                if (productoOpt.isPresent() && "S".equals(productoOpt.get().getXpfeca())) {
                    // Obtener la configuración del mes
                    Optional<Atvffmd> mesConfigOpt = atvffmdRepository.findById(
                        new AtvffmdId(codigoProducto, codigoDocumento, ano, mes.shortValue()));
                    
                    if (mesConfigOpt.isPresent()) {
                        Atvffmd mesConfig = mesConfigOpt.get();
                        LocalDate fechaArqueo = LocalDate.of(ano, mes, mesConfig.getMddia());
                        
                        // Verificar si hay arqueo para esta fecha
                        boolean tieneArqueo = verificarArqueoEnFecha(codigoSucursal, codigoProducto, codigoDocumento, fechaArqueo);
                        
                        if (!tieneArqueo) {
                            // Buscar información de la sucursal/área
                            Optional<Xbknam> areaOpt = buscarAreaPorSucursal(codigoSucursal);
                            
                            if (areaOpt.isPresent()) {
                                Xbknam area = areaOpt.get();
                                
                                // Crear DTO de incumplimiento
                                IncumplimientoDTO incumplimiento = new IncumplimientoDTO();
                                incumplimiento.setProducto(productoOpt.get().getXpdsdo());
                                incumplimiento.setCodZona(area.getXncdci());
                                incumplimiento.setCodArea(area.getXnnmky());
                                incumplimiento.setDescArea(area.getXnname());
                                incumplimiento.setFechaIncumplimiento(fechaArqueo);
                                incumplimiento.setCodpro(codigoProducto);
                                incumplimiento.setCoddoc(codigoDocumento);
                                
                                incumplimientos.add(incumplimiento);
                            }
                        }
                    }
                }
            }
            
            return ResponseIncumplimientoDTO.exitoso(incumplimientos);
        } catch (Exception e) {
            return ResponseIncumplimientoDTO.error("02", "Error al obtener incumplimientos por mes y año: " + e.getMessage());
        }
		
	}



	@Override
	public ResponseIncumplimientoDTO<Boolean> verificarIncumplimientoSucursal(Integer codigoSucursal,
			String codigoProducto, String codigoDocumento, String usuario) {
		try {
            // Obtener mes y año actual
            LocalDate fechaActual = LocalDate.now();
            int mesActual = fechaActual.getMonthValue();
            int anoActual = fechaActual.getYear();
            
            // Si estamos en el primer mes del año, consultamos el mes 12 del año anterior
            Integer mesConsulta = (mesActual == 1) ? 12 : mesActual - 1;
            int anoConsulta = (mesActual == 1) ? anoActual - 1 : anoActual;
            
            // Obtener la configuración del mes
            Optional<Atvffmd> mesConfigOpt = atvffmdRepository.findById(
                new AtvffmdId(codigoProducto, codigoDocumento, anoConsulta, mesConsulta.shortValue())
            );
            
            if (mesConfigOpt.isPresent()) {
                Atvffmd mesConfig = mesConfigOpt.get();
                LocalDate fechaArqueo = LocalDate.of(anoConsulta, mesConsulta, mesConfig.getMddia());
                
                // Verificar si hay arqueo para esta fecha
                boolean tieneArqueo = verificarArqueoEnFecha(codigoSucursal, codigoProducto, codigoDocumento, fechaArqueo);
                
                return ResponseIncumplimientoDTO.exitoso(!tieneArqueo); // Devuelve true si hay incumplimiento (no tiene arqueo)
            }
            
            return ResponseIncumplimientoDTO.exitoso(false); // No hay configuración para este mes, no se puede determinar incumplimiento
        } catch (Exception e) {
            return ResponseIncumplimientoDTO.error("03", "Error al verificar incumplimiento de sucursal: " + e.getMessage());
        }
		
	}



	@Override
	public ResponseIncumplimientoDTO<List<IncumplimientoDTO>> obtenerDetalleArqueosSucursal(Integer codigoSucursal,
			Integer mes, Integer ano, String usuario) {
		
		 try {
	            List<IncumplimientoDTO> detalleArqueos = new ArrayList<>();
	            
	            // Obtener todos los productos asociados a la sucursal
	            List<AtvffPds> productosSucursal = atvffpdsRepository.findByIdXscosu(codigoSucursal);
	            
	            for (AtvffPds productoSucursal : productosSucursal) {
	                String codigoProducto = productoSucursal.getXscopr();
	                String codigoDocumento = productoSucursal.getXscodo();
	                
	                // Obtener información del producto
	                Optional<AtvffPdo> productoOpt = atvffpdoRepository.findById(
	                    new AtvffPdoId(codigoProducto, codigoDocumento)
	                );
	                
	                if (productoOpt.isPresent()) {
	                    AtvffPdo producto = productoOpt.get();
	                    
	                    String anoStr = String.format("%02d",ano);
	                    String mesStr = String.valueOf(mes);
	                    
	                    // Obtener arqueos para este producto en el mes y año especificados
	                    List<Atvffarq> arqueos = atvffarqRepository.findArqueosPorMes(
	                    	codigoSucursal, codigoProducto, codigoDocumento, mesStr, anoStr
	                    );
	                    
	                    // Buscar información de la sucursal/área
	                    Optional<Xbknam> areaOpt = buscarAreaPorSucursal(codigoSucursal);
	                    
	                    if (areaOpt.isPresent()) {
	                        Xbknam area = areaOpt.get();
	                        
	                        // Crear DTOs para cada arqueo
	                        for (Atvffarq arqueo : arqueos) {
	                            IncumplimientoDTO detalleArqueo = new IncumplimientoDTO();
	                            detalleArqueo.setProducto(producto.getXpdsdo());
	                            detalleArqueo.setCodZona(area.getXncdci());
	                            detalleArqueo.setCodArea(area.getXnnmky());
	                            detalleArqueo.setDescArea(area.getXnname());
	                            LocalDate fecha = LocalDate.parse(arqueo.getAqfear());
	                            detalleArqueo.setFechaIncumplimiento(fecha);
	                            
	                            detalleArqueos.add(detalleArqueo);
	                        }
	                    }
	                }
	            }
	            
	            return ResponseIncumplimientoDTO.exitoso(detalleArqueos);
	        } catch (Exception e) {
	            return ResponseIncumplimientoDTO.error("04", "Error al obtener detalle de arqueos: " + e.getMessage());
	        }
	}
	
	/**
	 * Verifica si existe un arqueo para una sucursal, producto y documento en una fecha específica.
	 */
	private boolean verificarArqueoEnFecha(Integer codigoSucursal, String codigoProducto, String codigoDocumento, LocalDate fecha) {
	    // Convertir LocalDate a String en el formato esperado (por ejemplo, "YYYY-MM-DD")
	    String fechaStr = fecha.toString();
	    
	    return atvffarqRepository.existsByAqcdsuAndAqcoprAndAqcodoAndAqfear(
	        codigoSucursal, codigoProducto, codigoDocumento, fechaStr);
	}

    /**
     * Busca información del área asociada a una sucursal.
     * 
     * @param codigoSucursal Código de la sucursal
     * @return Optional con la información del área si existe
     */
    private Optional<Xbknam> buscarAreaPorSucursal(Integer codigoSucursal) {
        // En un sistema real, aquí habría una lógica para mapear sucursales a áreas
        // Para este ejemplo, simplificamos asumiendo que el código de sucursal
        // puede usarse para buscar directamente en la tabla XBKNAM
        
        // Esto es una simplificación, en la implementación real
        // se debería consultar alguna tabla de mapeo o usar otra lógica
        return xbknamRepository.findByXnnmky(codigoSucursal);
    }



	@Override
	public List<ArqueoAnormalDTO> consultarArqueosAnormales(Integer ano, Integer mes, Integer codsuc) {
		List<Atvffapa> arqueos;

		// Lógica para obtener los arqueos según los parámetros proporcionados
		if (mes != null && mes > 0 && codsuc != null && codsuc > 0) {
			arqueos = atvffapaRepository.findByApanoAndApmesAndApcodsuc(ano, mes, codsuc);
		} else if (mes != null && mes > 0) {
			arqueos = atvffapaRepository.findByApanoAndApmes(ano, mes);
		} else if (codsuc != null && codsuc > 0) {
			arqueos = atvffapaRepository.findByCodsuc(codsuc);
		} else {
			arqueos = atvffapaRepository.findByApano(ano);
		}

		if (arqueos.isEmpty()) {
			throw new IllegalArgumentException(
					"No hay registros con datos válidos para los parámetros proporcionados.");
			
		}

		List<ArqueoAnormalDTO> resultado = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (Atvffapa arqueo : arqueos) {
			ArqueoAnormalDTO dto = new ArqueoAnormalDTO();
			dto.setCodsuc(arqueo.getApcodsuc());
			dto.setSuc(arqueo.getApnomsuc());
			dto.setProd(arqueo.getApcopr());
			dto.setDoc(arqueo.getApcodo());
			dto.setFecharq(arqueo.getApfear());
			dto.setFechaArqueoStr(arqueo.getApfear().format(formatter));

			// Obtener descripción del producto
			AtvffPdo producto = atvffpdoRepository.findByXpcoprAndXpcodo(arqueo.getApcopr(), arqueo.getApcodo());

			if (producto != null) {
			    dto.setDesprod(producto.getXpdsdo());
			} else {
			    dto.setDesprod("Producto no encontrado"); // Mensaje más descriptivo
			}

			// Agregamos el DTO al resultado independientemente de si encontramos el producto o no
			resultado.add(dto);

		}
		return resultado;
	}

	@Override
    public Page<ArqueoDTO> obtenerArqueosPorSucursal(String usuario, Integer ano, Integer mes, Integer sucursal, Pageable pageable) {
		
    	// Verificar si el usuario existe
        AtvffUser user = atvffuserRepository.findByXuUser(usuario);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + usuario);
        }
        
        // Obtener todos los productos/documentos de la sucursal
        List<AtvffPds> productosSucursal = atvffpdsRepository.findByIdXscosu(sucursal);
        if (productosSucursal.isEmpty()) {
            throw new IllegalArgumentException("No hay productos/documentos para la sucursal: " + sucursal);
        }
        
        List<ArqueoDTO> resultado = new ArrayList<>();
        
        // Para cada producto/documento, calcular métricas
        for (AtvffPds pds : productosSucursal) {
            String producto = pds.getXscopr();
            String documento = pds.getXscodo();
            
            // Obtener descripción del producto/documento
            AtvffPdo pdo = atvffpdoRepository.findByXpcoprAndXpcodo(producto, documento);
            if (pdo == null) {
                continue; // Si no existe descripción, pasar al siguiente
            }
            
            // Obtener frecuencia de arqueo
            AtvffFre fre = atvffFreRepository.findById_FxCoprAndId_FxCodo(producto, documento);
            if (fre == null) {
                continue; // Si no existe frecuencia, pasar al siguiente
            }
            
            // Determinar mes inicial y final según tipo de frecuencia
            int mesIni = mes;
            int mesFin = mes;
            
            if ("T".equals(fre.getFxFrar())) { // Trimestral
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
            } else if ("S".equals(fre.getFxFrar())) { // Semestral
                if (mes >= 1 && mes <= 6) {
                    mesIni = 1;
                    mesFin = 6;
                } else {
                    mesIni = 7;
                    mesFin = 12;
                }
            } else if ("A".equals(fre.getFxFrar())) { // Anual
                mesIni = 1;
                mesFin = 12;
            }
            
            LocalDate fechaInicio = LocalDate.of(ano, mesIni, 1);
            LocalDate fechaFin = LocalDate.of(ano, mesFin, YearMonth.of(ano, mesFin).lengthOfMonth());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaInicioStr = fechaInicio.format(formatter);
            String fechaFinStr = fechaFin.format(formatter);
            
            // Obtener arqueos realizados
            List<Atvffarq> arqueos = atvffarqRepository.findArqueosByProductoDocumentoAndPeriodo(
                    sucursal, producto, documento, fechaInicioStr, fechaFinStr);
            
            // Obtener arqueos cuadrados
            Integer arqueosCuadrados = atvffarqRepository.countArqueosCuadradosByProductoDocumentoAndPeriodo(
                    sucursal, producto, documento, fechaInicioStr, fechaFinStr);
            
            // Calcular porcentajes
            int arqProgramados = fre.getFxDifr();
            int arqEjecutados = arqueos.size();
            int cumplimiento = calcularPorcentajes.calcularPorcentajeCumplimiento(arqEjecutados, arqProgramados);
            int calidad = calcularPorcentajes.calcularPorcentajeCalidad(arqueosCuadrados, arqEjecutados);
            
            // Crear DTO y añadir a resultados
            ArqueoDTO arqueoDTO = new ArqueoDTO();
            arqueoDTO.setXpcopr(producto);
            arqueoDTO.setXpcodo(documento);
            arqueoDTO.setXpdsdo(pdo.getXpdsdo());
            arqueoDTO.setFxfrar(fre.getFxFrar());
            arqueoDTO.setProg(arqProgramados);
            arqueoDTO.setEjec(arqEjecutados);
            arqueoDTO.setCump(cumplimiento);
            arqueoDTO.setCal(calidad);
            arqueoDTO.setOpt("");
            
            resultado.add(arqueoDTO);
        }
        
        // Si el objeto pageable no es paginado, devolver la lista completa sin recortar
        if (pageable.isUnpaged()) {
            return new PageImpl<>(resultado, pageable, resultado.size());
        }
        
        // Aplicar la paginación manualmente al resultado de tipo List
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultado.size());
        List<ArqueoDTO> content = resultado.subList(start, end);
        
        return new PageImpl<>(content, pageable, resultado.size());
    }



	@Override
	public ArqueoTotalesDTO obtenerTotales(String usuario, Integer sucursal, Integer ano, Integer mes) {
		List<ArqueoDTO> arqueos = obtenerArqueosPorSucursal(usuario, ano, mes, sucursal, Pageable.unpaged()).getContent(); // Usuario por defecto
	    
	    if (arqueos.isEmpty()) {
	        return new ArqueoTotalesDTO(0, 0, 0, 0, sucursal, ano.toString() + String.format("%02d", mes));
	    }
	    
	    int totalProg = 0;
	    int totalEjec = 0;
	    int totalCump = 0;
	    int totalCal = 0;
	    
	    for (ArqueoDTO arqueo : arqueos) {
	        totalProg += arqueo.getProg();
	        totalEjec += arqueo.getEjec();
	        totalCump += arqueo.getCump();
	        totalCal += arqueo.getCal();
	    }
	    
	    // Calcular promedios
	    int promedioCump = !arqueos.isEmpty() ? totalCump / arqueos.size() : 0;
	    int promedioCal = !arqueos.isEmpty() ? totalCal / arqueos.size() : 0;
	    
	    return new ArqueoTotalesDTO(
	            totalProg, 
	            totalEjec, 
	            promedioCump, 
	            promedioCal, 
	            sucursal, 
	            ano.toString() + String.format("%02d", mes)
	    );
	}



	@Override
	public ArqueoResumenDTO procesarArqueo(String usuario, String pro, String doc, String des, Integer sucursal,
			String nomSuc, Integer ano, Integer mes) {
		 // Verificar si el producto/documento existe
	    AtvffPdo pdo = atvffpdoRepository.findByXpcoprAndXpcodo(pro, doc);
	    if (pdo == null) {
	        throw new IllegalArgumentException("Producto/documento no encontrado: " + pro + "/" + doc);
	    }
	    
	    // Obtener frecuencia
	    AtvffFre fre = atvffFreRepository.findById_FxCoprAndId_FxCodo(pro, doc);
	    if (fre == null) {
	        throw new IllegalArgumentException("No hay configuración de frecuencia para: " + pro + "/" + doc);
	    }
	    
	    // Determinar mes inicial y final según tipo de frecuencia
	    int mesIni = mes;
	    int mesFin = mes;
	    
	    if ("T".equals(fre.getFxFrar())) { // Trimestral
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
	    } else if ("S".equals(fre.getFxFrar())) { // Semestral
	        if (mes >= 1 && mes <= 6) {
	            mesIni = 1;
	            mesFin = 6;
	        } else {
	            mesIni = 7;
	            mesFin = 12;
	        }
	    } else if ("A".equals(fre.getFxFrar())) { // Anual
	        mesIni = 1;
	        mesFin = 12;
	    }
	    
	    LocalDate fechaInicio = LocalDate.of(ano, mesIni, 1);
        LocalDate fechaFin = LocalDate.of(ano, mesFin, YearMonth.of(ano, mesFin).lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaInicioStr = fechaInicio.format(formatter);
        String fechaFinStr = fechaFin.format(formatter);
	    
	    // Obtener arqueos realizados
	    List<Atvffarq> arqueos = atvffarqRepository.findArqueosByProductoDocumentoAndPeriodo(
	            sucursal, pro, doc, fechaInicioStr, fechaFinStr);
	    
	    // Obtener arqueos cuadrados
	    Integer arqueosCuadrados = atvffarqRepository.countArqueosCuadradosByProductoDocumentoAndPeriodo(
	            sucursal, pro, doc, fechaInicioStr, fechaFinStr);
	    
	    // Calcular porcentajes
	    int arqProgramados = fre.getFxDifr();
	    int arqEjecutados = arqueos.size();
	    int cumplimiento = calcularPorcentajes.calcularPorcentajeCumplimiento(arqEjecutados, arqProgramados);
	    int calidad = calcularPorcentajes.calcularPorcentajeCalidad(arqueosCuadrados, arqEjecutados);
	    
	    // Obtener fecha del último arqueo
	    String fecha = "";
	    if (!arqueos.isEmpty()) {
	        //String ultimaFecha = arqueos.get(arqueos.size() - 1).getAqfear();
	        //fecha = ultimaFecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    	fecha = arqueos.get(arqueos.size() - 1).getAqfear();
	    }
	    
	    // Crear resumen
	    ArqueoResumenDTO resumen = new ArqueoResumenDTO();
	    resumen.setPro(pro);
	    resumen.setDoc(doc);
	    resumen.setDes(des);
	    resumen.setFecha(fecha);
	    resumen.setArqueos(arqEjecutados);
	    resumen.setCuadrados(arqueosCuadrados);
	    resumen.setCumplimiento(cumplimiento);
	    resumen.setCalidad(calidad);
	    
	    return resumen;
	}

}



