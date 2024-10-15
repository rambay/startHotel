package com.springboot.app.serviceImplement;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.springboot.app.model.Servicio;
import com.springboot.app.repository.ServicioRepository;
import com.springboot.app.service.ServicioService;

@Service
public class ServicioServiceImplement implements ServicioService{
	
	@Autowired
	private ServicioRepository dao;
	
	@Autowired
    private SpringTemplateEngine templateEngine; 

	@Override
	public ResponseEntity<Map<String, Object>> listarServicios() {
		Map<String, Object> respuesta = new HashMap<>();    
	    List<Servicio> servicios = dao.findAll();
	    
	    if (!servicios.isEmpty()) {
	        respuesta.put("mensaje", "Lista de servicios");
	        respuesta.put("servicios", servicios);
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
	public ResponseEntity<Map<String, Object>> listarServiciosPorId(Long id) {
		Map<String, Object> respuesta = new HashMap<>();        
	    Optional<Servicio> servicio = dao.findById(id);
	    
	    if (servicio.isPresent()) {
	        respuesta.put("servicio", servicio.get());
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
	public ResponseEntity<Map<String, Object>> agregarServicios(Servicio servicio) {
		Map<String, Object> respuesta = new HashMap<>();                
	    dao.save(servicio);
	    respuesta.put("servicio", servicio);
	    respuesta.put("mensaje", "Se añadió correctamente el servicio");
	    respuesta.put("status", HttpStatus.CREATED);
	    respuesta.put("fecha", new Date());    
	    return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);    
	}

	@Override
	public ResponseEntity<Map<String, Object>> editarServicios(Servicio srv, Long id) {
		Map<String, Object> respuesta = new HashMap<>();    
	    Optional<Servicio> servicioExiste = dao.findById(id);        
	    if (servicioExiste.isPresent()) {
	        Servicio servicio = servicioExiste.get();
	        servicio.setDescripcion(srv.getDescripcion());
	        servicio.setPrecio(srv.getPrecio());
	        dao.save(servicio);
	        respuesta.put("servicio", servicio);
	        respuesta.put("mensaje", "Datos del servicio modificados");
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
	public ResponseEntity<Map<String, Object>> eliminarServicios(Long id) {
		Map<String, Object> respuesta = new HashMap<>();    
	    Optional<Servicio> servicioExiste = dao.findById(id);    
	    if (servicioExiste.isPresent()) {
	        Servicio servicio = servicioExiste.get();
	        dao.delete(servicio);
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
	public ResponseEntity<Map<String, Object>> generarReporteServicios() {
		Map<String, Object> respuesta = new HashMap<>();
	    
	    List<Servicio> servicios = dao.findAll();
	    
	    if (servicios.isEmpty()) {
	        respuesta.put("mensaje", "No hay servicios para mostrar");
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }

	    
	    Context context = new Context();
	    context.setVariable("servicios", servicios);

	    
	    String htmlContent = templateEngine.process("report-template-servicios", context);

	    
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
