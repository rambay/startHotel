package com.springboot.app.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.springboot.app.model.Habitacion;
import com.springboot.app.repository.HabitacionRepository;
import com.springboot.app.repository.TipoHabitacionRepository;
import com.springboot.app.service.HabitacionService;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HabitacionServiceImplement implements HabitacionService {

    @Autowired
    private HabitacionRepository dao;
    
    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;
    
    @Autowired
    private SpringTemplateEngine templateEngine; 
    
    @Override
    public ResponseEntity<Map<String, Object>> listarHabitaciones() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Habitacion> habitaciones = dao.findAll();
        
        if (!habitaciones.isEmpty()) {
            respuesta.put("mensaje", "Lista de habitaciones");
            respuesta.put("habitaciones", habitaciones);
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
    public ResponseEntity<Map<String, Object>> listarHabitacionPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Habitacion> habitacion = dao.findById(id);
        
        if (habitacion.isPresent()) {
            respuesta.put("habitacion", habitacion.get());
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
    public ResponseEntity<Map<String, Object>> agregarHabitacion(Habitacion habitacion) {
        Map<String, Object> respuesta = new HashMap<>();
        dao.save(habitacion);
        respuesta.put("habitacion", habitacion);
        respuesta.put("mensaje", "Se añadió correctamente la habitación");
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editarHabitacion(Habitacion hab, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Habitacion> habitacionExiste = dao.findById(id);
        
        if (habitacionExiste.isPresent()) {
            Habitacion habitacion = habitacionExiste.get();
            habitacion.setNumeroHabitacion(hab.getNumeroHabitacion());
            habitacion.setCapacidad(hab.getCapacidad());
            habitacion.setImageHabitacion(hab.getImageHabitacion());
            habitacion.setTipo(hab.getTipo());
            habitacion.setPrecioPorNoche(hab.getPrecioPorNoche());
            habitacion.setEstado(hab.getEstado());
            habitacion.setDescripcion(hab.getDescripcion());
            dao.save(habitacion);
            respuesta.put("habitacion", habitacion);
            respuesta.put("mensaje", "Datos de la habitación modificados");
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
    public ResponseEntity<Map<String, Object>> eliminarHabitacion(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Habitacion> habitacionExiste = dao.findById(id);
        
        if (habitacionExiste.isPresent()) {
            Habitacion habitacion = habitacionExiste.get();
            dao.delete(habitacion);
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

	@Override
	public ResponseEntity<Map<String, Object>> filtrarHabitacionesPorFechasYCapacidad(int capacidad, Date fecInicio, Date fecFin) {
        Map<String, Object> response = new HashMap<>();
        List<Habitacion> habitaciones = dao.obtenerHabitacionesPorFechas(capacidad, fecInicio, fecFin);

        if (habitaciones.isEmpty()) {
            response.put("mensaje", "No hay habitaciones disponibles para las fechas y capacidad seleccionadas.");
            return ResponseEntity.ok(response);
        }

        response.put("habitaciones", habitaciones);
        return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> generarReporteHabitaciones() {
		Map<String, Object> respuesta = new HashMap<>();
	    
	   
	    List<Habitacion> habitaciones = dao.findAll();
	    
	    if (habitaciones.isEmpty()) {
	        respuesta.put("mensaje", "No hay habitaciones para mostrar");
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }

	    
	    Context context = new Context();
	    context.setVariable("habitaciones", habitaciones);

	    
	    String htmlContent = templateEngine.process("report-template-habitacion", context);

	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ConverterProperties converterProperties = new ConverterProperties();
	    HtmlConverter.convertToPdf(htmlContent, outputStream, converterProperties);

	    
	    byte[] pdfBytes = outputStream.toByteArray();
	    String base64Content = Base64.getEncoder().encodeToString(pdfBytes);

	    
	    respuesta.put("mensaje", "Reporte generado correctamente");
	    respuesta.put("pdf", base64Content);
	    respuesta.put("status", HttpStatus.OK);
	    
	    return ResponseEntity.ok(respuesta);
	}
	

}