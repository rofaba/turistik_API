package com.turistik.service;

import com.turistik.model.Hotel;
import com.turistik.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    public Hotel guardar(Hotel hotel) {
        // Aquí podrías añadir validaciones (ej: que el precio no sea negativo)
        return hotelRepository.save(hotel);
    }

    public List<Hotel> buscarPorCalidad(int minEstrellas) {
        return hotelRepository.findByEstrellasGreaterThanEqual(minEstrellas);
    }
}
