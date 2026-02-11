package com.turistik.service;

import com.turistik.model.Hotel;
import com.turistik.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    public List<Hotel> buscarPorCiudad(String ciudad) {
        return hotelRepository.findByCiudadIgnoreCase(ciudad);
    }

    public Hotel guardar(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}