package com.turistik.repository;

import com.turistik.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para el acceso a datos de los Puntos de Interés.
 */
@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {
}