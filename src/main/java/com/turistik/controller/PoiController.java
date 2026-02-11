package com.turistik.controller;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Poi;
import com.turistik.service.PoiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar los Puntos de Interés (POIs).
 * Proporciona endpoints para listar y crear POIs.
 */

@RestController
@RequestMapping("/api/v1/pois")
public class PoiController {

    @Autowired
    private PoiService poiService;

    @GetMapping
    public List<Poi> listarPois() {
        return poiService.obtenerTodos();
    }
    @PostMapping
    public Poi crearPoi(@Valid @RequestBody Poi poi) {
        return poiService.guardar(poi);
    }

    @GetMapping("/{id}")
    public PoiResponseDTO obtenerDetalle(@PathVariable Long id) {
        return poiService.obtenerPoiEnriquecido(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poi> actualizarPoi(@PathVariable Long id, @RequestBody Poi poiActualizado) {
        Poi poi = poiService.actualizar(id, poiActualizado);
        return ResponseEntity.ok(poi);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarPoi(@PathVariable Long id) {
        poiService.eliminar(id);

        // Devolvemos un mensaje JSON limpio confirmando la eliminación
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "POI eliminado correctamente con ID: " + id);
        return ResponseEntity.ok(respuesta);
    }
}