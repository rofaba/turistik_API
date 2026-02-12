🌍 Turistik API - Gestión Turística de Andalucía
=========================================================

Turistik es una **API REST robusta** desarrollada con **Spring Boot** para la gestión y consulta de recursos turísticos en las provincias de Andalucía. Inicialmente enfocada en las ciudades de *Málaga, Granada, Sevilla y Cádiz*, permite localizar hoteles, monumentos (POIs), restaurantes y actividades mediante búsquedas geoespaciales avanzadas y filtros inteligentes.

🛠️ Arquitectura y Tecnologías
------------------------------

-   **Backend:** Java 17 con Spring Boot.

-   **Persistencia:** JPA / Hibernate con base de datos **PostgreSQL**.

-   **Contenedores:** Despliegue completo mediante **Docker y Docker Compose**.

-   **Seguridad:** Spring Security con **Autenticación Básica** para operaciones de escritura.

-   **Documentación:** Swagger UI (OpenAPI 3.0) y **Javadoc** exhaustivo en todo el código.

🚀 Instalación y Despliegue (Docker)
------------------------------------

Para garantizar que la API y la base de datos se configuren con los registros reales cargados estratégicamente, sigue estos pasos:

1.  **Clonar el repositorio:** `git clone https://github.com/rofaba/turistik_API.git`

2.  **Levantar el entorno:**  
 

    Bash

    ```
    docker-compose down -v && docker-compose up --build

    ```

    *Nota: El flag `-v` asegura que el volumen se cree limpio con toda la data actualizada.*

    

3.  **Acceder a la API:** La documentación interactiva y el testeo se realizan desde:

    📍 [http://localhost:8080/swagger-ui/index.html#/)

🔐 Seguridad y Acceso
---------------------

Se ha implementado una política de seguridad basada en el principio de menor privilegio:

-   **Lectura (Pública):** Todos los endpoints `GET` son accesibles para turistas.

-   **Escritura (Protegida):** Los métodos `POST`, `PUT` y `DELETE` requieren credenciales de administrador.

    -   **Usuario:** `admin`

    -   **Contraseña:** `admin123`

📍 Endpoints del Sistema
------------------------
### 📍 Catálogo Completo de Endpoints

| Recurso Principal | Método | Endpoint | Descripción | Acceso |
| :--- | :--- | :--- | :--- | :--- |
| **Turismo (Global)** | `GET` | `/api/v1/turismo/cerca` | **Búsqueda Geoespacial:** Hoteles, POIs, Restaurantes y Actividades en un radio. | **Público** |
| **Hoteles** | `GET` | `/hoteles` | Listado completo de hoteles (Sevilla, Málaga, Granada, Cádiz, etc.). | **Público** |
| **Hoteles** | `GET` | `/hoteles/{id}` | Obtener detalles completos de un hotel específico. | **Público** |
| **Hoteles** | `POST` | `/hoteles` | Registrar un nuevo establecimiento (Ej: Hotel ME Málaga Piqué). | 🔒 **Admin** |
| **Hoteles** | `PUT` | `/hoteles/{id}` | Actualizar precios o estrellas de un hotel. | 🔒 **Admin** |
| **Hoteles** | `DELETE` | `/hoteles/{id}` | Eliminar un registro de hotel. | 🔒 **Admin** |
| **Restaurantes** | `GET` | `/restaurantes` | Listar toda la oferta gastronómica (150 registros). | **Público** |
| **Restaurantes** | `POST` | `/restaurantes` | Añadir un nuevo restaurante al catálogo. | 🔒 **Admin** |
| **Puntos Interés** | `GET` | `/pois` | Listar monumentos, museos y parques andaluces. | **Público** |
| **Puntos Interés** | `POST` | `/pois` | Dar de alta un nuevo monumento. | 🔒 **Admin** |
| **Actividades** | `GET` | `/actividades` | Listar planes de ocio, tours y talleres. | **Público** |
| **Actividades** | `POST` | `/actividades` | Crear una nueva oferta de actividad turística. | 🔒 **Admin** |
🌟 Mejoras e Innovación (Sección 20%)
-------------------------------------

Este proyecto incluye funcionalidades avanzadas que mejoran la experienciadel usuario y la calidad de los datos, destacando:

1.  **Filtros Inteligentes de Clima:** Las actividades incluyen un campo `exterior` (booleano) que permite a la API sugerir planes basándose en el pronóstico meteorológico (Indoor/Outdoor).

2.  **Geolocalización Real:** Implementación de la fórmula de **Haversine** en consultas nativas SQL para calcular distancias reales sobre la curvatura terrestre.

3.  **Gestión de Errores Profesional:** Uso de `@ControllerAdvice` para capturar excepciones y devolver respuestas estandarizadas en JSON (401 Unauthorized, 404 Not Found, etc.).

4.  **Data Realista:** Población de base de datos con 150 registros coherentes generados mediante herramientas de IA y datos abiertos de portales oficiales.

📁 Estructura del Proyecto
--------------------------

-   `src/main/java`: Código fuente documentado con Javadoc.

-   `src/main/resources/data.sql`: Script de carga de datos (Hoteles, POIs, Restaurantes, Actividades).

-   `docker-compose.yml`: Orquestación de contenedores (App + DB).

* * * * *

**Autor:** RODRIGO FAURE

**Asignatura:** Acceso a Datos (AD)  
Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Curso:** 2024-2026