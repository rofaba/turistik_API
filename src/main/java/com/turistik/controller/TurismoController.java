package com.turistik.controller;

import com.turistik.service.TurismoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** Controlador REST para gestionar las funcionalidades relacionadas con el turismo.
 * * Proporciona endpoints para obtener información turística cercana a una ubicación específica. */

@RestController
@RequestMapping("/api/v1/turismo")
public class TurismoController {

    @Autowired
    private TurismoService turismoService;

    @GetMapping("/cerca")
    public ResponseEntity<Map<String, Object>> getCercanos(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "2.0") double distancia) { //2km por defecto

        return ResponseEntity.ok(turismoService.obtenerTodoCerca(lat, lng, distancia));
    }
}