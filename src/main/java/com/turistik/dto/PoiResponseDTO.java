package com.turistik.dto;

import com.turistik.model.Hotel;
import com.turistik.model.Restaurant;
import lombok.Data;

import java.util.List;

/**
 * DTO para enviar una respuesta enriquecida al usuario.
 * Combina datos del servidor con información en tiempo real.
 */


@Data
public class PoiResponseDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String imagenUrl;
    private String climaActual;
    private String recomendacion;

    private List<Hotel> hotelesCercanos;
    private List<Restaurant> restaurantesCercanos;
}