package com.turistik.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private String mensaje;
    private String codigo;
    private Map<String, String> detalles; // Para los errores de validación de campos
}