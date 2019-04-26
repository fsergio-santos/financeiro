package com.financeiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.negocio.Lancamento;
import com.financeiro.repository.filtros.LancamentoFiltro;

public interface LancamentoService {
	
	List<Lancamento> listarTodasLancamentos();
	
	Lancamento salvar(Lancamento lancamento);

	Lancamento findById(Integer id);

	Lancamento salvar(Integer id, Lancamento lancamento);

	void delete(Integer id);

	Page<Lancamento> filtrar(LancamentoFiltro lancamentoFilter, Pageable pageable);

}
