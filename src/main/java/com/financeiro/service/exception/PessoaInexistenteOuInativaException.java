package com.financeiro.service.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {

	private static final long serialVersionUID = -2929497812485039494L;
	
	public PessoaInexistenteOuInativaException(String message) {
		super(message);
	}

}
