package com.turistik.service;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Hotel;
import com.turistik.model.Poi;
import com.turistik.model.Restaurant; // No olvides importar el modelo
import com.turistik.repository.HotelRepository;
import com.turistik.repository.PoiRepository;
import com.turistik.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor // Genera el constructor para los campos 'final' (Inyección limpia)
public class PoiService {

    // Al ser final y usar @RequiredArgsConstructor, no necesitas @Autowired manual
    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;

    @Value("${api.weather.key}")
    private String apiKey;

    private final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={key}&units=metric&lang=es";

    public List<Poi> obtenerTodos() {
        return poiRepository.findAll();
    }

    public Poi guardar(Poi poi) {
        if (poi.getId() != null && poiRepository.existsById(poi.getId())) {
            throw new RuntimeException("Error: El POI con ID " + poi.getId() + " ya existe.");
        }
        return poiRepository.save(poi);
    }

    public PoiResponseDTO obtenerPoiEnriquecido(Long id) {
        // 1. Busqueda en DB local
        Poi poi = poiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El punto de interés con ID " + id + " no existe."));

        // 2. Llamada a OpenWeatherMap
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(WEATHER_URL, Map.class,
                poi.getLatitud(), poi.getLongitud(), apiKey);

        // Extraer datos del clima
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        Double temp = Double.valueOf(main.get("temp").toString());
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        String descripcionClima = weatherList.get(0).get("description").toString();
        String climaPrincipal = weatherList.get(0).get("main").toString().toLowerCase();

        // 3. Búsqueda de Hoteles Y Restaurantes por la CIUDAD del POI
        // Asegúrate de que los nombres de los métodos coincidan con tus Repositories
        List<Hotel> hoteles = hotelRepository.findByCiudadIgnoreCase(poi.getCiudad());
        List<Restaurant> restaurantes = restaurantRepository.findByCityIgnoreCase(poi.getCiudad());

        // 4. Montaje del DTO
        PoiResponseDTO dto = new PoiResponseDTO();
        dto.setId(poi.getId());
        dto.setNombre(poi.getNombre());
        dto.setCategoria(poi.getCategoria());
        dto.setDescripcion(poi.getDescripcion());
        dto.setImagenUrl(poi.getImage_url());
        dto.setClimaActual(descripcionClima + ", " + temp + "°C");

        dto.setHotelesCercanos(hoteles);
        dto.setRestaurantesCercanos(restaurantes);

        // 5. Lógica de recomendación
        boolean esLluvia = climaPrincipal.contains("rain") || climaPrincipal.contains("drizzle");

        if (esLluvia) {
            dto.setRecomendacion("Está lloviendo en la zona. " +
                    (poi.getCategoria().equalsIgnoreCase("Monumento") ?
                            "Como " + poi.getNombre() + " es al aire libre, mejor lleva paraguas." :
                            "Buen momento para disfrutar de " + poi.getNombre() + " ya que es cubierto."));
        } else {
            dto.setRecomendacion("¡Día espléndido! Perfecto para visitar " + poi.getNombre());
        }

        return dto;
    }
    // Método para actualizar un POI existente
    public Poi actualizar(Long id, Poi datosNuevos) {
        // 1. Verificamos si existe antes de intentar hacer nada
        Poi poiExistente = poiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: El POI con ID " + id + " no existe."));

        // 2. Actualizamos los campos (puedes elegir cuáles permitir cambiar)
        poiExistente.setNombre(datosNuevos.getNombre());
        poiExistente.setDescripcion(datosNuevos.getDescripcion());
        poiExistente.setCategoria(datosNuevos.getCategoria());
        poiExistente.setCiudad(datosNuevos.getCiudad());
        poiExistente.setLatitud(datosNuevos.getLatitud());
        poiExistente.setLongitud(datosNuevos.getLongitud());
        poiExistente.setImage_url(datosNuevos.getImage_url());

        // 3. Guardamos los cambios
        return poiRepository.save(poiExistente);
    }

    // Método para eliminar un POI
    public void eliminar(Long id) {
        if (!poiRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El POI con ID " + id + " no existe.");
        }
        poiRepository.deleteById(id);
    }
    public List<Poi> buscarCercanos(double lat, double lng, double dist) {
        return poiRepository.buscarCercanos(lat, lng, dist);
    }
}