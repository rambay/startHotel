package com.springboot.app.controller;

import com.springboot.app.model.Pago;
import com.springboot.app.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return service.listarPagos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id) {
        return service.listarPagoPorId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody Pago pago) {
        return service.agregarPago(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editar(@RequestBody Pago pago, @PathVariable Long id) {
        return service.editarPago(pago, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarPago(id);
    }
}
