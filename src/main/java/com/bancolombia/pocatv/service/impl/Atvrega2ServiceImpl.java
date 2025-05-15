package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.ReporteArqueoDTO;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.Atvrega2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class Atvrega2ServiceImpl implements Atvrega2Service {

    @Autowired
    private AtvffFreRepository atvfffreRepository;

    @Autowired
    private AtvffarqRepository atvffarqRepository;

    @Autowired
    private AtvffmdRepository atvffmdRepository;

    @Autowired
    private AtvffPdoRepository atvffpdoRepository;

    @Autowired
    private XbknamRepository xbknamRepository;

    @Autowired
    private XmgregRepository xmgregRepository;

    @Autowired
    private AtvffPdsRepository atvffpdsRepository;

    @Autowired
    private AtvffegaRepository atvffegaRepository;

    @Override
    @Transactional
    public List<ReporteArqueoDTO> generarReporteArqueos(String zFecha) {
        try {
            LocalDate hoy;
            if (zFecha != null && !zFecha.isEmpty()) {
                // Parsear zFecha para obtener la fecha
                hoy = LocalDate.parse(zFecha); // Asegúrate de que el formato sea correcto
            } else {
                hoy = LocalDate.now(); // Usar la fecha actual
            }

            limpiarDatosTemporales(hoy.getMonthValue(), hoy.getYear());

            // Inicializar variables
            int currentMonth = hoy.getMonthValue();
            int currentYear = hoy.getYear();
            int previousMonth = currentMonth == 1 ? 12 : currentMonth - 1;
            int previousYear = currentMonth == 1 ? currentYear - 1 : currentYear;

            // Obtener todos los productos-documentos
            List<AtvffPdo> productos = atvffpdoRepository.findAll();
            List<ReporteArqueoDTO> resultados = new ArrayList<>();

            // Procesar cada producto-documento
            for (AtvffPdo producto : productos) {
                String codigoProducto = producto.getXpcopr();
                String codigoDocumento = producto.getXpcodo();

                // Obtener frecuencia de arqueo
                AtvffFre frecuencia = atvfffreRepository.findById_FxCoprAndId_FxCodo(codigoProducto, codigoDocumento);
                if (frecuencia == null) continue;

                String tipoFrecuencia = frecuencia.getFxFrar();
                int diasFrecuencia = frecuencia.getFxDifr();

                // Verificar si aplica según el mes actual
                if (!aplicaFrecuencia(tipoFrecuencia, currentMonth)) continue;

                // Obtener sucursales que manejan este producto-documento
                List<Xbknam> sucursales = xbknamRepository.findAll();

                for (Xbknam sucursal : sucursales) {
                    Integer codigoSucursal = sucursal.getXnnmky();
                    String region = sucursal.getXncdmr();

                    // Verificar si la sucursal maneja este producto-documento
                    if (!atvffpdsRepository.existsByXscosuAndXscoprAndXscodo(codigoSucursal, codigoProducto, codigoDocumento)) {
                        continue;
                    }

                    // Calcular estadísticas para el mes anterior
                    Map<String, Integer> estadisticasAnteriores = calcularEstadisticas(
                            codigoSucursal, codigoProducto, codigoDocumento,
                            previousYear, previousMonth, diasFrecuencia
                    );

                    // Calcular estadísticas para el mes actual
                    Map<String, Integer> estadisticasActuales = calcularEstadisticas(
                            codigoSucursal, codigoProducto, codigoDocumento,
                            currentYear, currentMonth, diasFrecuencia
                    );

                    // Crear DTO con resultados
                    ReporteArqueoDTO reporte = new ReporteArqueoDTO();
                    reporte.setNombre(sucursal.getXnname());
                    reporte.setRegion(region);
                    reporte.setSucursal(codigoSucursal);
                    reporte.setCump11(estadisticasAnteriores.get("cumplimiento"));
                    reporte.setCump22(estadisticasActuales.get("cumplimiento"));
                    reporte.setInf11(estadisticasAnteriores.get("calidad"));
                    reporte.setInf22(estadisticasActuales.get("calidad"));

                    // Calcular variaciones
                    reporte.setVarc(calcularVariacion(estadisticasAnteriores.get("cumplimiento"), estadisticasActuales.get("cumplimiento")));
                    reporte.setVari(calcularVariacion(estadisticasAnteriores.get("calidad"), estadisticasActuales.get("calidad")));

                    // Establecer nombres de meses
                    reporte.setMesAnterior(obtenerNombreMes(previousMonth));
                    reporte.setMesActual(obtenerNombreMes(currentMonth));
                    reporte.setAnoAnterior(previousYear);
                    reporte.setAnoActual(currentYear);

                    resultados.add(reporte);
                    guardarResultadoTemporal(reporte, currentMonth, currentYear);
                }
            }
            return resultados;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void limpiarDatosTemporales(Integer mes, Integer ano) {
        atvffegaRepository.deleteByEgmesinAndEganoin(mes, ano);
    }

    private boolean aplicaFrecuencia(String tipoFrecuencia, int mes) {
        switch (tipoFrecuencia) {
            case "M": // Mensual
                return true;
            case "T": // Trimestral
                return mes == 3 || mes == 6 || mes == 9 || mes == 12;
            case "S": // Semestral
                return mes == 6 || mes == 12;
            case "A": // Anual
                return mes == 12;
            default:
                return false;
        }
    }

    private Map<String, Integer> calcularEstadisticas(Integer sucursal, String codigoProducto,
                                                      String codigoDocumento, Integer ano,
                                                      Integer mes, Integer diasFrecuencia) {
        Map<String, Integer> resultado = new HashMap<>();

        // Obtener arqueos del período
        String anoStr = String.valueOf(ano);
        String mesStr = mes < 10 ? "0" + mes : String.valueOf(mes);
        List<Atvffarq> arqueos = atvffarqRepository.findArqueosxMes(sucursal, codigoProducto, codigoDocumento, anoStr, mesStr);

        Optional<Atvffmd> mesInfo = atvffmdRepository.findByMdcoprAndMdcodoAndMdanoAndMdmes(codigoProducto, codigoDocumento, ano, mes.shortValue());
        int diaArqueo = mesInfo.map(Atvffmd::getMddia).map(Short::intValue).orElse(0);
        int rangoPermitido = mesInfo.map(Atvffmd::getMdrang1).map(Short::intValue).orElse(0);

        // Contar arqueos realizados y cuadrados
        int arqueosTotales = 0;
        int arqueosCuadrados = 0;

        for (Atvffarq arqueo : arqueos) {
            LocalDate fechaArqueo = LocalDate.parse(arqueo.getAqfear());
            if (fechaArqueo.getDayOfMonth() == diaArqueo ||
                    (fechaArqueo.getDayOfMonth() >= (diaArqueo - rangoPermitido) && fechaArqueo.getDayOfMonth() < diaArqueo)) {
                arqueosTotales++;
                if ("C".equals(arqueo.getAqres()) || "S".equals(arqueo.getAqjust())) {
                    arqueosCuadrados++;
                }
            }
        }

        // Calcular porcentaje de cumplimiento y calidad
        int porcentajeCumplimiento = diasFrecuencia > 0 ? (arqueosTotales * 100) / diasFrecuencia : 0;
        int porcentajeCalidad = arqueosTotales > 0 ? (arqueosCuadrados * 100) / arqueosTotales : 0;

        resultado.put("cumplimiento", porcentajeCumplimiento);
        resultado.put("calidad", porcentajeCalidad);

        return resultado;
    }

    private int calcularVariacion(Integer anterior, Integer actual) {
        if (anterior > 0) {
            return (actual - anterior) * 100 / anterior;
        } else {
            return -999; // Valor alternativo en caso de que anterior sea 0
        }
    }

    private String obtenerNombreMes(int mes) {
        String[] nombresMeses = {"Enero", "Feb", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agos", "Sept", "Oct", "Nov", "Dic"};
        return nombresMeses[mes - 1];
    }

    private void guardarResultadoTemporal(ReporteArqueoDTO reporte, Integer mes, Integer ano) {
        try {
            Atvffega resultado = new Atvffega();


            if (reporte.getCump11() != null) {
                String Cump11Str = reporte.getCump11().toString();
                if (reporte.getCump11().toString().length() > 3) {
                    Cump11Str = Cump11Str.substring(Cump11Str.length() - 3);
                }
                Integer Cump11Int = Integer.valueOf(Cump11Str);
                resultado.setEgcump11(Cump11Int);
            }

            if (reporte.getCump22() != null) {
                String Cump22Str = reporte.getCump22().toString();
                if (reporte.getCump22().toString().length() > 3) {
                    Cump22Str = Cump22Str.substring(Cump22Str.length() - 3);
                }
                Integer Cump22Int = Integer.valueOf(Cump22Str);
                resultado.setEgcump22(Cump22Int);
            }

            if (reporte.getVarc() != null) {
                String VarcStr = reporte.getVarc().toString();
                if (reporte.getVarc().toString().length() > 3) {
                    VarcStr = VarcStr.substring(VarcStr.length() - 3);
                }
                resultado.setEgvarc(VarcStr);
            }


            if (reporte.getInf11() != null) {
                String Inf11Str = reporte.getInf11().toString();
                if (reporte.getInf11().toString().length() > 3) {
                    Inf11Str = Inf11Str.substring(Inf11Str.length() - 3);
                }
                Integer Inf11Int = Integer.valueOf(Inf11Str);
                resultado.setEginf11(Inf11Int);
            }

            if (reporte.getInf22() != null) {
                String Inf22Str = reporte.getInf22().toString();
                if (reporte.getInf22().toString().length() > 3) {
                    Inf22Str = Inf22Str.substring(Inf22Str.length() - 3);
                }
                Integer Inf22Int = Integer.valueOf(Inf22Str);
                resultado.setEginf22(Inf22Int);
            }
            if (reporte.getVari() != null) {
                String VariStr = reporte.getVari().toString();
                if (VariStr.length() > 3) {
                    VariStr = VariStr.substring(VariStr.length() - 3);
                }
                resultado.setEgvari(VariStr);
            }

            resultado.setEgmesin(mes);
            resultado.setEganoin(ano);
            resultado.setEgnombre(reporte.getNombre());
            resultado.setEgsucursal(reporte.getSucursal());


            resultado.setEgantc("");  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEgactc("");  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEganti("");  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEgacti("");  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEganonc(0);  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEganoni(0);  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEganocc(0);  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEganoci(0);  // Asignar valor predeterminado o desde el reporte si es necesario
            resultado.setEgind07("0"); // Asignar valor como en la lógica original


            atvffegaRepository.save(resultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}