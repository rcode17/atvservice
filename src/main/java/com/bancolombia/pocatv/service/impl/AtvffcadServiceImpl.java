package com.bancolombia.pocatv.service.impl;


import com.bancolombia.pocatv.dto.Conciliacion2DTO;
import com.bancolombia.pocatv.dto.ConciliacionRequest;
import com.bancolombia.pocatv.model.Atvffcad;
import com.bancolombia.pocatv.model.AtvffcadId;
import com.bancolombia.pocatv.model.Atvfflog;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Atvffsal;
import com.bancolombia.pocatv.model.Gidbll;
import com.bancolombia.pocatv.repository.AtvffcadRepository;
import com.bancolombia.pocatv.repository.AtvfflogRepository;
import com.bancolombia.pocatv.repository.AtvffPdoRepository;
import com.bancolombia.pocatv.repository.AtvffUserRepository;
import com.bancolombia.pocatv.repository.AtvffsalRepository;
import com.bancolombia.pocatv.repository.GidbllRepository;
import com.bancolombia.pocatv.service.AtvffcadService;
import com.bancolombia.pocatv.utils.ValidateFechaUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AtvffcadServiceImpl implements AtvffcadService {

    @Autowired
    private AtvffPdoRepository atvffpdoRepository;
    
    @Autowired
    private AtvffsalRepository atvffsalRepository;
    
    @Autowired
    private GidbllRepository gidbllRepository;
    
    @Autowired
    private AtvffcadRepository atvffcadRepository;
    
    @Autowired
    private AtvffUserRepository atvffUserRepository;
    
    @Autowired
    private ValidateFechaUtil validateFechaUtil;
    
    @Autowired
    private AtvfflogRepository atvfflogRepository;

    @Override
    public void generarConciliacion(ConciliacionRequest request) {
        if (request.getDia() == null || request.getMes() == null || request.getAno() == null) {
            throw new IllegalArgumentException("Los parámetros día, mes y año son obligatorios");
        }
        
        // Formatear la fecha como se hace en el RPG
        String fecha = String.format("%04d%02d%02d", request.getAno(), request.getMes(), request.getDia());
        Long fechaLong = Long.parseLong(fecha);
        
        // Obtener todos los registros de productos/documentos
        List<AtvffPdo> productos = atvffpdoRepository.findAll();
        
        if (productos.isEmpty()) {
            throw new IllegalArgumentException("No hay registros de productos/documentos disponibles");
        }
        
        // Procesar cada producto
        for (AtvffPdo producto : productos) {
            // Calcular saldo de inventario
            BigDecimal saldoInventario = calcularSaldoInventario(producto.getXpcopr(), producto.getXpcodo(), fecha);
            
            // Calcular saldo contable
            BigDecimal saldoContable = calcularSaldoContable(fechaLong, new BigDecimal(producto.getXpcta().toString()));
                    
            // Calcular diferencia
            BigDecimal diferencia = saldoContable.subtract(saldoInventario);
            
            // Crear o actualizar registro en ATVFFCAD
            guardarConciliacion(request.getAno(), request.getMes(), request.getDia(), 
                    producto.getXpcopr(), producto.getXpcodo(), 
                    saldoInventario, saldoContable, diferencia);
        }
    }
    
    private BigDecimal calcularSaldoInventario(String codigoProducto, String codigoDocumento, String fecha) {
    	 Long fechaLong = Long.parseLong(fecha);
    	List<Atvffsal> saldos = atvffsalRepository.findBySatproAndSatdocAndSafech(
                codigoProducto, codigoDocumento, fechaLong);
        
        if (saldos.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return saldos.stream()
                .map(Atvffsal::getSadisp)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calcularSaldoContable(Long fechaLong, BigDecimal cuenta) {
        if (cuenta == null) {
            return BigDecimal.ZERO;
        }
        
        // Convertir Long a LocalDate
        String fechaStr = fechaLong.toString();
        int año = Integer.parseInt(fechaStr.substring(0, 4));
        int mes = Integer.parseInt(fechaStr.substring(4, 6));
        int dia = Integer.parseInt(fechaStr.substring(6, 8));
        
        LocalDate fecha = LocalDate.of(año, mes, dia);
        
        String cuentaStr = cuenta.toString();
        
        List<Gidbll> registrosContables = gidbllRepository.findByGxdtdyAndGxnoac(fecha, cuentaStr);
        
        if (registrosContables.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return registrosContables.stream()
                .map(Gidbll::getGxamdt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private void guardarConciliacion(Integer ano, Integer mes, Integer dia, 
                                    String codigoProducto, String codigoDocumento,
                                    BigDecimal saldoInventario, BigDecimal saldoContable, 
                                    BigDecimal diferencia) {
        
        // Buscar si ya existe un registro con la misma clave
        Optional<Atvffcad> conciliacionExistente = atvffcadRepository.findByCaanoAndCamesAndCadiaAndCacoprAndCacodo(
                ano, mes, dia, codigoProducto, codigoDocumento);
        
        Atvffcad conciliacion;
        
        if (conciliacionExistente.isPresent()) {
            // Actualizar registro existente
            conciliacion = conciliacionExistente.get();
            conciliacion.setCasinv(saldoInventario);
            conciliacion.setCascont(saldoContable);
            conciliacion.setCadif(diferencia);
        } else {
            // Crear nuevo registro
            conciliacion = new Atvffcad();
            conciliacion.setCaano(ano);
            conciliacion.setCames(mes);
            conciliacion.setCadia(dia);
            conciliacion.setCacopr(codigoProducto);
            conciliacion.setCacodo(codigoDocumento);
            conciliacion.setCasinv(saldoInventario);
            conciliacion.setCascont(saldoContable);
            conciliacion.setCadif(diferencia);
        }
        
        // Guardar o actualizar el registro
        atvffcadRepository.save(conciliacion);
    }
    
    
    
    @Override
    public List<Conciliacion2DTO> consultarConciliacionesPorFecha(Integer caano, Integer cames, Integer cadia, String xuUser) {
        
    	String mensaje=validateFechaUtil.validarMesYAnio(cames, caano);
    	if(!mensaje.isEmpty() && mensaje!=null && !mensaje.equals("")) {
    		throw new IllegalArgumentException(mensaje);
    	}
    	
    	List<Atvffcad> conciliaciones = atvffcadRepository.findByCaanoAndCamesAndCadia(caano, cames, cadia);
        List<Conciliacion2DTO> resultado = new ArrayList<>();
        
        if (conciliaciones.isEmpty()) {
            throw new IllegalArgumentException("No hay registros de conciliación para la fecha especificada");
        }
        
        for (Atvffcad conciliacion : conciliaciones) {
            // Verificar si el usuario tiene acceso al producto
            AtvffUser usuarioOpt = atvffUserRepository.findByXuUser(xuUser);
            
            
            boolean tieneAcceso = usuarioOpt.getXuCopr().stream()
                    .anyMatch(producto -> producto.getXpcopr().equals(conciliacion.getCacopr()));
            
           if(tieneAcceso) {
                Conciliacion2DTO dto = new Conciliacion2DTO();
                dto.setCacopr(conciliacion.getCacopr());
                dto.setCacodo(conciliacion.getCacodo());
                dto.setCasinv(conciliacion.getCasinv());
                dto.setCascont(conciliacion.getCascont());
                dto.setCadif(conciliacion.getCadif());
                dto.setXpdsdo(""); // Este dato no está en el modelo original
                dto.setOpt("");
                
                resultado.add(dto);
           } 
        }
        
        try {
        
        Atvfflog atvfflog= new Atvfflog();
        atvfflog.setLouser(xuUser);
        atvfflog.setLofecha(LocalDate.now());
        atvfflogRepository.save(atvfflog);
      
        } catch (Exception e) {
        	 throw new IllegalArgumentException(e);
        }
        return resultado;
    }
    
    @Override
    public boolean procesarConciliacion(Integer caano, Integer cames, Integer cadia, 
                                     String cacopr, String cacodo, 
                                     String xuUser) {
        // Verificar si existe la conciliación
        AtvffcadId id = new AtvffcadId(caano, cames, cadia, cacopr, cacodo);
        Optional<Atvffcad> conciliacionOpt = atvffcadRepository.findById(id);
        
        if (!conciliacionOpt.isPresent()) {
            throw new IllegalArgumentException("No se encontró la conciliación especificada");
        }
        
        // Verificar si el usuario tiene acceso
        Optional<AtvffUser> usuarioOpt = atvffUserRepository.findUserWithProductAccess(
            xuUser, cacopr);
        
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("El usuario no tiene acceso a este producto");
        }
        
        // Aquí iría la lógica para procesar la conciliación
        // En el sistema original se llama a ATVRCAS
        
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}