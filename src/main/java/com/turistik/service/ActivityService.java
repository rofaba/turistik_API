package com.turistik.service;

import com.turistik.model.Activity;
import com.turistik.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** * Servicio para gestionar las actividades turísticas. * Proporciona métodos para listar, buscar por ciudad, guardar, buscar cercanos y eliminar actividades. */

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public List<Activity> listarTodas() {
        return activityRepository.findAll();
    }

    public List<Activity> buscarPorCiudad(String ciudad) {
        return activityRepository.findByCiudadIgnoreCase(ciudad);
    }

    public Activity guardar(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> buscarCercanos(double lat, double lng, double dist) {
        return activityRepository.buscarCercanos(lat, lng, dist);
    }

    public void eliminar(Long id) {
        activityRepository.deleteById(id);
    }
}