package com.financeiro.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.Permissao;
import com.financeiro.repository.filtros.PermissaoFiltro;

public interface PermissaoRepositoryQuery {
	
	public Page<Permissao> listPermissaoWithPagination(PermissaoFiltro permissaoFiltro, Pageable pageable);
	
	public int deleteAllPermissao();

}
