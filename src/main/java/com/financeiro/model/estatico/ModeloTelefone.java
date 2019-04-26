package com.financeiro.model.estatico;

public enum ModeloTelefone {
	
	FIXO("Fixo"),
	CELULAR("Celular");
	
	private String descricao;

	ModeloTelefone(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	

}
