package com.financeiro.web.registrar;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.financeiro.model.security.Usuario;

public class RegistrarUsuario extends ApplicationEvent {

	private static final long serialVersionUID = -8209471723168920668L;
	
	private final String url;
	private final Locale locale;
	private final Usuario usuario;
	
	public RegistrarUsuario( Usuario usuario, Locale locale, String url) {
		super(usuario);
		this.url = url;
		this.locale = locale;
		this.usuario = usuario;
	}

	public String getUrl() {
		return url;
	}

	public Locale getLocale() {
		return locale;
	}

	public Usuario getUsuario() {
		return usuario;
	}



}
