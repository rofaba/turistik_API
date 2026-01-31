package com.turistik.service;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Poi;
import com.turistik.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los Puntos de Interés.
 * Proporciona una capa intermedia entre el controlador y el repositorio[cite: 46].
 */
@Service
public class PoiService {

    @Autowired
    private PoiRepository poiRepository;

    public List<Poi> obtenerTodos() {
        return poiRepository.findAll();
    }

    public Poi guardar(Poi poi) {
        // Aquí podríamos añadir lógica, como validar coordenadas
        return poiRepository.save(poi);
    }

    public PoiResponseDTO obtenerPoiEnriquecido(Long id) {
        Poi poi = poiRepository.findById(id).orElseThrow();

        PoiResponseDTO dto = new PoiResponseDTO();
        dto.setId(poi.getId());
        dto.setNombre(poi.getNombre());
        dto.setCategoria(poi.getCategoria());

        // Lógica de recomendación basada en el clima (hoy sol)

        dto.setClimaActual("Soleado, 25°C");

        if (poi.getCategoria().equalsIgnoreCase("Cultura")) {
            dto.setRecomendacion("Hoy hace calor, el aire acondicionado del museo te vendrá genial.");
        } else {
            dto.setRecomendacion("Día perfecto para disfrutar del exterior.");
        }

        return dto;
    }
}