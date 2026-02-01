package com.turistik.controller;

import com.turistik.model.Hotel;
import com.turistik.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/v1/hoteles")
    public class HotelController {
        @Autowired
        private HotelService hotelService;

        @GetMapping
        public List<Hotel> getHoteles() {
            return hotelService.listarTodos();
        }

        @PostMapping
        // Requiere autenticación Admin (admin123)
        public Hotel crearHotel(@RequestBody Hotel hotel) {
            return hotelService.guardar(hotel);
        }
    }


