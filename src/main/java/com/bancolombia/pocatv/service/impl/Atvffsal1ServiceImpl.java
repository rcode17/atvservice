package com.bancolombia.pocatv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancolombia.pocatv.model.Atvffsal1;
import com.bancolombia.pocatv.model.Atvffsal1Id;
import com.bancolombia.pocatv.repository.Atvffsal1Repository;
import com.bancolombia.pocatv.service.Atvffsal1Service;

import java.util.List;

@Service
public class Atvffsal1ServiceImpl implements Atvffsal1Service {

    @Autowired
    private Atvffsal1Repository atvffsalRepository;

    @Override
    public List<Atvffsal1> obtenerTodos() {
        return atvffsalRepository.findAll();
    }

    @Override
    public Atvffsal1 obtenerPorId(Atvffsal1Id id) {
        return atvffsalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el registro con ID: " + id));
    }

    @Override
    public List<Atvffsal1> buscarPorProductoYDocumento(String satpro, String satdoc) {
        List<Atvffsal1> registros = atvffsalRepository.findBySatproAndSatdoc(satpro, satdoc);
        if (registros.isEmpty()) {
            throw new IllegalArgumentException("No hay registros con los parámetros proporcionados: satpro=" + satpro + ", satdoc=" + satdoc);
        }
        return registros;
    }

    @Override
    public Atvffsal1 guardar(Atvffsal1 atvffsal) {
        return atvffsalRepository.save(atvffsal);
    }

    @Override
    public void eliminarPorId(Atvffsal1Id id) {
        atvffsalRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void actualizarDocumento() {
        // Implementación de la lógica del programa RPG original
        List<Atvffsal1> registros = atvffsalRepository.findBySatproAndSatdoc("12", "001");
        
        for (Atvffsal1 registro : registros) {
            registro.setSatdoc("002");
            atvffsalRepository.save(registro);
        }
    }
}