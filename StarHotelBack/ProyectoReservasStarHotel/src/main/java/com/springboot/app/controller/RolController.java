package com.springboot.app.controller;


import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.app.model.Rol;
import com.springboot.app.service.RolService;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class RolController {

    private final RolService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarRoles();
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<Map<String, Object>> listar(@PathVariable("rolId") Long rolId) {
        return service.obtenerRolPorId(rolId);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Rol rol) {
        return service.registrarRoles(rol);
    }

    @PutMapping("/{rolId}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable("rolId") Long rolId, @RequestBody Rol rol) {
        return service.actualizarRoles(rol, rolId);
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable("rolId") Long rolId) {
        return service.eliminarRoles(rolId);
    }
}
