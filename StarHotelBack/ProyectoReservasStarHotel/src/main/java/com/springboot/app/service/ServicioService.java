package com.springboot.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.springboot.app.model.Servicio;

public interface ServicioService {
	
	public ResponseEntity<Map<String, Object>> listarServicios();

	public ResponseEntity<Map<String, Object>> listarServiciosPorId(Long id);

	public ResponseEntity<Map<String, Object>> agregarServicios(Servicio servicio);

	public ResponseEntity<Map<String, Object>> editarServicios(Servicio servicio, Long id);

	public ResponseEntity<Map<String, Object>> eliminarServicios(Long id);

    ResponseEntity<Map<String, Object>> generarReporteServicios();

}
