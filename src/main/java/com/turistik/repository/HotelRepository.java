package com.turistik.repository;

import com.turistik.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByEstrellasGreaterThanEqual(int estrellas);
    List<Hotel> findByCiudad(String ciudad);




}
