package com.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.app.model.Habitacion;
import com.springboot.app.service.HabitacionService;

import java.util.Date;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarHabitaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id) {
        return service.listarHabitacionPorId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody Habitacion habitacion) {
        return service.agregarHabitacion(habitacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editar(@RequestBody Habitacion habitacion, @PathVariable Long id) {
        return service.editarHabitacion(habitacion, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarHabitacion(id);
    }
    
    @GetMapping("/filtrar")
    public ResponseEntity<Map<String, Object>> filtrarHabitaciones(
        @RequestParam Long idTipoHabitacion,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        
        return service.filtrarHabitacionesPorTipoYFechas(idTipoHabitacion, fechaIni, fechaFin);
    }
    
    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> generarReporte() {
        return service.generarReporteHabitaciones();
    }
    
}
