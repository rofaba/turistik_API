package com.turistik.controller;

import com.turistik.dto.PoiResponseDTO;
import com.turistik.model.Poi;
import com.turistik.service.PoiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar los Puntos de Interés (POIs).
 * Proporciona endpoints para listar y crear POIs.
 */

@RestController
@RequestMapping("/api/v1/pois")
public class PoiController {

    @Autowired
    private PoiService poiService;

    @GetMapping
    public List<Poi> listarPois() {
        return poiService.obtenerTodos();
    }

    // En PoiController.java

    /**
     * Busca puntos de interés filtrados por ciudad.
     * @param ciudad Nombre de la ciudad.
     * @return Lista de POIs en esa ubicación.
     */
    @GetMapping("/ciudad/{ciudad}") // Cambiamos la ruta para evitar el conflicto
    public ResponseEntity<List<Poi>> getPoisPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(poiService.listarPorCiudad(ciudad));
    }

    /**
     * Endpoint para crear un nuevo punto de interés.
     * Acceso: Público.
     * @param poi Objeto POI a crear.
     * @return ResponseEntity con el POI creado.
     */
    @PostMapping
    public Poi crearPoi(@Valid @RequestBody Poi poi) {
        return poiService.guardar(poi);
    }

    /**
     * Endpoint para obtener el detalle enriquecido de un POI por su ID.
     * Acceso: Público.
     * @param id ID del POI.
     * @return ResponseEntity con el detalle enriquecido del POI.
     */
    @GetMapping("/{id}")
    public PoiResponseDTO obtenerDetalle(@PathVariable Long id) {
        return poiService.obtenerPoiEnriquecido(id);
    }
/** Endpoint para actualizar un POI existente.
     * Acceso: Público.
     * @param id ID del POI a actualizar.
     * @param poiActualizado Objeto POI con los datos actualizados.
     * @return ResponseEntity con el POI actualizado.
     */

    @PutMapping("/{id}")
    public ResponseEntity<Poi> actualizarPoi(@PathVariable Long id, @RequestBody Poi poiActualizado) {
        Poi poi = poiService.actualizar(id, poiActualizado);
        return ResponseEntity.ok(poi);
    }

    /** Endpoint para eliminar un POI por su ID.
     * Acceso: Público.
     * @param id ID del POI a eliminar.
     * @return ResponseEntity con un mensaje de confirmación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarPoi(@PathVariable Long id) {
        poiService.eliminar(id);

        // Devolvemos un mensaje JSON confirmando la eliminación
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "POI eliminado correctamente con ID: " + id);
        return ResponseEntity.ok(respuesta);
    }
}