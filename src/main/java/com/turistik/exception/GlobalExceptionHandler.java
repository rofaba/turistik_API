package com.turistik.exception;

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
/*  Maneja las RuntimeExceptions lanzadas en la aplicación.
    Devuelve un cuerpo de respuesta con marca de tiempo,
    mensaje de error y código personalizado. */

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("codigo", "ERROR_RECURSO");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    /*  Maneja errores de validación de argumentos en las solicitudes.
        Devuelve un cuerpo de respuesta con detalles de los errores
        de validación. */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", "Datos de entrada inválidos");
        body.put("detalles", errores);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    /*  Maneja excepciones de acceso denegado.
        Devuelve un cuerpo de respuesta indicando que el usuario
        no tiene permisos para realizar la acción solicitada. */

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", "No tienes permisos para realizar esta acción.");

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}