package com.turistik.controller;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Poi;
import com.turistik.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public Poi crearPoi(@RequestBody Poi poi) {
        return poiService.guardar(poi);
    }

    @GetMapping("/{id}")
    public PoiResponseDTO obtenerDetalle(@PathVariable Long id) {
        return poiService.obtenerPoiEnriquecido(id);
    }
}