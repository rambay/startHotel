package com.springboot.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.springboot.app.model.TipoHabitacion;

public interface TipoHabitacionService {
	
	public ResponseEntity<Map<String, Object>> listarTipoHabitaciones();

    public ResponseEntity<Map<String, Object>> listarTipoHabitacionPorId(Long id);

    public ResponseEntity<Map<String, Object>> agregarTipoHabitacion(TipoHabitacion tipoHabitacion);

    public ResponseEntity<Map<String, Object>> editarTipoHabitacion(TipoHabitacion tipoHabitacion, Long id);

    public ResponseEntity<Map<String, Object>> eliminarTipoHabitacion(Long id);

}
