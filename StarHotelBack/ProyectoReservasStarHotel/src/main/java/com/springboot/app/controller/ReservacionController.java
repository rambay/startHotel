package com.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.app.model.Reservacion;
import com.springboot.app.service.ReservacionService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reservaciones")
public class ReservacionController {

    @Autowired
    private ReservacionService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarReservaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id) {
        return service.listarReservacionPorId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody Reservacion reservacion) {
        return service.agregarReservacion(reservacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editar(@RequestBody Reservacion reservacion, @PathVariable Long id) {
        return service.editarReservacion(reservacion, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarReservacion(id);
    }
    
    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> generarReporte() {
        return service.generarReporteReservaciones();
    }
}