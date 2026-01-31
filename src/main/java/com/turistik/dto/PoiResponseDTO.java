package com.turistik.dto;

import lombok.Data;

/**
 * DTO para enviar una respuesta enriquecida al usuario.
 * Combina datos del servidor con información en tiempo real.
 */
@Data
public class PoiResponseDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private String climaActual; // Ej: "Soleado, 22°C"
    private String recomendacion; // Ej: "Es un gran día para ir a este museo"
}