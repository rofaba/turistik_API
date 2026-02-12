## 🌍 Turistik API
**Gestión Turística de Varguardia de Andalucía**

Turistik es una API REST de alto rendimiento diseñada para la digitalización del sector turístico andaluz.
La plataforma centraliza la oferta turística de manera inicial en las ciudades de Málaga, Granada, Sevilla y Cádiz, permitiendo a los usuarios descubrir cada ciudad mediante una arquitectura robusta, segura y geolocalizada.

## 🛠️ Stack Tecnológico

**Core**

- Java 17

- Spring Boot 4.0.2

**Data Layer**

- PostgreSQL

- JPA / Hibernate

**Geolocalización**

- Implementación nativa de la Fórmula de Haversine para cálculo de distancias geográficas.

**Seguridad**

- Spring Security
- Basic Authentication
- CSRF deshabilitado para entorno API

**Infraestructura**

- Docker

- Docker Compose

**Documentación**

- Swagger UI (OpenAPI 3.1)

- Javadoc

## 🚀 Despliegue

La API incluye un script data.sql con más de 150 registros turísticos precargados.

### Clonar repositorio
git clone https://github.com/rofaba/turistik_API.git

### Levantar infraestructura
    Bash  

    docker-compose down -v  

    docker-compose up --build


## 📍 Documentación con Swagger UI
### http://localhost:8080/swagger-ui/index.html

## 🔐 Modelo de Seguridad
    Nivel	        Acceso      Endpoints  

    Público 🔓	Libre	    Consultas, listados y detalles (GET)  

    Privado 🔒	Admin	    Creación, modificación y borrado (POST, PUT, DELETE)

### **Credenciales Admin** : admin / admin123  


## 📍 Catálogo Maestro de Endpoints

| Recurso | Método | Endpoint | Descripción | Acceso |
| :--- | :---: | :--- | :--- | :---: |
| **Turismo** | `GET` | `/api/v1/turismo/cerca` | **Haversine Engine:** Búsqueda combinada por radio. | **Público** |
| **Hoteles** | `GET` | `/api/v1/hoteles` | Listado global de todos los hoteles. | **Público** |
| **Hoteles** | `GET` | `/api/v1/hoteles/{id}` | Ficha técnica detallada de un hotel por ID. | **Público** |
| **Hoteles** | `GET` | `/api/v1/hoteles/buscar` | Filtrado dinámico por ciudad (`?ciudad=...`). | **Público** |
| **Hoteles** | `POST` | `/api/v1/hoteles` | Registro de nuevos hoteles. | 🔒 **Admin** |
| **Hoteles** | `PUT` | `/api/v1/hoteles/{id}` | Actualización completa de datos de un hotel. | 🔒 **Admin** |
| **Hoteles** | `DELETE` | `/api/v1/hoteles/{id}` | Eliminación física del registro de hotel. | 🔒 **Admin** |
| **POIs** | `GET` | `/api/v1/pois` | Listado de monumentos, museos y parques. | **Público** |
| **POIs** | `GET` | `/api/v1/pois/{id}` | **Smart POI:** Detalle con clima y recomendaciones. | **Público** |
| **POIs** | `GET` | `/api/v1/pois/{ciudad}` | Filtro administrativo por nombre de ciudad. | **Público** |
| **POIs** | `POST` | `/api/v1/pois` | Crear un nuevo punto de interés. | 🔒 **Admin** |
| **POIs** | `PUT` | `/api/v1/pois/{id}` | Modificar información de un monumento. | 🔒 **Admin** |
| **POIs** | `DELETE` | `/api/v1/pois/{id}` | Borrar un monumento del sistema. | 🔒 **Admin** |
| **Restaurantes** | `GET` | `/api/restaurants` | Guía gastronómica completa de Andalucía. | **Público** |
| **Restaurantes** | `GET` | `/api/restaurants/{id}` | Detalle de restaurante específico por ID. | **Público** |
| **Restaurantes** | `GET` | `/api/restaurants/buscar` | Búsqueda rápida por ciudad (`?city=...`). | **Público** |
| **Restaurantes** | `GET` | `/api/restaurants/top` | **Ranking:** Los mejores valorados por ciudad. | **Público** |
| **Restaurantes** | `GET` | `/api/restaurants/cocina` | **Filtro Gourmet:** Por ciudad y tipo de cocina. | **Público** |
| **Restaurantes** | `POST` | `/api/restaurants` | Añadir nuevo restaurante al catálogo. | 🔒 **Admin** |
| **Restaurantes** | `PUT` | `/api/restaurants/{id}` | Actualizar datos de un restaurante existente. | 🔒 **Admin** |
| **Restaurantes** | `DELETE` | `/api/restaurants/{id}` | Eliminar restaurante del sistema. | 🔒 **Admin** |
| **Actividades** | `GET` | `/api/v1/actividades` | Catálogo completo de tours y experiencias. | **Público** |
| **Actividades** | `GET` | `/api/v1/actividades/buscar` | Búsqueda de planes de ocio por ciudad. | **Público** |
| **Actividades** | `POST` | `/api/v1/actividades` | Crear una nueva oferta de actividad turística. | 🔒 **Admin** |

## 🌟 **Innovaciones Destacadas**

### **Smart Recommendations**  

    El endpoint /pois/{id} devuelve hoteles y restaurantes cercanos mediante DTO enriquecido.

### **Arquitectura de Datos Realista**  

    Más de 150 registros turísticos coherentes, optimizados para pruebas de geolocalización.

### **Resiliencia**  

    Manejo centralizado de errores con @ControllerAdvice garantizando respuestas consistentes.

## 📁 Estructura del Proyecto  

    - src/main/java        → Lógica de negocio (Layered Architecture)  

    - src/main/resources   → Configuración y carga SQL  

    - docker-compose.yml   → Infraestructura  


### 👨‍💻 Autor

- **Rodrigo Faure Bascur**  

- **Ciclo:** Desarrollo de Aplicaciones Multiplataforma (DAM) 24-26 

- **Asignatura:** Acceso a Datos (AD)  

- **Docente** : Francisco Romero Guillén