package com.bancolombia.pocatv.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bancolombia.pocatv.dto.ArqueoDTO;
import com.bancolombia.pocatv.dto.ArqueoDescuadradoDTO;
import com.bancolombia.pocatv.dto.IncumplimientoDTO;
import com.bancolombia.pocatv.dto.ResponseDTO;
import com.bancolombia.pocatv.dto.ResponseIncumplimientoDTO;
import com.bancolombia.pocatv.dto.ArqueoResumenDTO;
import com.bancolombia.pocatv.dto.ArqueoTotalesDTO;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.Atvffcrd;

public interface AtvffarqService {
	Optional<Atvffarq> findByFields(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear);
	List<Atvffarq> buscarRangosInconsistentes(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear, String aqcedan);
	
	 List<ArqueoDescuadradoDTO> consultarArqueosDescuadrados(String usuario, Integer mes, Integer anno, String dom, Integer codArea);
	    
	    void gestionarArqueo(Integer codsuc, String codpro, String coddoc, String fecha, Double diferencia, String acceso);
	    
	    void justificarArqueo(Integer codsuc, String codpro, String coddoc, String fecha, Double diferencia, String acceso);
	    
	    void quitarJustificacion(Integer codsuc, String codpro, String coddoc, String fecha, Double diferencia, String acceso);
	    
	    
	    /**
	     * Procesa los arqueos descuadrados y los guarda en la tabla de rangos descuadrados
	     * @return Lista de rangos descuadrados procesados
	     */
	    List<Atvffcrd> procesarArqueosDescuadrados();
	    
	    /**
	     * Procesa los arqueos descuadrados a partir de una fecha específica
	     * @param fechaInicio Fecha desde la cual procesar
	     * @return Lista de rangos descuadrados procesados
	     */
	    List<Atvffcrd> procesarArqueosDescuadradosDesdeFecha(LocalDate fechaInicio);
	    
	    /**
	     * Obtiene la lista de áreas que incumplieron el arqueo en el último día hábil del mes.
	     * 
	     * @param usuario Identificador del usuario que realiza la consulta
	     * @return ResponseDTO con la lista de incumplimientos encontrados
	     */
	    ResponseIncumplimientoDTO<List<IncumplimientoDTO>> obtenerIncumplimientosUltimoDiaHabil(String usuario);
	    
	    /**
	     * Obtiene la lista de áreas que incumplieron el arqueo en el último día hábil 
	     * para un mes y año específicos.
	     * 
	     * @param mes Mes para el cual se desea consultar los incumplimientos (1-12)
	     * @param ano Año para el cual se desea consultar los incumplimientos
	     * @param usuario Identificador del usuario que realiza la consulta
	     * @return ResponseDTO con la lista de incumplimientos encontrados
	     */
	    ResponseIncumplimientoDTO<List<IncumplimientoDTO>> obtenerIncumplimientosPorMesAno(Integer mes, Integer ano, String usuario);
	    
	    /**
	     * Verifica si una sucursal específica ha incumplido con el arqueo en el último día hábil.
	     * 
	     * @param codigoSucursal Código de la sucursal a verificar
	     * @param codigoProducto Código del producto asociado al arqueo
	     * @param codigoDocumento Código del documento asociado al arqueo
	     * @param usuario Identificador del usuario que realiza la consulta
	     * @return ResponseDTO con el resultado de la verificación
	     */
	    ResponseIncumplimientoDTO<Boolean> verificarIncumplimientoSucursal(Integer codigoSucursal, 
	                                                        String codigoProducto, 
	                                                        String codigoDocumento, 
	                                                        String usuario);
	    
	    /**
	     * Obtiene el detalle de los arqueos realizados para una sucursal en un período específico.
	     * 
	     * @param codigoSucursal Código de la sucursal a consultar
	     * @param mes Mes para el cual se desea consultar los arqueos (1-12)
	     * @param ano Año para el cual se desea consultar los arqueos
	     * @param usuario Identificador del usuario que realiza la consulta
	     * @return ResponseDTO con el detalle de los arqueos realizados
	     */
	    ResponseIncumplimientoDTO<List<IncumplimientoDTO>> obtenerDetalleArqueosSucursal(Integer codigoSucursal, 
	                                                                      Integer mes, 
	                                                                      Integer ano, 
	                                                                      String usuario);
	    
	    
	    
	    List<ArqueoAnormalDTO> consultarArqueosAnormales(Integer ano, Integer mes, Integer codsuc);
	    
	    /**
	     * Obtiene la lista de arqueos por sucursal, año y mes
	     * @param usuario Usuario que realiza la consulta
	     * @param ano Año de consulta
	     * @param mes Mes de consulta
	     * @param sucursal Código de sucursal
	     * @return Lista de arqueos con sus métricas
	     */
	    Page<ArqueoDTO> obtenerArqueosPorSucursal(String usuario, Integer ano, Integer mes, Integer sucursal, Pageable pageable);
	    
	    /**
	     * Obtiene los totales de arqueos por sucursal, año y mes
	     * @param sucursal Código de sucursal
	     * @param ano Año de consulta
	     * @param mes Mes de consulta
	     * @return Totales calculados
	     */
	    ArqueoTotalesDTO obtenerTotales(String usuario, Integer sucursal, Integer ano, Integer mes);
	    
	    /**
	     * Procesa un arqueo seleccionado
	     * @param usuario Usuario que realiza la operación
	     * @param pro Código de producto
	     * @param doc Código de documento
	     * @param des Descripción
	     * @param sucursal Código de sucursal
	     * @param ano Año
	     * @param mes Mes
	     * @return Resumen del arqueo procesado
	     */
	    ArqueoResumenDTO procesarArqueo(String usuario, String pro, String doc, String des, Integer sucursal, 
	                                   String nomSuc, Integer ano, Integer mes);
	    
}
