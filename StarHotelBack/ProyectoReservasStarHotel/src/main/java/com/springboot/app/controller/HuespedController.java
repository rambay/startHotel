package com.springboot.app.controller;

import com.springboot.app.model.Huesped;
import com.springboot.app.service.HuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/huespedes")
public class HuespedController {

    @Autowired
    private HuespedService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarHuespedes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id) {
        return service.listarHuespedPorId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody Huesped huesped) {
        return service.agregarHuesped(huesped);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editar(@RequestBody Huesped huesped, @PathVariable Long id) {
        return service.editarHuesped(huesped, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarHuesped(id);
    }
    
    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> generarReporte() {
        return service.generarReporteHuespedes();
    }
}