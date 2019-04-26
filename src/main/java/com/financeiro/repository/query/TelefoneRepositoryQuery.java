package com.financeiro.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.repository.filtros.PessoaFiltro;

public interface TelefoneRepositoryQuery {  
	
	Page<Pessoa> listPessoaWithTelefonePagination(PessoaFiltro pessoaFiltro, Pageable pageable);
	
	Pessoa findByTelefoneNomePessoa(PessoaFiltro pessoaFiltro);
		
	Pessoa findByTelefoneIdPessoa(Integer id);

}
