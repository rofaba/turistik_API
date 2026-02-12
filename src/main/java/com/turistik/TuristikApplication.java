package com.turistik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Turistik.
 * * Esta clase inicializa el contexto de Spring Boot y arranca la API REST para la
 * gestión turística de Andalucía. Incluye la configuración automática de los
 * recursos de persistencia (JPA), seguridad (Spring Security) y los servicios
 * de geolocalización geoespacial.
 * * @author TuNombre
 * @version 1.0
 */

@SpringBootApplication
public class TuristikApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuristikApplication.class, args);
    }

}
