package com.financeiro.web.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ObjetoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7967096268738284391L;

	private HttpServletResponse response;
	private Integer id;
	
	public ObjetoCriadoEvent(Object source, HttpServletResponse response, Integer id) {
		super(source);
		this.response = response;
		this.id = id;
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Integer getId() {
		return id;
	}
	
	

}
