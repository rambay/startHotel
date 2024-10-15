package com.springboot.app.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.springboot.app.model.Rol;

public interface RolService {
    public ResponseEntity<Map<String,Object>> listarRoles();
    public ResponseEntity<Map<String, Object>> obtenerRolPorId(Long id);
    public ResponseEntity<Map<String, Object>> registrarRoles(Rol rol);
    public ResponseEntity<Map<String, Object>> actualizarRoles(Rol rol, Long id);
    public ResponseEntity<Map<String, Object>> eliminarRoles(Long id);
}