package com.turistik.repository;

import com.turistik.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz para el acceso a datos de los Puntos de Interés.
 */
@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {

    @Query(value = "SELECT id, nombre, descripcion, categoria, ciudad, latitud, longitud, " +
            "image_url AS imageUrl " +
            "FROM pois p WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(p.latitud)) * " +
            "cos(radians(p.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(p.latitud)))) <= :distancia",
            nativeQuery = true)
    List<Poi> buscarCercanos(@Param("lat") double lat, @Param("lng") double lng, @Param("distancia") double distancia);}