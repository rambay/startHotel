package com.springboot.app.serviceImplement;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.app.model.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailImplement implements UserDetails{
	private final Usuario usuario;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		 return usuario.getContrasena();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUser() {
        return usuario.getNombre();
    }

    public Long getId() {
        return usuario.getId();
    }

    public String getRol() {
        return usuario.getRol().getNombre();
    }
}
