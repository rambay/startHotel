package com.springboot.app.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.app.model.Usuario;
import com.springboot.app.repository.UsuarioRepository;

@Service
public class UserServiceImplement implements UserDetailsService{
	@Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con : "+ email + "no existe."));
        return new UserDetailImplement(usuario);
    }
}
