package com.springboot.app.service;

import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.springboot.app.model.Habitacion;

public interface HabitacionService {
	
    ResponseEntity<Map<String, Object>> listarHabitaciones();

    ResponseEntity<Map<String, Object>> listarHabitacionPorId(Long id);

    ResponseEntity<Map<String, Object>> agregarHabitacion(Habitacion habitacion);

    ResponseEntity<Map<String, Object>> editarHabitacion(Habitacion habitacion, Long id);

    ResponseEntity<Map<String, Object>> eliminarHabitacion(Long id);

    ResponseEntity<Map<String, Object>> filtrarHabitacionesPorTipoYFechas(Long idTipoHabitacion, Date fechaIni, Date fechaFin);

    ResponseEntity<Map<String, Object>> generarReporteHabitaciones();

}
