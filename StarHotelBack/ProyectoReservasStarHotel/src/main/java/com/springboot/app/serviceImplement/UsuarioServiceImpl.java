package com.springboot.app.serviceImplement;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.springboot.app.model.EstadoUsuario;
import com.springboot.app.model.Usuario;
import com.springboot.app.repository.UsuarioRepository;
import com.springboot.app.service.UsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
	
	private final UsuarioRepository repository;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private SpringTemplateEngine templateEngine; 

	
	@Override
	public ResponseEntity<Map<String, Object>> listarUsuarios() {
		Map<String, Object> respuesta = new HashMap<>();
		List<Usuario> usuarios = repository.findAll();
		
		if(!usuarios.isEmpty()) {
			respuesta.put("mensaje", "Listado de usuarios");
			respuesta.put("usuarios", usuarios);
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
	public ResponseEntity<Map<String, Object>> listarPorEstadoUsuario() {
		Map<String, Object> respuesta = new HashMap<>();
		
	    List<Usuario> usuarios = repository.findByEstado(EstadoUsuario.ACTIVO);
		
		if(!usuarios.isEmpty()) {
			respuesta.put("mensaje", "Listado de usuarios activos");
			respuesta.put("usuarios", usuarios);
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
	public ResponseEntity<Map<String, Object>> obtenerUsuarioPorId(Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Usuario> usuarios = repository.findById(id);
		
		if(usuarios.isPresent()) {
			respuesta.put("usuarios", usuarios);
			respuesta.put("mensaje", "Busquedad correcta");
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
	public ResponseEntity<Map<String, Object>> registrarUsuarios(Usuario usuario) {
		Map<String, Object> respuesta = new HashMap<>();
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
		repository.save(usuario);
		respuesta.put("usuarios", usuario);
		respuesta.put("mensaje", "Se ha añadido correctamente el usuario");
		respuesta.put("status", HttpStatus.CREATED);
		respuesta.put("fecha", new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizarUsuarios(Usuario usuario, Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Usuario> usuarioExiste = repository.findById(id);
		
		if(usuarioExiste.isPresent()) {
			Usuario user = usuarioExiste.get();
            user.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
			repository.save(user);
			respuesta.put("usuarios", user);
			respuesta.put("mensaje", "Datos del usuario modificado");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
		} else {
			respuesta.put("mensaje", "Sin registros con ID: " + id);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarUsuarios(Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Usuario> usuarioExiste = repository.findById(id);
		
		if(usuarioExiste.isPresent()) {
			Usuario usuario = usuarioExiste.get();
			usuario.setEstado(EstadoUsuario.INACTIVO);
			repository.save(usuario);
			respuesta.put("mensaje", "Usuario Eliminado");
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
	public ResponseEntity<Map<String, Object>> generarReporteUsuarios() {
		Map<String, Object> respuesta = new HashMap<>();
	    
		   
	    List<Usuario> usuarios = repository.findAll();
	    
	    if (usuarios.isEmpty()) {
	        respuesta.put("mensaje", "No hay usuarios para mostrar");
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }

	    
	    Context context = new Context();
	    context.setVariable("usuarios", usuarios);

	    
	    String htmlContent = templateEngine.process("report-template-usuario", context);

	    
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
