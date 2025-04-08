package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.model.Atvffoas;
import com.bancolombia.pocatv.service.AtvffoasService;

@RestController
@RequestMapping("/api/atvffoas")
public class AtvffoasController {

    @Autowired
    private AtvffoasService atvffoasService;

    // Listado paginado de registros
    @GetMapping
    public ResponseEntity<Page<Atvffoas>> getAll(Pageable pageable) {
        return ResponseEntity.ok(atvffoasService.findAll(pageable));
    }


    // Endpoint para obtener los registros filtrados solo por año y mes
    @GetMapping("/filtrar/{mes}/{ano}")
    public ResponseEntity<Page<Atvffoas>> getByAnoMes(
            @PathVariable("ano") Integer oaano,
            @PathVariable("mes") Integer oames,
            Pageable pageable) {
        Page<Atvffoas> result = atvffoasService.findByAnoMes(oaano, oames, pageable);
        return ResponseEntity.ok(result);
    }
    
    
    /**
     * Endpoint para buscar registros de Atvffoas filtrados por usuario, año y mes.
     * Ejemplo: GET /api/atvffoas/buscar?usuario=user02&ano=2024&mes=12&page=0&size=10
     *
     * @param usuario El identificador del usuario.
     * @param ano     El año.
     * @param mes     El mes.
     * @param page    Número de página (por defecto 0).
     * @param size    Tamaño de página (por defecto 10).
     * @return Página de registros Atvffoas.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<Atvffoas>> buscar(
            @RequestParam("usuario") String usuario,
            @RequestParam("ano") Integer ano,
            @RequestParam("mes") Integer mes,
            Pageable pageable) {
        Page<Atvffoas> resultado = atvffoasService.buscarPorUsuarioAnoMes(usuario, ano, mes, pageable);
        return ResponseEntity.ok(resultado);
    }

}