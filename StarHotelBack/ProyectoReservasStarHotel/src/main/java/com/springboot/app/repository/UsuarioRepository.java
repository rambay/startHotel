package com.springboot.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.model.EstadoUsuario;
import com.springboot.app.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findOneByEmail(String email);
	List<Usuario> findByEstado(EstadoUsuario estado);
}
