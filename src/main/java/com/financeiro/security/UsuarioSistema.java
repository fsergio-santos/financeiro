package com.financeiro.security;

import org.springframework.security.core.userdetails.User;

import com.financeiro.model.security.Usuario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;

	public UsuarioSistema(Usuario usuario) {
	
		super(  usuario.getEmail(), 
				usuario.getPassword(), 
				usuario.isAccountNonExpired(), 
				usuario.isAccountNonLocked(),
				usuario.isCredentialsNonExpired(),
				usuario.isEnabled(),
				usuario.getAuthorities());
		
		 this.usuario = usuario;
		 
		
	}


	public Usuario getUsuario() {
		return usuario;
	}
	

}
