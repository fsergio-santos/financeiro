package com.financeiro.service.exception;

public class ValorSalarioInvalido extends RuntimeException{

	private static final long serialVersionUID = 6020293352115150052L;
	
	public ValorSalarioInvalido(String message) {
		super(message);
	}

}
