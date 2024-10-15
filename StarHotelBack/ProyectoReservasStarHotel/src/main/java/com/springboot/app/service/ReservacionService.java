package com.springboot.app.service;

import org.springframework.http.ResponseEntity;

import com.springboot.app.model.Reservacion;

import java.util.Map;

public interface ReservacionService {
    
    ResponseEntity<Map<String, Object>> listarReservaciones();

    ResponseEntity<Map<String, Object>> listarReservacionPorId(Long id);

    ResponseEntity<Map<String, Object>> agregarReservacion(Reservacion reservacion);

    ResponseEntity<Map<String, Object>> editarReservacion(Reservacion reservacion, Long id);

    ResponseEntity<Map<String, Object>> eliminarReservacion(Long id);

    ResponseEntity<Map<String, Object>> generarReporteReservaciones();

}