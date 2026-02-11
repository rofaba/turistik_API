package com.turistik.service;

import com.turistik.model.Restaurant;
import com.turistik.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> obtenerTodos() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> buscarPorCiudad(String ciudad) {
        return restaurantRepository.findByCityIgnoreCase(ciudad);
    }

    public Restaurant guardar(Restaurant restaurant) {
        // Podrías añadir validaciones aquí, como verificar si ya existe
        return restaurantRepository.save(restaurant);
    }
    // Añade esto a tu RestaurantService
    public List<Restaurant> buscarPorCocina(String city, String cuisine) {
        return restaurantRepository.findByCityIgnoreCaseAndCuisineTypeIgnoreCase(city, cuisine);
    }

    public List<Restaurant> obtenerMejores(String city) {
        return restaurantRepository.findByCityIgnoreCaseAndRatingGreaterThanEqual(city, 4.5);
    }
}