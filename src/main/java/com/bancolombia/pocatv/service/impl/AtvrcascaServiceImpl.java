package com.bancolombia.pocatv.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancolombia.pocatv.model.Atvffcasal;
import com.bancolombia.pocatv.model.Atvlfarq;
import com.bancolombia.pocatv.model.AtvlfarqId;
import com.bancolombia.pocatv.repository.AtvffcasalRepository;
import com.bancolombia.pocatv.repository.AtvlfarqRepository;
import com.bancolombia.pocatv.service.AtvrcascaService;

import java.util.List;
import java.util.Optional;

@Service
public class AtvrcascaServiceImpl implements AtvrcascaService {

 @Autowired
 private AtvffcasalRepository atvffcasalRepository;
 
 @Autowired
 private AtvlfarqRepository atvlfarqRepository;
 
 @Override
 @Transactional
 public void procesarCargaInformacion() {
     // Implementación de la lógica principal del programa RPG
     List<Atvffcasal> registros = atvffcasalRepository.findAll();
     
     for (Atvffcasal registro : registros) {
         // Crear clave para buscar en atvlfarq
         String saofco = registro.getSdeofco().toString();
         String satpro = registro.getSdtpro();
         String satdoc = registro.getSdtdoc();
         String safech = registro.getSdfech().toString();
         
         // Buscar si existe el registro
         Atvlfarq atvlfarq = atvlfarqRepository.findBySaofcoAndSatproAndSatdocAndSafech(
             saofco, satpro, satdoc, safech);
             
         if (atvlfarq != null) {
             // Actualizar registro existente
             atvlfarq.setSadisp(registro.getSddisp());
             atvlfarqRepository.save(atvlfarq);
         } else {
             // Crear nuevo registro
             Atvlfarq nuevoRegistro = new Atvlfarq();
             nuevoRegistro.setSaofco(saofco);
             nuevoRegistro.setSatpro(satpro);
             nuevoRegistro.setSatdoc(satdoc);
             nuevoRegistro.setSafech(safech);
             nuevoRegistro.setSadisp(registro.getSddisp());
             nuevoRegistro.setSaofic(registro.getSdeofic());
             atvlfarqRepository.save(nuevoRegistro);
         }
     }
 }
 
 @Override
 public List<Atvlfarq> consultarTodos() {
     return atvlfarqRepository.findAll();
 }
 
 @Override
 public Atvlfarq consultarPorClave(String saofco, String satpro, String satdoc, String safech) {
     Atvlfarq resultado = atvlfarqRepository.findBySaofcoAndSatproAndSatdocAndSafech(
         saofco, satpro, satdoc, safech);
         
     if (resultado == null) {
         throw new IllegalArgumentException("No se encontró registro con los parámetros proporcionados");
     }
     
     return resultado;
 }
 
 @Override
 @Transactional
 public Atvlfarq guardar(Atvlfarq atvlfarq) {
     return atvlfarqRepository.save(atvlfarq);
 }
 
 @Override
 @Transactional
 public void eliminar(String saofco, String satpro, String satdoc, String safech) {
     AtvlfarqId id = new AtvlfarqId();
     id.setSaofco(saofco);
     id.setSatpro(satpro);
     id.setSatdoc(satdoc);
     id.setSafech(safech);
     
     if (!atvlfarqRepository.existsById(id)) {
         throw new IllegalArgumentException("No existe registro con los parámetros proporcionados");
     }
     
     atvlfarqRepository.deleteById(id);
 }
}