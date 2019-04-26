package com.financeiro.model.dto;

import com.financeiro.util.ValidarEmail;

public class Email {
	
	private String email;
	
	public Email() {
		super();
	}

	public Email(String email) {
		super();
		this.email = email;
	}

	@ValidarEmail
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
