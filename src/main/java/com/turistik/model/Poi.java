package com.turistik.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un Punto de Interés turístico (monumentos, museos, parques)
 * almacenados en los recursos del servidor.
 */
@Entity
@Table(name = "pois")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Poi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    private String categoria; // Ejemplo: Museos, Parques [cite: 40]

    private Double latitud;
    private Double longitud;

    private String imagenUrl;
}
