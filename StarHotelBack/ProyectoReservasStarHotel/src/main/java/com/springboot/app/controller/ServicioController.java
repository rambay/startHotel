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

import com.springboot.app.model.Servicio;
import com.springboot.app.service.ServicioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/servicios")
public class ServicioController {


	@Autowired
    private ServicioService service;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarServicios();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id) {
        return service.listarServiciosPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody Servicio servicio) {
        return service.agregarServicios(servicio);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editar(@RequestBody Servicio servicio, @PathVariable Long id) {
        return service.editarServicios(servicio, id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarServicios(id);
    }
    
    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> generarReporte() {
        return service.generarReporteServicios();
    }
    
}
