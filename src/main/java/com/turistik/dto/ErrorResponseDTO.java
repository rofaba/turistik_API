package com.turistik.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

/** DTO para representar la estructura de una respuesta de error en la API.
 * Incluye un timestamp, mensaje de error, código y detalles adicionales (como errores de validación). */

@Data
@Builder
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private String mensaje;
    private String codigo;
    private Map<String, String> detalles; // Para los errores de validación de campos
}