package com.financeiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.negocio.Telefone;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.repository.filtros.TelefoneFiltro;

public interface TelefoneService  {
	
    List<Telefone> listarTodasTelefones();
	
	Telefone salvar(Telefone telefone);

	Telefone findById(Integer id);

	Telefone salvar(Integer id, Telefone telefone);

	void delete(Integer id);
	
	Page<Telefone> listTelefoneWithPagination(TelefoneFiltro telefoneFiltro, Pageable pageable);
	
	Pessoa findByTelefoneNomePessoa(PessoaFiltro pessoaFiltro);

	Pessoa findByTelefoneIdPessoa(Integer id);
	
	Page<Pessoa> listPessoaWithTelefonePagination(PessoaFiltro pessoaFiltro, Pageable pageable);

	Pessoa adicionaNovoTelefonePessoa(Pessoa pessoa);

	Pessoa removeTelefonePessoa(Pessoa pessoa, int index);

	Pessoa removeTelefonePessoaTabela(Pessoa pessoa, Integer index);

	void salvarTelefonePessoa(Pessoa pessoa);

	void deleteTelefonePessoa(Pessoa pessoa);

}
