package com.turistik.controller;

import com.turistik.model.Hotel;
import com.turistik.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Endpoint clave: Buscar hoteles en una ciudad específica
    @GetMapping("/buscar")
    public List<Hotel> getHotelesPorCiudad(@RequestParam String ciudad) {
        return hotelService.buscarPorCiudad(ciudad);
    }

    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody Hotel hotel) {
        Hotel nuevo = hotelService.guardar(hotel);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Hotel> actualizarHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        Hotel actualizado = hotelService.actualizar(id, hotel);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> eliminarHotel(@PathVariable Long id) {
        hotelService.eliminar(id);

        java.util.Map<String, String> respuesta = new java.util.HashMap<>();
        respuesta.put("mensaje", "Hotel eliminado correctamente con ID: " + id);
        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.buscarPorId(id);
        return ResponseEntity.ok(hotel);
    }
}

