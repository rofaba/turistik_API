package com.turistik.repository;

import com.turistik.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCityIgnoreCase(String city);

    List<Restaurant> findByCityIgnoreCaseAndCuisineTypeIgnoreCase(String city, String cuisine);
    List<Restaurant> findByCityIgnoreCaseAndRatingGreaterThanEqual(String city, Double rating);

    @Query(value = "SELECT id, name, city, address, rating, " +
            "cuisine_type AS cuisineType, average_price AS averagePrice " +
            "FROM restaurant r WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(r.latitud)) * " +
            "cos(radians(r.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(r.latitud)))) <= :distancia",
            nativeQuery = true)
    List<Restaurant> buscarCercanos(@Param("lat") double lat, @Param("lng") double lng, @Param("distancia") double distancia);}