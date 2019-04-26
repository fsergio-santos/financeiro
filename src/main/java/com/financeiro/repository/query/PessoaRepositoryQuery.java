package com.financeiro.repository.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.negocio.Telefone;
import com.financeiro.repository.filtros.PessoaFiltro;

public interface PessoaRepositoryQuery {
	
	Page<Pessoa> listPessoaWithPagination(PessoaFiltro pessoaFiltro, Pageable pageable);
	
	List<Pessoa> findByNomeStartingWithIgnoreCase(String nome);
	
	Pessoa findByPessoaIdWithTelefone(Integer id);
	
	List<Telefone> findTelefoneByIdPessoa(Integer id);
	
	Optional<Pessoa> buscarPessoaPeloCnpjCpf(String cnpjCpf);

}
