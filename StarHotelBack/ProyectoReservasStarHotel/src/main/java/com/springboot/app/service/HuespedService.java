package com.springboot.app.service;

import org.springframework.http.ResponseEntity;

import com.springboot.app.model.Huesped;

import java.util.Map;

public interface HuespedService {
    
    ResponseEntity<Map<String, Object>> listarHuespedes();

    ResponseEntity<Map<String, Object>> listarHuespedPorId(Long id);

    ResponseEntity<Map<String, Object>> agregarHuesped(Huesped huesped);

    ResponseEntity<Map<String, Object>> editarHuesped(Huesped huesped, Long id);

    ResponseEntity<Map<String, Object>> eliminarHuesped(Long id);
    
    ResponseEntity<Map<String, Object>> generarReporteHuespedes();
}