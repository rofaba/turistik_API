package com.turistik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Representa un Punto de Interés turístico (monumentos, museos, parques)
 * almacenados en los recursos del servidor.
 */
@Entity
@Table(name = "pois")
@Getter @Setter
public class Poi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Size(max = 1000, message = "La descripción es demasiado larga")
    private String descripcion;

    @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
    private Double latitud;

    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    private Double longitud;

    private String categoria;
    private String image_url;
}
