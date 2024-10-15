package com.springboot.app.serviceImplement;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.app.model.Rol;
import com.springboot.app.repository.RolRepository;
import com.springboot.app.service.RolService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RolServiceImpl implements RolService{
	
    private final RolRepository repository;
    
    @Override
	public ResponseEntity<Map<String, Object>> listarRoles() {
		Map<String, Object> response = new HashMap<>();
        List<Rol> roles = repository.findAll();

        if(!roles.isEmpty()) {
            response.put("roles", roles);
            response.put("fecha", new Date());
            response.put("mensaje", "Roles encontrados");
            response.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("fecha", new Date());
            response.put("mensaje", "No se encontro rol");
            response.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
	}

	@Override
	public ResponseEntity<Map<String, Object>> obtenerRolPorId(Long id) {
		Map<String, Object> response = new HashMap<>();
        Optional<Rol> rol = repository.findById(id);

        if(rol.isPresent()) {
            response.put("rol", rol.get());
            response.put("fecha", new Date());
            response.put("mensaje", "Rol encontrado");
            response.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("fecha", new Date());
            response.put("mensaje", "No se encontro rol");
            response.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
	}

	@Override
	public ResponseEntity<Map<String, Object>> registrarRoles(Rol rol) {
		 Map<String, Object> response = new HashMap<>();
	        repository.save(rol);
	        response.put("rol", rol);
	        response.put("fecha", new Date());
	        response.put("mensaje", "Se ha añadido correctamente el rol");
	        response.put("status", HttpStatus.CREATED);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizarRoles(Rol rol, Long id) {
		Map<String, Object> response = new HashMap<>();
        Optional<Rol> rolOptional = repository.findById(id);

        if(rolOptional.isPresent()) {
            Rol rolActual = rolOptional.get();
            repository.save(rolActual);
            response.put("rol", rolActual);
            response.put("fecha", new Date());
            response.put("mensaje", "Rol actualizado correctamente");
            response.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("fecha", new Date());
            response.put("mensaje", "No se encontro rol");
            response.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarRoles(Long id) {
		Map<String, Object> response = new HashMap<>();
        Optional<Rol> rolOptional = repository.findById(id);

        if(rolOptional.isPresent()) {
            repository.deleteById(id);
            response.put("rol", rolOptional.get());
            response.put("fecha", new Date());
            response.put("mensaje", "Rol eliminado correctamente");
            response.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("fecha", new Date());
            response.put("mensaje", "No se encontro rol");
            response.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
	}

}
