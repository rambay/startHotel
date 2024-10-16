package com.springboot.app.serviceImplement;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.springboot.app.model.Reservacion;
import com.springboot.app.repository.ReservacionRepository;
import com.springboot.app.service.ReservacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservacionServiceImplement implements ReservacionService {

    @Autowired
    private ReservacionRepository dao;
    
    @Autowired
    private SpringTemplateEngine templateEngine; 

    @Override
    public ResponseEntity<Map<String, Object>> listarReservaciones() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Reservacion> reservaciones = dao.findAll();
        
        if (!reservaciones.isEmpty()) {
            respuesta.put("mensaje", "Lista de reservaciones");
            respuesta.put("reservaciones", reservaciones);
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
    public ResponseEntity<Map<String, Object>> listarReservacionPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Reservacion> reservacion = dao.findById(id);
        
        if (reservacion.isPresent()) {
            respuesta.put("reservacion", reservacion.get());
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
    public ResponseEntity<Map<String, Object>> agregarReservacion(Reservacion reservacion) {
        Map<String, Object> respuesta = new HashMap<>();
        dao.save(reservacion);
        respuesta.put("reservacion", reservacion);
        respuesta.put("mensaje", "Se añadió correctamente la reservación");
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editarReservacion(Reservacion reserv, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Reservacion> reservacionExiste = dao.findById(id);
        
        if (reservacionExiste.isPresent()) {
            Reservacion reservacion = reservacionExiste.get();
            reservacion.setHuesped(reserv.getHuesped());
            reservacion.setHabitacion(reserv.getHabitacion());
            reservacion.setFechaIni(reserv.getFechaIni());
            reservacion.setFechaFin(reserv.getFechaFin());
            reservacion.setEstadoReservacion(reserv.getEstadoReservacion());
            reservacion.setTotalPago(reserv.getTotalPago());
            dao.save(reservacion);
            respuesta.put("reservacion", reservacion);
            respuesta.put("mensaje", "Datos de la reservación modificados");
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
    public ResponseEntity<Map<String, Object>> eliminarReservacion(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Reservacion> reservacionExiste = dao.findById(id);
        
        if (reservacionExiste.isPresent()) {
            Reservacion reservacion = reservacionExiste.get();
            dao.delete(reservacion);
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
	public ResponseEntity<Map<String, Object>> generarReporteReservaciones() {
		Map<String, Object> respuesta = new HashMap<>();
	    
		   
	    List<Reservacion> reservaciones = dao.findAll();
	    
	    if (reservaciones.isEmpty()) {
	        respuesta.put("mensaje", "No hay reservaciones para mostrar");
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }

	    
	    Context context = new Context();
	    context.setVariable("reservaciones", reservaciones);

	    
	    String htmlContent = templateEngine.process("report-template-reservacion", context);

	    
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