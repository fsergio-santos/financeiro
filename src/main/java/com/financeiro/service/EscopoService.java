package com.financeiro.service;

import java.util.List;

import com.financeiro.model.security.Escopo;

public interface EscopoService {
	
	List<Escopo> listarTodasEscopos();
	
	Escopo salvar(Escopo escopo);

	Escopo findById(Integer id);

	Escopo salvar(Integer id, Escopo escopo);

	void delete(Integer id);

}
