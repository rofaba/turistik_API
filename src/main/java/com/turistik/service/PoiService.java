package com.turistik.service;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Poi;
import com.turistik.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio que gestiona la lógica de negocio de los Puntos de Interés.
 * Proporciona una capa intermedia entre el controlador y el repositorio[cite: 46].
 */
@Service
public class PoiService {

    @Autowired
    private PoiRepository poiRepository;

    @Value("${api.weather.key}")
    private String apiKey;

    private final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={key}&units=metric&lang=es";

    /**
     * Obtiene todos los Puntos de Interés almacenados en la base de datos.
     * @return Lista de POIs.
     */

    public List<Poi> obtenerTodos() {
        return poiRepository.findAll();
    }

    /**
     * Guarda un nuevo Punto de Interés en la base de datos.
     * @param poi El POI a guardar.
     * @return El POI guardado.
     * @throws RuntimeException si el POI ya existe.
     */

    public Poi guardar(Poi poi) {
        // Si el usuario envía un ID en un POST comprobamos si ya existe
        if (poi.getId() != null && poiRepository.existsById(poi.getId())) {
            throw new RuntimeException("Error: El POI con ID " + poi.getId() + " ya existe. Usa PUT para actualizar.");
        }
        return poiRepository.save(poi);
    }

    /** Implementación del método para obtener POI enriquecido
     * Obtiene un POI definido con y según los datos de la API en tiempo real.
     * @param id ID del POI.
     * @return DTO con información del POI y datos adicionales.
     * @throws RuntimeException si el POI no existe.
     */

    public PoiResponseDTO obtenerPoiEnriquecido(Long id) {

        // Busca el POI en la DB (Docker)
        Poi poi = poiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El punto de interes con ID " + id + " no existe."));

        // LLama a la API del tiempo
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(WEATHER_URL, Map.class,
                poi.getLatitud(), poi.getLongitud(), apiKey);

        // Extrae los datos del JSON de OpenWeatherMap
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        Double temp = Double.valueOf(main.get("temp").toString());

        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        String descripcion = weatherList.get(0).get("description").toString();
        String climaPrincipal = weatherList.get(0).get("main").toString().toLowerCase();

        // Logica de recomendación
        PoiResponseDTO dto = new PoiResponseDTO();
        dto.setId(poi.getId());
        dto.setNombre(poi.getNombre());
        dto.setClimaActual(descripcion.substring(0, 1).toUpperCase() + descripcion.substring(1) + ", " + temp + "°C");

        // Ahora la pregunta: plan interior o exterior
        boolean esLluvia = climaPrincipal.contains("rain") || climaPrincipal.contains("drizzle") || climaPrincipal.contains("thunderstorm");

        if (esLluvia) {
            dto.setRecomendacion("Esta lloviendo en la zona. " +
                    (poi.getCategoria().equalsIgnoreCase("Museo") ? "Es el momento perfecto para visitar este museo y no mojarse." : "Mejor busca un plan de interior."));
        } else {
            dto.setRecomendacion("El tiempo es perfecto para disfrutar de " + poi.getNombre() + " al aire libre.");
        }

        return dto;
    }
}