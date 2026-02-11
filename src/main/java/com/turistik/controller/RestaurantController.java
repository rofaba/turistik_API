package com.turistik.controller;

import com.turistik.model.Restaurant;

import com.turistik.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
