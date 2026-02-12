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

    public Hotel actualizar(Long id, Hotel hotelActualizado) {
            // Verificamos si existe
            Hotel hotelExistente = hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se puede actualizar: El Hotel con ID " + id + " no existe."));

            // Actualizamos los campos uno a uno
            hotelExistente.setNombre(hotelActualizado.getNombre());
            hotelExistente.setCiudad(hotelActualizado.getCiudad());
            hotelExistente.setDireccion(hotelActualizado.getDireccion());
            hotelExistente.setEstrellas(hotelActualizado.getEstrellas());
            hotelExistente.setPrecioNoche(hotelActualizado.getPrecioNoche());
            hotelExistente.setTienePiscina(hotelActualizado.isTienePiscina());

            return hotelRepository.save(hotelExistente);
        }

        public void eliminar(Long id) {
            if (!hotelRepository.existsById(id)) {
                throw new RuntimeException("No se puede eliminar: El Hotel con ID " + id + " no existe.");
            }
            hotelRepository.deleteById(id);
        }

    public Hotel buscarPorId(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado con ID: " + id));
    }

    public List<Hotel> buscarCercanos(double lat, double lng, double dist) {
        return hotelRepository.buscarCercanos(lat, lng, dist);
    }
    }
