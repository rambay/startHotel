package com.springboot.app.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.springboot.app.model.Usuario;

public interface UsuarioService {
	public ResponseEntity<Map<String,Object>> listarUsuarios();
	public ResponseEntity<Map<String,Object>> listarPorEstadoUsuario();
	public ResponseEntity<Map<String, Object>> obtenerUsuarioPorId(Long id);
	public ResponseEntity<Map<String, Object>> registrarUsuarios(Usuario usuario);
	public ResponseEntity<Map<String, Object>> actualizarUsuarios(Usuario usuario, Long id);
	public ResponseEntity<Map<String, Object>> eliminarUsuarios(Long id);	
    ResponseEntity<Map<String, Object>> generarReporteUsuarios();

}
