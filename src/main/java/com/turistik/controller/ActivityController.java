package com.turistik.controller;

import com.turistik.model.Activity;
import com.turistik.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** * Controlador REST para gestionar las actividades turísticas.
 * * Proporciona endpoints para listar, buscar por ciudad y crear nuevas actividades. */

@RestController
@RequestMapping("/api/v1/actividades")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<Activity> getTodas() {
        return activityService.listarTodas();
    }

    @GetMapping("/buscar")
    public List<Activity> getPorCiudad(@RequestParam String ciudad) {
        return activityService.buscarPorCiudad(ciudad);
    }

    @PostMapping
    public Activity crear(@RequestBody Activity activity) {
        return activityService.guardar(activity);
    }
}