package com.bancolombia.pocatv.service.impl;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancolombia.pocatv.dto.EstadisticaProductoDTO;
import com.bancolombia.pocatv.dto.ResumenEstadisticasDTO;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.AtvffIap;
import com.bancolombia.pocatv.model.AtvffIap.AtvffIapId;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import com.bancolombia.pocatv.repository.AtvffIapRepository;
import com.bancolombia.pocatv.repository.AtvffFreRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.AtvffUserService;
import com.bancolombia.pocatv.service.AtvriapService;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;

@Service
public class AtvriapServiceImpl implements AtvriapService {
	
	private static final Logger logger = LoggerFactory.getLogger(AtvriapServiceImpl.class);
    
    // Número de productos a procesar en cada lote
    private static final int BATCH_SIZE = 100;
    
    // Número de hilos para procesamiento paralelo (basado en los núcleos disponibles)
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    @Autowired
    private AtvffUserRepository atvffUserRepository;
    
    @Autowired
    private AtvffIapRepository atvffIapRepository;
    
    @Autowired
    private XbknamRepository xbkNamRepository;
    
    @Autowired
    private AtvffUserService atvffUserService;
    
    @Autowired
    private AtvffPdoRepository atvffpdoRepository;
    
    @Autowired
    private AtvffFreRepository atvfffreRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Override
    public ResumenEstadisticasDTO obtenerEstadisticasCalidad(String user, Integer ano) {
        // Verificar usuario
        AtvffUser usuario = atvffUserRepository.findByXuUser(user);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + usuario);
        }
        
        
        // Si el dominio es 03, redirigir a otro servicio (simulando el CALL a ATVRIAS)
        if ("03".equals(usuario.getXuDom())) {
            // En un caso real, aquí se llamaría a otro servicio
            throw new UnsupportedOperationException("Redirección a ATVRIAS no implementada");
        }
        
        // Obtener datos de sucursal
        Set<Xbknam> areas = atvffUserService.obtenerAreasUsuario(usuario.getXuUser());
        for (Xbknam area : areas) {
            String nombre = area.getXnname(); // o el método correcto
            System.out.println(nombre);
        }
        //Xbknam sucursal = xbkNamRepository.findByXnnmky(usuario.);
        //String nombreSucursal = sucursal != null ? areas.getXnName() : "Sucursal Desconocida";
        
        // Obtener estadísticas
        List<AtvffIap> estadisticasDB = atvffIapRepository.findByAnoOrderByCodproAndCoddoc(ano);
        
        if (estadisticasDB.isEmpty()) {
            throw new IllegalArgumentException("No hay datos para el año: " + ano);
        }
        
        // Convertir a DTOs
        List<EstadisticaProductoDTO> estadisticas = new ArrayList<>();
        
        // Variables para totales
        int eneTotal = 0, febTotal = 0, marTotal = 0, abrTotal = 0, mayTotal = 0, junTotal = 0;
        int julTotal = 0, agoTotal = 0, sepTotal = 0, octTotal = 0, novTotal = 0, dicTotal = 0;
        
        for (AtvffIap iap : estadisticasDB) {
            EstadisticaProductoDTO dto = new EstadisticaProductoDTO();
            dto.setCodPro(iap.getId().getIpCodpro());
            dto.setCodDoc(iap.getId().getIpCoddoc());
            dto.setDocument(iap.getIpDocument());
            dto.setPromA(iap.getIpProma());
            dto.setEnero(iap.getIpEnero());
            dto.setFeb(iap.getIpFeb());
            dto.setMarzo(iap.getIpMarzo());
            dto.setAbril(iap.getIpAbril());
            dto.setMayo(iap.getIpMayo());
            dto.setJunio(iap.getIpJunio());
            dto.setJulio(iap.getIpJulio());
            dto.setAgosto(iap.getIpAgosto());
            dto.setSep(iap.getIpSep());
            dto.setOctubre(iap.getIpOctubre());
            dto.setNov(iap.getIpNov());
            dto.setDic(iap.getIpDic());
            
            estadisticas.add(dto);
            
            // Acumular totales
            eneTotal += iap.getIpEnero();
            febTotal += iap.getIpFeb();
            marTotal += iap.getIpMarzo();
            abrTotal += iap.getIpAbril();
            mayTotal += iap.getIpMayo();
            junTotal += iap.getIpJunio();
            julTotal += iap.getIpJulio();
            agoTotal += iap.getIpAgosto();
            sepTotal += iap.getIpSep();
            octTotal += iap.getIpOctubre();
            novTotal += iap.getIpNov();
            dicTotal += iap.getIpDic();
        }
        
        // Crear objeto de respuesta
        ResumenEstadisticasDTO resumen = new ResumenEstadisticasDTO();
        resumen.setEstadisticas(estadisticas);
        resumen.setEneTotal(eneTotal);
        resumen.setFebTotal(febTotal);
        resumen.setMarTotal(marTotal);
        resumen.setAbrTotal(abrTotal);
        resumen.setMayTotal(mayTotal);
        resumen.setJunTotal(junTotal);
        resumen.setJulTotal(julTotal);
        resumen.setAgoTotal(agoTotal);
        resumen.setSepTotal(sepTotal);
        resumen.setOctTotal(octTotal);
        resumen.setNovTotal(novTotal);
        resumen.setDicTotal(dicTotal);
        resumen.setContadorProductos(estadisticas.size());
        //resumen.setSucursal(nombreSucursal);
        resumen.setAno(ano);
        
        return resumen;
    }
    
    @Override
    @Transactional
    public List<AtvffIap> generarArchivoCalidadInformacion(int ano) {
        logger.info("Iniciando generación de archivo de calidad para el año: {}", ano);
        
        // Obtener todos los productos-documentos
        List<AtvffPdo> productos = atvffpdoRepository.findAll();
        logger.info("Total de productos a procesar: {}", productos.size());
        
        // Cargar todas las frecuencias en memoria para evitar múltiples consultas
        Map<String, AtvffFre> frecuenciasCache = cargarFrecuencias();
        
        // Dividir productos en lotes para procesamiento por lotes
        List<List<AtvffPdo>> lotes = dividirEnLotes(productos, BATCH_SIZE);
        
        // Crear un pool de hilos para procesamiento paralelo
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        try {
            // Procesar cada lote en paralelo
            List<CompletableFuture<List<AtvffIap>>> futuros = new ArrayList<>();
            
            for (List<AtvffPdo> lote : lotes) {
                CompletableFuture<List<AtvffIap>> futuro = CompletableFuture.supplyAsync(() -> 
                    procesarLoteProductos(lote, ano, frecuenciasCache), executor);
                futuros.add(futuro);
            }
            
            // Esperar a que todos los lotes terminen y combinar resultados
            List<AtvffIap> resultados = new ArrayList<>();
            for (CompletableFuture<List<AtvffIap>> futuro : futuros) {
                resultados.addAll(futuro.join());
            }
            
            logger.info("Procesamiento completado. Total de registros generados: {}", resultados.size());
            
            // Guardar todos los registros en un solo lote
            return atvffIapRepository.saveAll(resultados);
            
        } finally {
            executor.shutdown();
        }
    }
    
    /**
     * Carga todas las frecuencias en un mapa para acceso rápido
     */
    private Map<String, AtvffFre> cargarFrecuencias() {
        List<AtvffFre> todasLasFrecuencias = atvfffreRepository.findAll();
        Map<String, AtvffFre> cache = new HashMap<>();
        
        for (AtvffFre frecuencia : todasLasFrecuencias) {
            String clave = frecuencia.getId().getFxCopr() + "-" + frecuencia.getId().getFxCodo();
            cache.put(clave, frecuencia);
        }
        
        return cache;
    }
    
    /**
     * Divide una lista en sublistas de tamaño especificado
     */
    private <T> List<List<T>> dividirEnLotes(List<T> lista, int tamanoLote) {
        List<List<T>> lotes = new ArrayList<>();
        for (int i = 0; i < lista.size(); i += tamanoLote) {
            lotes.add(new ArrayList<>(lista.subList(i, Math.min(i + tamanoLote, lista.size()))));
        }
        return lotes;
    }
    
    /**
     * Procesa un lote de productos y genera registros de calidad
     */
    private List<AtvffIap> procesarLoteProductos(List<AtvffPdo> lote, int ano, Map<String, AtvffFre> frecuenciasCache) {
        List<AtvffIap> resultados = new ArrayList<>();
        
        // Preparar IDs para consulta en lote
        List<AtvffIapId> ids = new ArrayList<>();
        for (AtvffPdo producto : lote) {
            AtvffIapId id = new AtvffIapId();
            id.setIpAnos(ano);
            id.setIpCodpro(Integer.parseInt(producto.getXpcopr()));
            id.setIpCoddoc(Integer.parseInt(producto.getXpcodo()));
            ids.add(id);
        }
        
        // Obtener registros existentes en un solo query
        List<AtvffIap> registrosExistentes = atvffIapRepository.findAllById(ids);
        Map<String, AtvffIap> mapaRegistros = new HashMap<>();
        
        for (AtvffIap registro : registrosExistentes) {
            String clave = registro.getId().getIpAnos() + "-" + 
                          registro.getId().getIpCodpro() + "-" + 
                          registro.getId().getIpCoddoc();
            mapaRegistros.put(clave, registro);
        }
        
        // Procesar cada producto en el lote
        for (AtvffPdo producto : lote) {
            try {
                String codigoProducto = producto.getXpcopr();
                String codigoDocumento = producto.getXpcodo();
                String clave = ano + "-" + Integer.parseInt(codigoProducto) + "-" + Integer.parseInt(codigoDocumento);
                
                AtvffIap registro = mapaRegistros.get(clave);
                
                if (registro == null) {
                    // Crear nuevo registro
                    registro = crearNuevoRegistro(producto, ano);
                }
                
                // Procesar el registro
                procesarRegistro(registro, producto, ano, frecuenciasCache);
                resultados.add(registro);
                
            } catch (Exception e) {
                logger.error("Error al procesar producto: {}", producto.getXpcopr(), e);
            }
        }
        
        return resultados;
    }
    
    /**
     * Crea un nuevo registro de calidad con valores iniciales
     */
    private AtvffIap crearNuevoRegistro(AtvffPdo producto, int ano) {
        AtvffIap registro = new AtvffIap();
        AtvffIapId id = new AtvffIapId();
        id.setIpAnos(ano);
        id.setIpCodpro(Integer.parseInt(producto.getXpcopr()));
        id.setIpCoddoc(Integer.parseInt(producto.getXpcodo()));
        registro.setId(id);
        registro.setIpDocument(producto.getXpdsdo());
        
        // Inicializar todos los meses con 0
        registro.setIpEnero(0);
        registro.setIpFeb(0);
        registro.setIpMarzo(0);
        registro.setIpAbril(0);
        registro.setIpMayo(0);
        registro.setIpJunio(0);
        registro.setIpJulio(0);
        registro.setIpAgosto(0);
        registro.setIpSep(0);
        registro.setIpOctubre(0);
        registro.setIpNov(0);
        registro.setIpDic(0);
        registro.setIpProma(0);
        
        return registro;
    }
    
    /**
     * Procesa un registro calculando la calidad para cada mes
     */
    private void procesarRegistro(AtvffIap registro, AtvffPdo producto, int ano, Map<String, AtvffFre> frecuenciasCache) {
        String codigoProducto = producto.getXpcopr();
        String codigoDocumento = producto.getXpcodo();
        
        // Verificar si existe frecuencia para este producto-documento
        String claveFrecuencia = codigoProducto + "-" + codigoDocumento;
        AtvffFre frecuencia = frecuenciasCache.get(claveFrecuencia);
        
        if (frecuencia == null) {
            // Si no hay frecuencia, mantener valores por defecto
            return;
        }
        
        // Obtener todos los arqueos del año para este producto-documento
        Map<Integer, List<Atvffarq>> arqueosPorMes = obtenerArqueosPorMes(codigoProducto, codigoDocumento, ano);
        
        // Procesar datos para cada mes
        int totalCalidad = 0;
        int mesesProcesados = 0;
        int mesActual = LocalDate.now().getMonthValue();
        int anoActual = Year.now().getValue();
        
        for (int mes = 1; mes <= 12; mes++) {
            // Verificar si el mes actual es mayor al mes actual del año en curso
            if (ano == anoActual && mes > mesActual) {
                asignarValorMes(registro, mes, 0);
                continue;
            }
            
            try {
                List<Atvffarq> arqueosMes = arqueosPorMes.getOrDefault(mes, Collections.emptyList());
                int calidadMes = calcularCalidadMesOptimizado(arqueosMes);
                
                // Asignar calidad al mes
                asignarValorMes(registro, mes, calidadMes);
                
                if (calidadMes > 0) {
                    totalCalidad += calidadMes;
                    mesesProcesados++;
                }
            } catch (Exception e) {
                logger.error("Error al calcular calidad para producto {}-{}, mes {}: {}", 
                        codigoProducto, codigoDocumento, mes, e.getMessage());
                asignarValorMes(registro, mes, 0);
            }
        }
        
        // Calcular promedio anual
        if (mesesProcesados > 0) {
            registro.setIpProma(totalCalidad / mesesProcesados);
        }
    }
    
    /**
     * Obtiene todos los arqueos del año para un producto-documento y los organiza por mes
     */
    private Map<Integer, List<Atvffarq>> obtenerArqueosPorMes(String codigoProducto, String codigoDocumento, int ano) {
        // Obtener todos los arqueos del año
        List<Atvffarq> arqueos = atvffarqRepository.findByAqcoprAndAqcodoAndAno(
                codigoProducto, codigoDocumento, String.valueOf(ano));
        
        // Organizar por mes
        Map<Integer, List<Atvffarq>> arqueosPorMes = new HashMap<>();
        
        for (Atvffarq arqueo : arqueos) {
            try {
                String fechaStr = arqueo.getAqfear();
                LocalDate fecha = LocalDate.parse(fechaStr);
                int mes = fecha.getMonthValue();
                
                if (!arqueosPorMes.containsKey(mes)) {
                    arqueosPorMes.put(mes, new ArrayList<>());
                }
                
                arqueosPorMes.get(mes).add(arqueo);
            } catch (Exception e) {
                logger.warn("Error al procesar fecha de arqueo: {}", arqueo.getAqfear());
            }
        }
        
        return arqueosPorMes;
    }
    
    /**
     * Calcula la calidad para un mes específico basado en los arqueos
     */
    private int calcularCalidadMesOptimizado(List<Atvffarq> arqueos) {
        if (arqueos == null || arqueos.isEmpty()) {
            return 0;
        }
        
        // Contar arqueos cuadrados
        long arqueosCuadrados = 0;
        for (Atvffarq arqueo : arqueos) {
            if (arqueo != null && 
                arqueo.getAqres() != null && 
                "C".equalsIgnoreCase(arqueo.getAqres().trim())) {
                arqueosCuadrados++;
            }
        }
        
        // Calcular calidad
        return (int) ((arqueosCuadrados * 100) / arqueos.size());
    }
    
    // Método auxiliar para asignar valor a un mes específico
    private void asignarValorMes(AtvffIap registro, int mes, int valor) {
        switch (mes) {
            case 1: registro.setIpEnero(valor); break;
            case 2: registro.setIpFeb(valor); break;
            case 3: registro.setIpMarzo(valor); break;
            case 4: registro.setIpAbril(valor); break;
            case 5: registro.setIpMayo(valor); break;
            case 6: registro.setIpJunio(valor); break;
            case 7: registro.setIpJulio(valor); break;
            case 8: registro.setIpAgosto(valor); break;
            case 9: registro.setIpSep(valor); break;
            case 10: registro.setIpOctubre(valor); break;
            case 11: registro.setIpNov(valor); break;
            case 12: registro.setIpDic(valor); break;
        }
    }
    
    private int calcularCalidadMes(String codigoProducto, String codigoDocumento, int ano, int mes) {
        // Obtener la frecuencia de arqueo para este producto-documento
        AtvffFreId atvffFreId = new AtvffFreId();
        atvffFreId.setFxCopr(codigoProducto);
        atvffFreId.setFxCodo(codigoDocumento);
        Optional<AtvffFre> frecuenciaOpt = atvfffreRepository.findById(atvffFreId);
        
        if (!frecuenciaOpt.isPresent()) {
            return 0;
        }
        
        AtvffFre frecuencia = frecuenciaOpt.get();
        String tipoFrecuencia = frecuencia.getFxFrar();
        int diasFrecuencia = frecuencia.getFxDifr();
        
        // Obtener información del mes
        LocalDate fechaInicioDate = LocalDate.of(ano, mes, 1);
        LocalDate fechaFinDate = fechaInicioDate.withDayOfMonth(fechaInicioDate.lengthOfMonth());
        // Convertir las fechas a String
        String fechaInicio = fechaInicioDate.toString();
        String fechaFin = fechaFinDate.toString();
        
        // Obtener arqueos para este producto-documento en el mes
        List<Atvffarq> arqueos = atvffarqRepository.findByProductoAndDocumentoAndFechaBetween(
                codigoProducto, codigoDocumento, fechaInicio, fechaFin);
        
        if (arqueos.isEmpty()) {
            return 0;
        }
        
        // Contar arqueos cuadrados
        long arqueosCuadrados = arqueos.stream()
                .filter(arqueo -> arqueo != null && 
                        arqueo.getAqres() != null && 
                        "C".equalsIgnoreCase(arqueo.getAqres().trim()))
                .count();
        
        // Calcular calidad
        if (arqueos.size() > 0) {
            return (int) ((arqueosCuadrados * 100) / arqueos.size());
        } else {
            return 0;
        }
    }
    
    @Override
    public AtvffIap obtenerCalidadInformacion(int ano, int codigoProducto, int codigoDocumento) {
        AtvffIapId id = new AtvffIapId();
        id.setIpAnos(ano);
        id.setIpCodpro(codigoProducto);
        id.setIpCoddoc(codigoDocumento);
        
        Optional<AtvffIap> resultado = atvffIapRepository.findById(id);
        
        if (resultado.isPresent()) {
            return resultado.get();
        } else {
            throw new IllegalArgumentException("No hay registros con datos válidos para los parámetros proporcionados.");
        }
    }
    
    @Override
    public AtvffIap guardarCalidadInformacion(AtvffIap atvffiap) {
        return atvffIapRepository.save(atvffiap);
    }
    
}