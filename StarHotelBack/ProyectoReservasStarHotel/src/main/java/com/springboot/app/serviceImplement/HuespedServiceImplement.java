package com.springboot.app.serviceImplement;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.springboot.app.model.Huesped;
import com.springboot.app.repository.HuespedRepository;
import com.springboot.app.service.HuespedService;

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
public class HuespedServiceImplement implements HuespedService {

    @Autowired
    private HuespedRepository dao;
    
    
    @Autowired
    private SpringTemplateEngine templateEngine; 


    @Override
    public ResponseEntity<Map<String, Object>> listarHuespedes() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Huesped> huespedes = dao.findAll();
        
        if (!huespedes.isEmpty()) {
            respuesta.put("mensaje", "Lista de huéspedes");
            respuesta.put("huespedes", huespedes);
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
    public ResponseEntity<Map<String, Object>> listarHuespedPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Huesped> huesped = dao.findById(id);
        
        if (huesped.isPresent()) {
            respuesta.put("huesped", huesped.get());
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
    public ResponseEntity<Map<String, Object>> agregarHuesped(Huesped huesped) {
        Map<String, Object> respuesta = new HashMap<>();
        dao.save(huesped);
        respuesta.put("huesped", huesped);
        respuesta.put("mensaje", "Se añadió correctamente el huésped");
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editarHuesped(Huesped hue, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Huesped> huespedExiste = dao.findById(id);
        
        if (huespedExiste.isPresent()) {
            Huesped huesped = huespedExiste.get();
            huesped.setNombre(hue.getNombre());
            huesped.setApellido(hue.getApellido());
            huesped.setEmail(hue.getEmail());
            huesped.setNombreuser(hue.getNombreuser());
            huesped.setTelefono(hue.getTelefono());
            huesped.setDireccion(hue.getDireccion());
            dao.save(huesped);
            respuesta.put("huesped", huesped);
            respuesta.put("mensaje", "Datos del huésped modificados");
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
    public ResponseEntity<Map<String, Object>> eliminarHuesped(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Huesped> huespedExiste = dao.findById(id);
        
        if (huespedExiste.isPresent()) {
            Huesped huesped = huespedExiste.get();
            dao.delete(huesped);
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
	public ResponseEntity<Map<String, Object>> generarReporteHuespedes() {
		Map<String, Object> respuesta = new HashMap<>();
	    
	    // Paso 1: Listar huéspedes
	    List<Huesped> huespedes = dao.findAll();
	    
	    if (huespedes.isEmpty()) {
	        respuesta.put("mensaje", "No hay huéspedes para mostrar");
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }

	    
	    Context context = new Context();
	    context.setVariable("huespedes", huespedes);

	    
	    String htmlContent = templateEngine.process("report-template-huesped", context);

	    
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