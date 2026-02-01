package com.turistik.service;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Hotel;
import com.turistik.model.Poi;
import com.turistik.repository.HotelRepository;
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
    @Autowired
    private HotelRepository hotelRepository;

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
        // Busqueda en DB local de Docker
        Poi poi = poiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El punto de interés con ID " + id + " no existe."));

        // Llamada a OpenWeatherMap
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(WEATHER_URL, Map.class,
                poi.getLatitud(), poi.getLongitud(), apiKey);

        // Conseguir data del clima desde la respuesta API externa
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        Double temp = Double.valueOf(main.get("temp").toString());
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        String descripcionClima = weatherList.get(0).get("description").toString();
        String climaPrincipal = weatherList.get(0).get("main").toString().toLowerCase();


        // Búsqueda de hoteles en la ciduad del POI
        List<Hotel> hoteles = hotelRepository.findByCiudadIgnoreCase(poi.getCiudad());

        // Montaje del DTO con TODO lo que tenemos
        PoiResponseDTO dto = new PoiResponseDTO();
        dto.setId(poi.getId());
        dto.setNombre(poi.getNombre());
        dto.setCategoria(poi.getCategoria());
        dto.setDescripcion(poi.getDescripcion());
        dto.setImagenUrl(poi.getImage_url());
        dto.setClimaActual(descripcionClima + ", " + temp + "°C");
        dto.setHotelesCercanos(hoteles);

        // Recomendación personalizada con el nombre del sitio
        boolean esLluvia = climaPrincipal.contains("rain") || climaPrincipal.contains("drizzle");

        if (esLluvia) {
            dto.setRecomendacion("Está lloviendo en la zona. " +
                    (poi.getCategoria().equalsIgnoreCase("Monumento") ?
                            "Como " + poi.getNombre() + " es al aire libre, mejor lleva paraguas o visita un museo cercano." :
                            "Buen momento para disfrutar de " + poi.getNombre() + " ya que es un lugar cubierto."));
        } else {
            dto.setRecomendacion("¡Día espléndido! Es el momento perfecto para recorrer " + poi.getNombre() + " y hacer fotos increíbles.");
        }

        return dto;
    }
}