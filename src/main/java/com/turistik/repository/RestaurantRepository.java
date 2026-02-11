package com.turistik.repository;

import com.turistik.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCityIgnoreCase(String city);

    List<Restaurant> findByCityIgnoreCaseAndCuisineTypeIgnoreCase(String city, String cuisine);
    List<Restaurant> findByCityIgnoreCaseAndRatingGreaterThanEqual(String city, Double rating);
}