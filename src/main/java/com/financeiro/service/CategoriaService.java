package com.financeiro.service;

import java.util.List;

import com.financeiro.model.negocio.Categoria;

public interface CategoriaService {

	List<Categoria> listarTodasCategorias();
	
	Categoria salvar(Categoria categoria);

	Categoria findById(Integer id);

	Categoria salvar(Integer id, Categoria categoria);

	void delete(Integer id);

}
