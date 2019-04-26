package com.financeiro.model.estatico;

public enum Scopes {

	LEITURA("LEITURA"),
	ESCRITA("ESCRITA"),
	EXCLUSAO("EXCLUSAO");

	private String descricao;

	Scopes(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
