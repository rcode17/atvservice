package com.bancolombia.pocatv.controller;

import com.bancolombia.pocatv.dto.ArqueoAnormalDTO;
import com.bancolombia.pocatv.service.AtvffapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atvffapa")
public class AtvffapaController {
	@Autowired
    private AtvffapaService atvffapaService;

    /**
     * Procesa los arqueos con periodicidad anormal para un año específico
     * @param ano Año a procesar
     * @return Respuesta con el número de registros procesados
     */
    @PostMapping("/procesar/{ano}")
    public ResponseEntity<String> procesarArqueosAnormales(@PathVariable Integer ano) {
        try {
            int registrosProcesados = atvffapaService.procesarArqueosAnormales(ano);
            return ResponseEntity.ok("Proceso completado. Se procesaron " + registrosProcesados + " registros de arqueos anormales.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en los parámetros: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar los arqueos anormales: " + e.getMessage());
        }
    }

    /**
     * Consulta los arqueos con periodicidad anormal para un año y mes específicos
     * @param ano Año a consultar
     * @param mes Mes a consultar (opcional)
     * @return Lista de arqueos anormales
     */
    @GetMapping("/anormales/{ano}")
    public ResponseEntity<?> consultarArqueosAnormales(
            @PathVariable Integer ano,
            @RequestParam(required = false) Integer mes) {
        try {
            List<ArqueoAnormalDTO> arqueos = atvffapaService.consultarArqueosAnormales(ano, mes);
            return ResponseEntity.ok(arqueos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en los parámetros: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar los arqueos anormales: " + e.getMessage());
        }
    }

    /**
     * Endpoint para verificar el estado del servicio
     * @return Mensaje indicando que el servicio está activo
     */
    @GetMapping("/estado")
    public ResponseEntity<String> verificarEstado() {
        return ResponseEntity.ok("Servicio de arqueos activo");
    }
}
