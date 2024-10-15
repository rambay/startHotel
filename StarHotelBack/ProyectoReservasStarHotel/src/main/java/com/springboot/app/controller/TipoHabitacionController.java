package com.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.model.TipoHabitacion;
import com.springboot.app.service.TipoHabitacionService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/tipoHabitaciones")
public class TipoHabitacionController {

    @Autowired
    private TipoHabitacionService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarTipoHabitaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id) {
        return service.listarTipoHabitacionPorId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody TipoHabitacion tipoHabitacion) {
        return service.agregarTipoHabitacion(tipoHabitacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editar(@RequestBody TipoHabitacion tipoHabitacion, @PathVariable Long id) {
        return service.editarTipoHabitacion(tipoHabitacion, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarTipoHabitacion(id);
    }
}
