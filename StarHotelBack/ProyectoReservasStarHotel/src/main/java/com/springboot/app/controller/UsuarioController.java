package com.springboot.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.model.Usuario;
import com.springboot.app.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200/")
@AllArgsConstructor
public class UsuarioController {
	
	private final UsuarioService service;
	
	@GetMapping
	@Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados en el sistema.")
	public ResponseEntity<Map<String, Object>> listar(){
		return service.listarUsuarios();
	}
	
	@GetMapping("/active")
	@Operation(summary = "Listar todos los usuarios activos", description = "Obtiene una lista de todos los usuarios activos registrados en el sistema.")
	public ResponseEntity<Map<String, Object>> listarUsuariosActivos(){
		return service.listarPorEstadoUsuario();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener un usuario por su ID", description = "Busca y devuelve un usuario específico basado en el ID proporcionado.")
	public ResponseEntity<Map<String, Object>> listarPorID(@PathVariable Long id){
		return service.obtenerUsuarioPorId(id);
	}
	
	@PostMapping
	@Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema con los datos proporcionados.")
	public ResponseEntity<Map<String, Object>> agregar(@RequestBody Usuario usuario) {
		return service.registrarUsuarios(usuario);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar un usuario existente por su ID", description = "Actualiza los datos de un usuario existente según el ID proporcionado.")
	public ResponseEntity<Map<String, Object>> editar(@RequestBody Usuario usuario, @PathVariable Long id) {
		return service.actualizarUsuarios(usuario, id);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un usuario por su ID", description = "Elimina un usuario específico del sistema utilizando su ID.")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
		return service.eliminarUsuarios(id);
	}
	
	@GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> generarReporte() {
        return service.generarReporteUsuarios();
    }
}
