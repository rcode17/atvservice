package com.bancolombia.pocatv.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.dto.ConciliacionDTO;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.Atvffsal1Repository;
import com.bancolombia.pocatv.repository.GidblRepository;
import com.bancolombia.pocatv.repository.XbknamRepository;
import com.bancolombia.pocatv.service.ConciliacionService;

@Service
public class ConciliacionServiceImpl   implements ConciliacionService{

	@Autowired
	private XbknamRepository xbknamRepository;

	@Autowired
	private AtvffPdoRepository atvffpdoRepository;

	@Autowired
	private Atvffsal1Repository atvffsal1Repository;

	@Autowired
	private GidblRepository gidblRepository;
	
	
	@Override
    public List<ConciliacionDTO> obtenerConciliacion(String codigoProducto, String codigoDocumento, LocalDate fecha) {
        // Validar parámetros
        if (codigoProducto == null || codigoDocumento == null || fecha == null) {
            throw new IllegalArgumentException("Los parámetros codigoProducto, codigoDocumento y fecha son obligatorios");
        }
        
        // Obtener información del producto/documento usando los nombres de campos originales
        AtvffPdo productoDocumento = atvffpdoRepository.findByXpcoprAndXpcodo(
                codigoProducto, codigoDocumento);
        
        if (productoDocumento == null) {
            throw new IllegalArgumentException("No se encontró el producto/documento especificado");
        }
        
        // Obtener la cuenta contable asociada usando el nombre de campo original
        Long cuentaContable = productoDocumento.getXpcta().longValue();
        
        // Obtener todas las áreas operativas
        List<Xbknam> areasOperativas = xbknamRepository.findAll();
        
        // Lista para almacenar los resultados de conciliación
        List<ConciliacionDTO> resultados = new ArrayList<>();
        
        // Procesar cada área operativa
        for (Xbknam area : areasOperativas) {
            // Obtener el código de área
            Integer codigoArea = area.getXnnmky();
            
            // Calcular saldo de inventario
            LocalDate date = LocalDate.of(fecha.getYear(), fecha.getMonth(), fecha.getDayOfMonth());
            int safech = Integer.parseInt(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            BigDecimal saldoInventario = atvffsal1Repository.sumSadisp(
                    codigoArea, codigoProducto, codigoDocumento, safech);
            
            // Si no hay saldo de inventario, asignar cero
            if (saldoInventario == null) {
                saldoInventario = BigDecimal.ZERO;
            }
            
            // Calcular saldo contable
   
            BigDecimal saldoContable = gidblRepository.sumSaldoContablePorOficinaYCuentaYFecha(
                    codigoArea.toString(), cuentaContable.toString(), fecha);
            
            // Si no hay saldo contable, asignar cero
            if (saldoContable == null) {
                saldoContable = BigDecimal.ZERO;
            }
            
            // Calcular diferencia
            BigDecimal diferencia = saldoContable.subtract(saldoInventario);
            
            // Solo incluir en los resultados si hay datos (saldo inventario o contable)
            if (saldoInventario.compareTo(BigDecimal.ZERO) != 0 || saldoContable.compareTo(BigDecimal.ZERO) != 0) {
                // Crear objeto DTO con los resultados
                ConciliacionDTO conciliacion = new ConciliacionDTO(
                        codigoArea,
                        area.getXnname(),
                        saldoInventario,
                        saldoContable,
                        diferencia
                );
                
                // Agregar a la lista de resultados
                resultados.add(conciliacion);
            }
        }
        
        // Verificar si se encontraron resultados
        if (resultados.isEmpty()) {
            throw new IllegalArgumentException("No hay registros con datos válidos para los parámetros proporcionados");
        }
        
        return resultados;
    }

}
