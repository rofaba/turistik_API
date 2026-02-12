package com.turistik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/** Servicio centralizado para obtener información turística. Este servicio se encarga de coordinar las consultas
 *  a los diferentes servicios (POI, hoteles, restaurantes, actividades) para proporcionar una respuesta unificada al cliente. */

@Service
@RequiredArgsConstructor
public class TurismoService {

    // Inyectamos los servicios, NO los repositorios
    private final PoiService poiService;
    private final HotelService hotelService;
    private final RestaurantService restaurantService;
    private final ActivityService activityService;

    public Map<String, Object> obtenerTodoCerca(double lat, double lng, double dist) {
        Map<String, Object> respuesta = new HashMap<>();

        // Cada servicio se encarga de su propia entidad
        respuesta.put("pois", poiService.buscarCercanos(lat, lng, dist));
        respuesta.put("hoteles", hotelService.buscarCercanos(lat, lng, dist));
        respuesta.put("restaurantes", restaurantService.buscarCercanos(lat, lng, dist));
        respuesta.put("actividades", activityService.buscarCercanos(lat, lng, dist));

        return respuesta;
    }

}