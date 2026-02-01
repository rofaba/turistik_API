package com.turistik.exception;

import com.turistik.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestor global de excepciones para la API Turistik.
 * Asegura que el usuario reciba respuestas claras en lugar de errores de servidor genéricos.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /*  Maneja excepciones de tipo RuntimeException.
    Devuelve un DTO con detalles del error y
    un código HTTP 404 (Not Found). */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .mensaje(ex.getMessage())
                .codigo("ERROR_RECURSO")
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

/*  Maneja errores de validación de argumentos.
    Devuelve un DTO con detalles de los errores de validación
    y un código HTTP 400 (Bad Request). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .mensaje("Datos de entrada inválidos")
                .detalles(errores)
                .codigo("ERROR_VALIDACION")
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

/*  Maneja excepciones de acceso denegado.
    Devuelve un DTO con un mensaje de error
    y un código HTTP 403 (Forbidden). */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .mensaje("No tienes permisos para realizar esta acción.")
                .codigo("ERROR_SEGURIDAD")
                .build();
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}