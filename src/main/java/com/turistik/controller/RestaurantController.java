package com.turistik.controller;

import com.turistik.model.Restaurant;

import com.turistik.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controlador REST para gestionar los restaurantes.
 * * Proporciona endpoints para listar, buscar por ciudad, buscar por tipo de cocina,
 * obtener los mejores restaurantes, crear, actualizar y eliminar restaurantes. */

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> obtenerTodos() {
        return restaurantService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<Restaurant> guardar(@RequestBody Restaurant restaurant) {
        return new ResponseEntity<>(restaurantService.guardar(restaurant), HttpStatus.CREATED);
    }
    // Actualiza tu RestaurantController.java con estos métodos adicionales

    @GetMapping("/buscar")
    public List<Restaurant> buscarPorCiudad(@RequestParam String city) {
        return restaurantService.buscarPorCiudad(city);
    }

    @GetMapping("/cocina")
    public List<Restaurant> buscarPorCocina(@RequestParam String city, @RequestParam String cuisine) {
        return restaurantService.buscarPorCocina(city, cuisine);
    }

    @GetMapping("/top")
    public List<Restaurant> obtenerMejores(@RequestParam String city) {
        return restaurantService.obtenerMejores(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> actualizar(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.actualizar(id, restaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> eliminar(@PathVariable Long id) {
        restaurantService.eliminar(id);
        java.util.Map<String, String> response = new java.util.HashMap<>();
        response.put("message", "Restaurante eliminado con éxito");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> obtenerPorId(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.buscarPorId(id);
        return ResponseEntity.ok(restaurant);

    }
}
