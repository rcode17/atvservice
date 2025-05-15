package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.AtvffoasRepository;
import com.bancolombia.pocatv.repository.AtvffPdsRepository;
import com.bancolombia.pocatv.repository.AtvffFreRepository;
import com.bancolombia.pocatv.repository.AtvffarqRepository;
import com.bancolombia.pocatv.repository.AtvffmdRepository;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffDsunRepository;
import com.bancolombia.pocatv.service.AtvffdsunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AtvffdsunServiceImpl implements AtvffdsunService {
private static final Logger logger = LoggerFactory.getLogger(AtvffdsunServiceImpl.class);
    
    @Autowired
    private AtvffoasRepository atvffoasRepository;
    
    @Autowired
    private AtvffPdsRepository atvffpdsRepository;
    
    @Autowired
    private AtvffFreRepository atvfffreRepository;
    
    @Autowired
    private AtvffarqRepository atvffarqRepository;
    
    @Autowired
    private AtvffmdRepository atvffmdRepository;
    
    @Autowired
    private AtvffPdoRepository atvffpdoRepository;
    
    @Autowired
    private AtvffDsunRepository atvffdsunRepository;

    @Override
    @Transactional
    public void procesarActualizacion(Integer mes, Integer ano, String usuario) {
        logger.info("Iniciando proceso de actualización para mes: {} año: {} usuario: {}", mes, ano, usuario);
        
        try {
            // Eliminar registros existentes
            eliminarRegistros(mes, ano);
            
            // Proceso principal
            procesoPrincipal(mes, ano);
            
            logger.info("Proceso de actualización completado exitosamente");
        } catch (Exception e) {
            logger.error("Error en el proceso de actualización: {}", e.getMessage(), e);
            throw new RuntimeException("Error en el proceso de actualización: " + e.getMessage(), e);
        }
    }
    
    private void eliminarRegistros(Integer mes, Integer ano) {
        logger.debug("Eliminando registros existentes para mes: {} año: {}", mes, ano);
        atvffdsunRepository.deleteByDnanoAndDnmes(ano, mes);
    }
    
    private void procesoPrincipal(Integer mes, Integer ano) {
        logger.debug("Iniciando proceso principal para mes: {} año: {}", mes, ano);
        
        List<Atvffoas> registrosOas = atvffoasRepository.findByIdOamesAndIdOaano(mes, ano);
        logger.info("Se encontraron {} registros en ATVFFOAS", registrosOas.size());
        
        for (Atvffoas oas : registrosOas) {
            Integer suc = oas.getId().getOaxnnmky();
            logger.debug("Procesando sucursal: {}", suc);
            traerProductos(suc, mes, ano, oas);
        }
    }
    
    private void traerProductos(Integer suc, Integer mes, Integer ano, Atvffoas oas) {
        List<AtvffPds> productosSucursal = atvffpdsRepository.findByIdXscosu(suc);
        logger.debug("Se encontraron {} productos para la sucursal: {}", productosSucursal.size(), suc);
        
        for (AtvffPds pds : productosSucursal) {
            String pro = pds.getXscopr();
            String doc = pds.getXscodo();
            
            logger.debug("Procesando producto: {} documento: {}", pro, doc);
            
            // Inicializar mes
            Map<String, Object> datosMes = inicializarMes(pro, doc, mes, ano, suc);
            
            if (datosMes != null) {
                // Crear o actualizar registro en ATVFFDSUN
                AtvffDsun dsun = new AtvffDsun();
                AtvffDsunId dsunId = new AtvffDsunId();
                dsunId.setDnano(ano);
                dsunId.setDnmes(mes);
                dsunId.setDnxnnmky(suc);
                dsun.setId(dsunId);
                
                dsun.setDncumplit(oas.getOacumpli());
                dsun.setDncalidt(oas.getOacalid());
                dsun.setDnnombre(oas.getOanombre());
                
                // Inicializar todos los campos con "NA"
                inicializarCampos(dsun);
                
                // Guardar el registro
                AtvffDsun existente = atvffdsunRepository.findByDnanoAndDnmesAndDnxnnmky(ano, mes, suc);
                if (existente == null) {
                    atvffdsunRepository.save(dsun);
                    logger.debug("Registro creado para sucursal: {}", suc);
                } else {
                    dsun = existente;
                    logger.debug("Registro existente encontrado para sucursal: {}", suc);
                }
                
                // Procesar producto
                procesarProducto(pro, doc, dsun, datosMes);
            } else {
                logger.warn("No se pudo inicializar mes para producto: {} documento: {}", pro, doc);
            }
        }
    }
    
    private Map<String, Object> inicializarMes(String pro, String doc, Integer mes, Integer ano, Integer suc) {
        Map<String, Object> resultado = new HashMap<>();
        
        AtvffFre fre = atvfffreRepository.findByFxCoprAndFxCodo2(pro, doc);
        if (fre == null) {
            logger.warn("No se encontró registro en ATVFFFRE para producto: {} documento: {}", pro, doc);
            return null;
        }
        
        String tipo = fre.getFxFrar();
        Integer arqpro = fre.getFxDifr();
        Integer mesini = 0;
        Integer mesfin = 0;
        
        // Determinar rango de meses según tipo
        if ("T".equals(tipo)) {
            // Trimestral
            if (mes >= 1 && mes <= 3) {
                mesini = 1;
                mesfin = mesini + 2;
            } else if (mes >= 4 && mes <= 6) {
                mesini = 4;
                mesfin = mesini + 2;
            } else if (mes >= 7 && mes <= 9) {
                mesini = 7;
                mesfin = mesini + 2;
            } else {
                mesini = 10;
                mesfin = mesini + 2;
            }
        } else if ("S".equals(tipo)) {
            // Semestral
            if (mes >= 1 && mes <= 6) {
                mesini = 1;
                mesfin = mesini + 5;
            } else {
                mesini = 7;
                mesfin = mesini + 5;
            }
        } else if ("A".equals(tipo)) {
            // Anual
            mesini = 1;
            mesfin = mesini + 11;
        } else {
            // Mensual
            mesini = mes;
            mesfin = mes;
        }
        
        logger.debug("Tipo: {}, arqpro: {}, mesini: {}, mesfin: {}", tipo, arqpro, mesini, mesfin);
        
        resultado.put("tipo", tipo);
        resultado.put("arqpro", arqpro);
        resultado.put("mesini", mesini);
        resultado.put("mesfin", mesfin);
        
        // Evaluar arqueos
        Map<String, Object> evaluacion = evaluarArqueos(suc, pro, doc, ano, mesini, mesfin, arqpro);
        resultado.putAll(evaluacion);
        
        // Calcular fórmulas
        Integer cum = calcularCumplimiento(evaluacion);
        Integer cal = calcularCalidad(evaluacion);
        
        resultado.put("cum", cum);
        resultado.put("cal", cal);
        
        logger.debug("Cumplimiento: {}, Calidad: {}", cum, cal);
        
        return resultado;
    }
    
    private Map<String, Object> evaluarArqueos(Integer suc, String pro, String doc, Integer ano, Integer mesini, Integer mesfin, Integer arqpro) {
        Map<String, Object> resultado = new HashMap<>();
        
        // Obtener información del archivo MD
        short mesfinShort = mesfin.shortValue();
        Atvffmd md = atvffmdRepository.findByCompositeId2(pro, doc, ano, mesfinShort);
        if (md == null) {
            logger.warn("No se encontró registro en ATVFFMD para producto: {} documento: {} año: {} mes: {}", pro, doc, ano, mesfin);
            return resultado;
        }
        
        Integer diafin = md.getMddia() != null ? md.getMddia().intValue() : null;
        Integer rangodiai = (diafin - md.getMdrang1()) + 1;
        
        // Inicializar contadores
        Integer arqueos = 0;
        Integer arqeje = 0;
        Integer arqfin = 0;
        Integer cuadrado = 0;
        String res = "";
        String jus = "";
        Integer diaini = 0;
        Integer dia1 = 0;
        Integer dia2 = 0;
        
        // Buscar arqueos en el rango de fechas
        LocalDate fechaInicioLoc = LocalDate.of(ano, mesini, 1);
        LocalDate fechaFinLoc = LocalDate.of(ano, mesfin, diafin);
        String fechaInicio = fechaInicioLoc.toString();
        String fechaFin = fechaFinLoc.toString();
        
        List<Atvffarq> arqueosList = atvffarqRepository.findBySucursalAndProductoAndDocumentoAndFechaBetween(suc, pro, doc, fechaInicio, fechaFin);
        logger.debug("Se encontraron {} arqueos para sucursal: {} producto: {} documento: {}", arqueosList.size(), suc, pro, doc);
        
        for (Atvffarq arq : arqueosList) {
            String fecha = arq.getAqfear();
            // Convertir la cadena a un objeto LocalDate
            LocalDate fechaLocalDate = LocalDate.parse(fecha);
            dia1 = fechaLocalDate.getDayOfMonth();
            
            if (dia1 > dia2) {
                if (diaini == 0) {
                    diaini = dia1;
                }
                
                arqueos++;
                
                if (arqpro == 1 && arqueos > 0) {
                    if (dia1 >= rangodiai && dia1 <= diafin) {
                        arqfin = 1;
                        res = arq.getAqres();
                        jus = arq.getAqjust();
                        dia2 = dia1;
                    } else {
                        arqfin = 1;
                        res = arq.getAqres();
                        jus = arq.getAqjust();
                        dia2 = dia1;
                    }
                }
                
                if (arqpro > 1 && arqueos > 0) {
                    if (arqeje < (arqpro - 1)) {
                        arqeje++;
                        // Observar
                        observar(arq, cuadrado);
                        dia2 = dia1;
                    } else {
                        arqfin = 1;
                        dia2 = dia1;
                        res = arq.getAqres();
                        jus = arq.getAqjust();
                    }
                }
            }
        }
        
        // Procesar resultados finales
        if (dia2 >= rangodiai && dia2 <= diafin) {
            AtvffPdo pdo = atvffpdoRepository.findByXpcoprAndXpcodo(pro, doc);
            if (pdo != null) {
                if ("S".equals(pdo.getXpfeca())) {
                    if (dia2 == diafin) {
                        arqeje = arqeje + arqfin;
                        if ("C".equals(res) || "S".equals(jus)) {
                            cuadrado++;
                        }
                    }
                } else if ("N".equals(pdo.getXpfeca())) {
                    arqeje = arqeje + arqfin;
                    if ("C".equals(res) || "S".equals(jus)) {
                        cuadrado++;
                    }
                }
            }
        } else {
            if (arqpro == 1 && arqeje == 1) {
                arqeje = arqeje + arqfin;
                if ("C".equals(res) || "S".equals(jus)) {
                    cuadrado++;
                }
            }
        }
        
        resultado.put("arqpro", arqpro);
        resultado.put("arqeje", arqeje);
        resultado.put("cuadrado", cuadrado);
        
        logger.debug("Arqueos ejecutados: {}, Cuadrados: {}", arqeje, cuadrado);
        
        return resultado;
    }
    
    private void observar(Atvffarq arq, Integer cuadrado) {
        if ("C".equals(arq.getAqres()) || "S".equals(arq.getAqjust())) {
            cuadrado++;
        }
    }
    
    private Integer calcularCumplimiento(Map<String, Object> datos) {
        Integer arqpro = (Integer) datos.getOrDefault("arqpro", 0);
        Integer arqeje = (Integer) datos.getOrDefault("arqeje", 0);
        
        if (arqeje == 0 || arqpro == 0) {
            return 0;
        }
        
        return (int) Math.round((double) arqeje / arqpro * 100);
    }
    
    private Integer calcularCalidad(Map<String, Object> datos) {
        Integer cuadrado = (Integer) datos.getOrDefault("cuadrado", 0);
        Integer arqeje = (Integer) datos.getOrDefault("arqeje", 0);
        
        if (cuadrado == 0 || arqeje == 0) {
            return 0;
        }
        
        return (int) Math.round((double) cuadrado / arqeje * 100);
    }
    
    private void inicializarCampos(AtvffDsun dsun) {
        // Inicializar todos los campos con "NA"
        dsun.setDncumpli1("NA");
        dsun.setDncalid1("NA");
        dsun.setDncumpli2("NA");
        dsun.setDncalid2("NA");
        dsun.setDncumpli3("NA");
        dsun.setDncalid3("NA");
        dsun.setDncumpli4("NA");
        dsun.setDncalid4("NA");
        dsun.setDncumpli5("NA");
        dsun.setDncalid5("NA");
        dsun.setDncumpli6("NA");
        dsun.setDncalid6("NA");
        dsun.setDncumpli7("NA");
        dsun.setDncalid7("NA");
        dsun.setDncumpli8("NA");
        dsun.setDncalid8("NA");
        dsun.setDncumpli9("NA");
        dsun.setDncalid9("NA");
        dsun.setDncumpli10("NA");
        dsun.setDncalid10("NA");
        dsun.setDncumpli11("NA");
        dsun.setDncalid11("NA");
        dsun.setDncumpli12("NA");
        dsun.setDncalid12("NA");
        dsun.setDncumpli13("NA");
        dsun.setDncalid13("NA");
        dsun.setDncumpli14("NA");
        dsun.setDncalid14("NA");
        dsun.setDncumpli15("NA");
        dsun.setDncalid15("NA");
        dsun.setDncumpli16("NA");
        dsun.setDncalid16("NA");
        dsun.setDncumpli17("NA");
        dsun.setDncalid17("NA");
        dsun.setDncumpli18("NA");
        dsun.setDncalid18("NA");
        dsun.setDncumpli19("NA");
        dsun.setDncalid19("NA");
        dsun.setDncumpli20("NA");
        dsun.setDncalid20("NA");
        dsun.setDncumpli21("NA");
        dsun.setDncalid21("NA");
        dsun.setDncumpli22("NA");
        dsun.setDncalid22("NA");
        dsun.setDncumpli23("NA");
        dsun.setDncalid23("NA");
        dsun.setDncumpli24("NA");
        dsun.setDncalid24("NA");
        dsun.setDncumpli25("NA");
        dsun.setDncalid25("NA");
        dsun.setDncumpli26("NA");
        dsun.setDncalid26("NA");
        dsun.setDncumpli27("NA");
        dsun.setDncalid27("NA");
        dsun.setDncumpli28("NA");
        dsun.setDncalid28("NA");
        dsun.setDncumpli29("NA");
        dsun.setDncalid29("NA");
        dsun.setDncumpli30("NA");
        dsun.setDncalid30("NA");
        dsun.setDncumpli31("NA");
        dsun.setDncalid31("NA");
        dsun.setDncumpli32("NA");
        dsun.setDncalid32("NA");
        dsun.setDncumpli33("NA");
        dsun.setDncalid33("NA");
        dsun.setDncumpli34("NA");
        dsun.setDncalid34("NA");
        dsun.setDncumpli35("NA");
        dsun.setDncalid35("NA");
        dsun.setDncumpli36("NA");
        dsun.setDncalid36("NA");
        dsun.setDncumpli37("NA");
        dsun.setDncalid37("NA");
        dsun.setDncumpli38("NA");
        dsun.setDncalid38("NA");
        dsun.setDncumpli39("NA");
        dsun.setDncalid39("NA");
        dsun.setDncumpli40("NA");
        dsun.setDncalid40("NA");
        dsun.setDncumpli41("NA");
        dsun.setDncalid41("NA");
        dsun.setDncumpli42("NA");
        dsun.setDncalid42("NA");
        dsun.setDncumpli43("NA");
        dsun.setDncalid43("NA");
        dsun.setDncumpli44("NA");
        dsun.setDncalid44("NA");
        dsun.setDncumpli45("NA");
        dsun.setDncalid45("NA");
        dsun.setDncumpli46("NA");
        dsun.setDncalid46("NA");
        dsun.setDncumpli47("NA");
        dsun.setDncalid47("NA");
        dsun.setDncumpli48("NA");
        dsun.setDncalid48("NA");
        dsun.setDncumpli49("NA");
        dsun.setDncalid49("NA");
        dsun.setDncumpli50("NA");
        dsun.setDncalid50("NA");
        dsun.setDncumpli51("NA");
        dsun.setDncalid51("NA");
        dsun.setDncumpli52("NA");
        dsun.setDncalid52("NA");
        dsun.setDncumpli53("NA");
        dsun.setDncalid53("NA");
        dsun.setDncumpli54("NA");
        dsun.setDncalid54("NA");
        dsun.setDncumpli55("NA");
        dsun.setDncalid55("NA");
        dsun.setDncumpli56("NA");
        dsun.setDncalid56("NA");
        dsun.setDncumpli57("NA");
        dsun.setDncalid57("NA");
        dsun.setDncumpli58("NA");
        dsun.setDncalid58("NA");
        dsun.setDncumpli59("NA");
        dsun.setDncalid59("NA");
        dsun.setDncumpli60("NA");
        dsun.setDncalid60("NA");
        dsun.setDncumpli61("NA");
        dsun.setDncalid61("NA");
        dsun.setDncumpli62("NA");
        dsun.setDncalid62("NA");
        dsun.setDncumpli63("NA");
        dsun.setDncalid63("NA");
        dsun.setDncumpli64("NA");
        dsun.setDncalid64("NA");
        dsun.setDncumpli65("NA");
        dsun.setDncalid65("NA");
        dsun.setDncumpli66("NA");
        dsun.setDncalid66("NA");
        dsun.setDncumpli67("NA");
        dsun.setDncalid67("NA");
        dsun.setDncumpli68("NA");
        dsun.setDncalid68("NA");
        dsun.setDncumpli69("NA");
        dsun.setDncalid69("NA");
        dsun.setDncumpli70("NA");
        dsun.setDncalid70("NA");
    }
    
    private void procesarProducto(String pro, String doc, AtvffDsun dsun, Map<String, Object> datosMes) {
        Integer cum = (Integer) datosMes.get("cum");
        Integer cal = (Integer) datosMes.get("cal");
        String cumStr = cum.toString();
        String calStr = cal.toString();
        
        logger.debug("Procesando producto: {} documento: {} con cumplimiento: {} calidad: {}", pro, doc, cumStr, calStr);
        
        // Actualizar el campo correspondiente según el producto y documento
        if ("01".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli1(cumStr);
            dsun.setDncalid1(calStr);
        } else if ("01".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli2(cumStr);
            dsun.setDncalid2(calStr);
        } else if ("01".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli3(cumStr);
            dsun.setDncalid3(calStr);
        } else if ("02".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli4(cumStr);
            dsun.setDncalid4(calStr);
        } else if ("02".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli5(cumStr);
            dsun.setDncalid5(calStr);
        } else if ("02".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli6(cumStr);
            dsun.setDncalid6(calStr);
        } else if ("03".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli7(cumStr);
            dsun.setDncalid7(calStr);
        } else if ("04".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli8(cumStr);
            dsun.setDncalid8(calStr);
        } else if ("04".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli9(cumStr);
            dsun.setDncalid9(calStr);
        } else if ("04".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli10(cumStr);
            dsun.setDncalid10(calStr);
        } else if ("04".equals(pro) && "004".equals(doc)) {
            dsun.setDncumpli11(cumStr);
            dsun.setDncalid11(calStr);
        } else if ("04".equals(pro) && "005".equals(doc)) {
            dsun.setDncumpli12(cumStr);
            dsun.setDncalid12(calStr);
        } else if ("04".equals(pro) && "006".equals(doc)) {
            dsun.setDncumpli13(cumStr);
            dsun.setDncalid13(calStr);
        } else if ("04".equals(pro) && "007".equals(doc)) {
            dsun.setDncumpli14(cumStr);
            dsun.setDncalid14(calStr);
        } else if ("04".equals(pro) && "008".equals(doc)) {
            dsun.setDncumpli15(cumStr);
            dsun.setDncalid15(calStr);
        } else if ("04".equals(pro) && "009".equals(doc)) {
            dsun.setDncumpli16(cumStr);
            dsun.setDncalid16(calStr);
        } else if ("04".equals(pro) && "010".equals(doc)) {
            dsun.setDncumpli17(cumStr);
            dsun.setDncalid17(calStr);
        } else if ("05".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli18(cumStr);
            dsun.setDncalid18(calStr);
        } else if ("05".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli19(cumStr);
            dsun.setDncalid19(calStr);
        } else if ("06".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli20(cumStr);
            dsun.setDncalid20(calStr);
        } else if ("07".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli21(cumStr);
            dsun.setDncalid21(calStr);
        } else if ("08".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli22(cumStr);
            dsun.setDncalid22(calStr);
        } else if ("08".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli23(cumStr);
            dsun.setDncalid23(calStr);
        } else if ("08".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli24(cumStr);
            dsun.setDncalid24(calStr);
        } else if ("09".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli25(cumStr);
            dsun.setDncalid25(calStr);
        } else if ("09".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli26(cumStr);
            dsun.setDncalid26(calStr);
        } else if ("09".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli27(cumStr);
            dsun.setDncalid27(calStr);
        } else if ("09".equals(pro) && "004".equals(doc)) {
            dsun.setDncumpli28(cumStr);
            dsun.setDncalid28(calStr);
        } else if ("09".equals(pro) && "005".equals(doc)) {
            dsun.setDncumpli29(cumStr);
            dsun.setDncalid29(calStr);
        } else if ("11".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli30(cumStr);
            dsun.setDncalid30(calStr);
        } else if ("11".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli31(cumStr);
            dsun.setDncalid31(calStr);
        } else if ("13".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli32(cumStr);
            dsun.setDncalid32(calStr);
        } else if ("13".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli33(cumStr);
            dsun.setDncalid33(calStr);
        } else if ("13".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli34(cumStr);
            dsun.setDncalid34(calStr);
        } else if ("13".equals(pro) && "004".equals(doc)) {
            dsun.setDncumpli35(cumStr);
            dsun.setDncalid35(calStr);
        } else if ("13".equals(pro) && "005".equals(doc)) {
            dsun.setDncumpli36(cumStr);
            dsun.setDncalid36(calStr);
        } else if ("13".equals(pro) && "006".equals(doc)) {
            dsun.setDncumpli37(cumStr);
            dsun.setDncalid37(calStr);
        } else if ("13".equals(pro) && "007".equals(doc)) {
            dsun.setDncumpli38(cumStr);
            dsun.setDncalid38(calStr);
        } else if ("13".equals(pro) && "008".equals(doc)) {
            dsun.setDncumpli39(cumStr);
            dsun.setDncalid39(calStr);
        } else if ("13".equals(pro) && "009".equals(doc)) {
            dsun.setDncumpli40(cumStr);
            dsun.setDncalid40(calStr);
        } else if ("13".equals(pro) && "010".equals(doc)) {
            dsun.setDncumpli41(cumStr);
            dsun.setDncalid41(calStr);
        } else if ("13".equals(pro) && "011".equals(doc)) {
            dsun.setDncumpli42(cumStr);
            dsun.setDncalid42(calStr);
        } else if ("13".equals(pro) && "012".equals(doc)) {
            dsun.setDncumpli43(cumStr);
            dsun.setDncalid43(calStr);
        } else if ("13".equals(pro) && "013".equals(doc)) {
            dsun.setDncumpli44(cumStr);
            dsun.setDncalid44(calStr);
        } else if ("13".equals(pro) && "015".equals(doc)) {
            dsun.setDncumpli45(cumStr);
            dsun.setDncalid45(calStr);
        } else if ("13".equals(pro) && "016".equals(doc)) {
            dsun.setDncumpli46(cumStr);
            dsun.setDncalid46(calStr);
        } else if ("12".equals(pro) && "002".equals(doc)) {
            dsun.setDncumpli47(cumStr);
            dsun.setDncalid47(calStr);
        } else if ("12".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli48(cumStr);
            dsun.setDncalid48(calStr);
        } else if ("16".equals(pro) && "001".equals(doc)) {
            dsun.setDncumpli49(cumStr);
            dsun.setDncalid49(calStr);
        } else if ("16".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli50(cumStr);
            dsun.setDncalid50(calStr);
        } else if ("16".equals(pro) && "005".equals(doc)) {
            dsun.setDncumpli51(cumStr);
            dsun.setDncalid51(calStr);
        } else if ("16".equals(pro) && "013".equals(doc)) {
            dsun.setDncumpli52(cumStr);
            dsun.setDncalid52(calStr);
        } else if ("16".equals(pro) && "015".equals(doc)) {
            dsun.setDncumpli53(cumStr);
            dsun.setDncalid53(calStr);
        } else if ("04".equals(pro) && "011".equals(doc)) {
            dsun.setDncumpli54(cumStr);
            dsun.setDncalid54(calStr);
        } else if ("05".equals(pro) && "003".equals(doc)) {
            dsun.setDncumpli55(cumStr);
            dsun.setDncalid55(calStr);
        } else if ("05".equals(pro) && "004".equals(doc)) {
            dsun.setDncumpli56(cumStr);
            dsun.setDncalid56(calStr);
        } else {
            logger.warn("Combinación de producto: {} documento: {} no reconocida", pro, doc);
        }
        
        // Guardar los cambios
        atvffdsunRepository.save(dsun);
        logger.debug("Registro actualizado para sucursal: {} producto: {} documento: {}", dsun.getId().getDnxnnmky(), pro, doc);
    }

    @Override
    public List<Object> consultarDatos(Integer mes, Integer ano) {
        logger.info("Consultando datos para mes: {} año: {}", mes, ano);
        
        List<AtvffDsun> datos = atvffdsunRepository.findByDnanoAndDnmes(ano, mes);
        if (datos.isEmpty()) {
            logger.warn("No se encontraron registros para mes: {} año: {}", mes, ano);
            throw new IllegalArgumentException("No hay registros para el mes " + mes + " y año " + ano);
        }
        
        logger.info("Se encontraron {} registros", datos.size());
        
        // Transformar datos para la respuesta
        List<Object> resultado = new ArrayList<>();
        for (AtvffDsun dsun : datos) {
            Map<String, Object> item = new HashMap<>();
            item.put("sucursal", dsun.getId().getDnxnnmky());
            item.put("nombre", dsun.getDnnombre());
            item.put("cumplimientoTotal", dsun.getDncumplit());
            item.put("calidadTotal", dsun.getDncalidt());
            
            // Agregar datos de productos
            Map<String, Object> productos = new HashMap<>();
            
            agregarProductoSiExiste(productos, dsun.getDncumpli1(), dsun.getDncalid1(), "01-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli2(), dsun.getDncalid2(), "01-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli3(), dsun.getDncalid3(), "01-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli4(), dsun.getDncalid4(), "02-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli5(), dsun.getDncalid5(), "02-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli6(), dsun.getDncalid6(), "02-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli7(), dsun.getDncalid7(), "03-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli8(), dsun.getDncalid8(), "04-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli9(), dsun.getDncalid9(), "04-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli10(), dsun.getDncalid10(), "04-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli11(), dsun.getDncalid11(), "04-004");
            agregarProductoSiExiste(productos, dsun.getDncumpli12(), dsun.getDncalid12(), "04-005");
            agregarProductoSiExiste(productos, dsun.getDncumpli13(), dsun.getDncalid13(), "04-006");
            agregarProductoSiExiste(productos, dsun.getDncumpli14(), dsun.getDncalid14(), "04-007");
            agregarProductoSiExiste(productos, dsun.getDncumpli15(), dsun.getDncalid15(), "04-008");
            agregarProductoSiExiste(productos, dsun.getDncumpli16(), dsun.getDncalid16(), "04-009");
            agregarProductoSiExiste(productos, dsun.getDncumpli17(), dsun.getDncalid17(), "04-010");
            agregarProductoSiExiste(productos, dsun.getDncumpli18(), dsun.getDncalid18(), "05-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli19(), dsun.getDncalid19(), "05-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli20(), dsun.getDncalid20(), "06-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli21(), dsun.getDncalid21(), "07-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli22(), dsun.getDncalid22(), "08-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli23(), dsun.getDncalid23(), "08-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli24(), dsun.getDncalid24(), "08-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli25(), dsun.getDncalid25(), "09-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli26(), dsun.getDncalid26(), "09-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli27(), dsun.getDncalid27(), "09-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli28(), dsun.getDncalid28(), "09-004");
            agregarProductoSiExiste(productos, dsun.getDncumpli29(), dsun.getDncalid29(), "09-005");
            agregarProductoSiExiste(productos, dsun.getDncumpli30(), dsun.getDncalid30(), "11-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli31(), dsun.getDncalid31(), "11-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli32(), dsun.getDncalid32(), "13-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli33(), dsun.getDncalid33(), "13-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli34(), dsun.getDncalid34(), "13-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli35(), dsun.getDncalid35(), "13-004");
            agregarProductoSiExiste(productos, dsun.getDncumpli36(), dsun.getDncalid36(), "13-005");
            agregarProductoSiExiste(productos, dsun.getDncumpli37(), dsun.getDncalid37(), "13-006");
            agregarProductoSiExiste(productos, dsun.getDncumpli38(), dsun.getDncalid38(), "13-007");
            agregarProductoSiExiste(productos, dsun.getDncumpli39(), dsun.getDncalid39(), "13-008");
            agregarProductoSiExiste(productos, dsun.getDncumpli40(), dsun.getDncalid40(), "13-009");
            agregarProductoSiExiste(productos, dsun.getDncumpli41(), dsun.getDncalid41(), "13-010");
            agregarProductoSiExiste(productos, dsun.getDncumpli42(), dsun.getDncalid42(), "13-011");
            agregarProductoSiExiste(productos, dsun.getDncumpli43(), dsun.getDncalid43(), "13-012");
            agregarProductoSiExiste(productos, dsun.getDncumpli44(), dsun.getDncalid44(), "13-013");
            agregarProductoSiExiste(productos, dsun.getDncumpli45(), dsun.getDncalid45(), "13-015");
            agregarProductoSiExiste(productos, dsun.getDncumpli46(), dsun.getDncalid46(), "13-016");
            agregarProductoSiExiste(productos, dsun.getDncumpli47(), dsun.getDncalid47(), "12-002");
            agregarProductoSiExiste(productos, dsun.getDncumpli48(), dsun.getDncalid48(), "12-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli49(), dsun.getDncalid49(), "16-001");
            agregarProductoSiExiste(productos, dsun.getDncumpli50(), dsun.getDncalid50(), "16-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli51(), dsun.getDncalid51(), "16-005");
            agregarProductoSiExiste(productos, dsun.getDncumpli52(), dsun.getDncalid52(), "16-013");
            agregarProductoSiExiste(productos, dsun.getDncumpli53(), dsun.getDncalid53(), "16-015");
            agregarProductoSiExiste(productos, dsun.getDncumpli54(), dsun.getDncalid54(), "04-011");
            agregarProductoSiExiste(productos, dsun.getDncumpli55(), dsun.getDncalid55(), "05-003");
            agregarProductoSiExiste(productos, dsun.getDncumpli56(), dsun.getDncalid56(), "05-004");
            
            item.put("productos", productos);
            resultado.add(item);
        }
        
        return resultado;
    }
    
    private void agregarProductoSiExiste(Map<String, Object> productos, String cumplimiento, String calidad, String clave) {
        if (!"NA".equals(cumplimiento)) {
            Map<String, String> prod = new HashMap<>();
            prod.put("cumplimiento", cumplimiento);
            prod.put("calidad", calidad);
            productos.put(clave, prod);
        }
    }
}
