package com.springboot.app.service;

import org.springframework.http.ResponseEntity;

import com.springboot.app.model.Pago;

import java.util.Map;

public interface PagoService {
    
    ResponseEntity<Map<String, Object>> listarPagos();

    ResponseEntity<Map<String, Object>> listarPagoPorId(Long id);

    ResponseEntity<Map<String, Object>> agregarPago(Pago pago);

    ResponseEntity<Map<String, Object>> editarPago(Pago pago, Long id);

    ResponseEntity<Map<String, Object>> eliminarPago(Long id);
}