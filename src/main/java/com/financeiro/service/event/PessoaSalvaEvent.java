package com.financeiro.service.event;

import org.springframework.util.StringUtils;

import com.financeiro.model.negocio.Pessoa;

public class PessoaSalvaEvent {

	private Pessoa pessoa;

	public PessoaSalvaEvent(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(pessoa.getFoto());
	}
	
}
