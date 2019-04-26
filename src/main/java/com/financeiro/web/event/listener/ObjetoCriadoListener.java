package com.financeiro.web.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.financeiro.web.event.ObjetoCriadoEvent;

public class ObjetoCriadoListener implements ApplicationListener<ObjetoCriadoEvent> {

	@Override
	public void onApplicationEvent(ObjetoCriadoEvent event) {
		HttpServletResponse response = event.getResponse();
		Integer id = event.getId();
		adicionarHeaderLocation(response, id);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Integer id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
