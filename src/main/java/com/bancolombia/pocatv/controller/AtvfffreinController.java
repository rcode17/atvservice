package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.model.Atvfffrein;
import com.bancolombia.pocatv.service.AtvffapaService;
import com.bancolombia.pocatv.service.AtvfffreinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atvfffrein")
public class AtvfffreinController {
    @Autowired
    private AtvfffreinService funcionarioService;

    // Obtener todos los funcionarios
    @GetMapping
    public List<Atvfffrein> getAllFuncionarios() {
        return funcionarioService.getAllFuncionarios();
    }

    // Obtener funcionarios paginados
    @GetMapping("/page")
    public Page<Atvfffrein> getFuncionariosByPage(
            Pageable pageable) {
        return funcionarioService.getFuncionariosByPage(pageable);
    }

    // Obtener funcionario por ID
    @GetMapping("/{resp}")
    public ResponseEntity<Atvfffrein> getFuncionarioById(@PathVariable String resp) {
        Atvfffrein funcionario = funcionarioService.getFuncionarioById(resp);
        return ResponseEntity.ok(funcionario);
    }

    // Crear nuevo funcionario
    @PostMapping
    public ResponseEntity<Atvfffrein> createFuncionario(@RequestBody Atvfffrein funcionario) {
        Atvfffrein savedFuncionario = funcionarioService.saveFuncionario(funcionario);
        return new ResponseEntity<>(savedFuncionario, HttpStatus.CREATED);
    }

    // Actualizar funcionario
    @PutMapping("/{resp}")
    public ResponseEntity<Atvfffrein> updateFuncionario(
            @PathVariable String resp,
            @RequestBody Atvfffrein funcionarioDetails) {
        Atvfffrein updatedFuncionario = funcionarioService.updateFuncionario(resp, funcionarioDetails);
        return ResponseEntity.ok(updatedFuncionario);
    }

    // Eliminar funcionario
    @DeleteMapping("/{resp}")
    public ResponseEntity<Map<String, Boolean>> deleteFuncionario(@PathVariable String resp) {
        funcionarioService.deleteFuncionario(resp);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // Buscar por área
    @GetMapping("/area/{area}")
    public List<Atvfffrein> getFuncionariosByArea(@PathVariable String area) {
        return funcionarioService.findByAreaContaining(area);
    }

    // Buscar por mes y año
    @GetMapping("/fecha")
    public List<Atvfffrein> getFuncionariosByMesAno(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        return funcionarioService.findByMesAno(mes, ano);
    }
}
