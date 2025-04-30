package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancolombia.pocatv.model.Atvffchars;
import com.bancolombia.pocatv.model.Atvffchsal;
import com.bancolombia.pocatv.model.AtvffchsalId;
import com.bancolombia.pocatv.repository.AtvffcharsRepository;
import com.bancolombia.pocatv.repository.AtvffchsalRepository;
import com.bancolombia.pocatv.service.AtvffcharsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AtvffcharsServiceImpl implements AtvffcharsService {

    @Autowired
    private AtvffcharsRepository atvffcharsRepository;
    
    @Autowired
    private AtvffchsalRepository atvffchsalRepository;
    
    @Override
    @Transactional
    public List<Atvffchsal> procesarArqueos() {
        // Obtener todos los registros planos
        List<Atvffchars> registrosPlanos = atvffcharsRepository.findAll();
        
        // Procesar cada registro plano y convertirlo a formato estructurado
        for (Atvffchars registro : registrosPlanos) {
            String plano = registro.getPlano();
            
            // Validar que el registro tenga la longitud adecuada
            if (plano == null || plano.length() < 108) {
                continue;
            }
            
            // Extraer campos según la estructura definida en RPG
            Atvffchsal arqueo = new Atvffchsal();
            arqueo.setSdfech(plano.substring(0, 8).trim());
            arqueo.setSdtpro(plano.substring(8, 10).trim());
            arqueo.setSdtdoc(plano.substring(10, 13).trim());
            
            try {
                arqueo.setSddisp(new BigDecimal(plano.substring(13, 28).trim()));
            } catch (NumberFormatException e) {
                arqueo.setSddisp(BigDecimal.ZERO);
            }
            
            arqueo.setSdeofic(plano.substring(28, 31).trim());
            arqueo.setSdeofco(plano.substring(31, 36).trim());
            arqueo.setSdusu1(plano.substring(36, 38).trim());
            arqueo.setSdusu2(plano.substring(38, 58).trim());
            arqueo.setSdusu3(plano.substring(58, 78).trim());
            
            try {
                arqueo.setSdusu4(new BigDecimal(plano.substring(78, 93).trim()));
            } catch (NumberFormatException e) {
                arqueo.setSdusu4(BigDecimal.ZERO);
            }
            
            try {
                arqueo.setSdusu5(new BigDecimal(plano.substring(93, 108).trim()));
            } catch (NumberFormatException e) {
                arqueo.setSdusu5(BigDecimal.ZERO);
            }
            
            // Guardar el registro procesado
            atvffchsalRepository.save(arqueo);
        }
        
        return atvffchsalRepository.findAll();
    }
    
    @Override
    public List<Atvffchsal> obtenerTodosLosArqueos() {
        return atvffchsalRepository.findAll();
    }
    
    @Override
    public List<Atvffchsal> obtenerArqueosPorOficina(String oficina) {
        if (oficina == null || oficina.trim().isEmpty()) {
            throw new IllegalArgumentException("La oficina no puede estar vacía");
        }
        return atvffchsalRepository.findBySdeofic(oficina);
    }
    
    @Override
    public List<Atvffchsal> obtenerArqueosPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento no puede estar vacío");
        }
        return atvffchsalRepository.findBySdtdoc(documento);
    }
    
    @Override
    @Transactional
    public Atvffchsal guardarArqueo(Atvffchsal arqueo) {
        if (arqueo == null) {
            throw new IllegalArgumentException("El arqueo no puede ser nulo");
        }
        
        if (arqueo.getSdeofic() == null || arqueo.getSdeofic().trim().isEmpty() ||
            arqueo.getSdtdoc() == null || arqueo.getSdtdoc().trim().isEmpty()) {
            throw new IllegalArgumentException("La oficina y el documento son obligatorios");
        }
        
        return atvffchsalRepository.save(arqueo);
    }
    
    @Override
    @Transactional
    public void eliminarArqueo(String oficina, String documento) {
        if (oficina == null || oficina.trim().isEmpty() ||
            documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("La oficina y el documento son obligatorios");
        }
        
        AtvffchsalId id = new AtvffchsalId(oficina, documento);
        Optional<Atvffchsal> arqueo = atvffchsalRepository.findById(id);
        
        if (!arqueo.isPresent()) {
            throw new IllegalArgumentException("No existe un arqueo con la oficina " + oficina + " y documento " + documento);
        }
        
        atvffchsalRepository.deleteById(id);
    }
}
