package com.financeiro.model.estatico;

public enum TipoTelefone {
	
	RESIDENCIAL("Residencial"),
	COMERCIAL("Comercial");
	
	private String descricao;
	
	TipoTelefone(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	
}
