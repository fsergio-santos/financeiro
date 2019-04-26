package com.financeiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.repository.filtros.PessoaFiltro;

public interface PessoaService  {
	
	List<Pessoa> listarTodasPessoas();
	
	Pessoa salvar(Pessoa pessoa);
	
	Pessoa update(Pessoa pessoa);

	Pessoa findById(Integer id);

	Pessoa salvar(Integer id, Pessoa pessoa);

	void delete(Integer id);
	
	Page<Pessoa> listPessoaWithPagination(PessoaFiltro pessoaFiltro, Pageable pageable);
	
	Pessoa adicionaNovoTelefonePessoa(Pessoa pessoa);
	
	Pessoa removeTelefonePessoa(Pessoa pessoa, int index);
	
	Pessoa removeTelefonePessoaTabela(Pessoa pessoa, int index);
	
	void salvarPessoaTelefone(Pessoa pessoa);
	
	Pessoa findByPessoaIdWithTelefone(Integer id);
	
	Optional<Pessoa> buscarPessoaPeloCnpjCpf(String cnpjCpf);

	
	
}
