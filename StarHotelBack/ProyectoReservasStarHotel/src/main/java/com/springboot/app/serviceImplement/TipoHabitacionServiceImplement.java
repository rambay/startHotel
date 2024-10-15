package com.springboot.app.serviceImplement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.app.model.TipoHabitacion;
import com.springboot.app.repository.TipoHabitacionRepository;
import com.springboot.app.service.TipoHabitacionService;

@Service
public class TipoHabitacionServiceImplement implements TipoHabitacionService {

    @Autowired
    private TipoHabitacionRepository dao;

    @Override
    public ResponseEntity<Map<String, Object>> listarTipoHabitaciones() {
        Map<String, Object> respuesta = new HashMap<>();
        List<TipoHabitacion> tipoHabitaciones = dao.findAll();
        
        if (!tipoHabitaciones.isEmpty()) {
            respuesta.put("mensaje", "Lista de tipos de habitación");
            respuesta.put("tipoHabitaciones", tipoHabitaciones);
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> listarTipoHabitacionPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<TipoHabitacion> tipoHabitacion = dao.findById(id);
        
        if (tipoHabitacion.isPresent()) {
            respuesta.put("tipoHabitacion", tipoHabitacion.get());
            respuesta.put("mensaje", "Búsqueda correcta");
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> agregarTipoHabitacion(TipoHabitacion tipoHabitacion) {
        Map<String, Object> respuesta = new HashMap<>();
        dao.save(tipoHabitacion);
        respuesta.put("tipoHabitacion", tipoHabitacion);
        respuesta.put("mensaje", "Se añadió correctamente el tipo de habitación");
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editarTipoHabitacion(TipoHabitacion th, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<TipoHabitacion> tipoHabitacionExiste = dao.findById(id);
        
        if (tipoHabitacionExiste.isPresent()) {
            TipoHabitacion tipoHabitacion = tipoHabitacionExiste.get();
            tipoHabitacion.setDescripcion(th.getDescripcion());
            dao.save(tipoHabitacion);
            respuesta.put("tipoHabitacion", tipoHabitacion);
            respuesta.put("mensaje", "Datos del tipo de habitación modificados");
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarTipoHabitacion(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<TipoHabitacion> tipoHabitacionExiste = dao.findById(id);
        
        if (tipoHabitacionExiste.isPresent()) {
            TipoHabitacion tipoHabitacion = tipoHabitacionExiste.get();
            dao.delete(tipoHabitacion);
            respuesta.put("mensaje", "Eliminado correctamente");
            respuesta.put("status", HttpStatus.NO_CONTENT);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        } else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

}