package com.turistik.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String duracion;
    private String ciudad;
    private Double latitud;
    private Double longitud;
    private boolean exterior; // Para validar con el clima
}