package com.financeiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.Permissao;
import com.financeiro.repository.filtros.PermissaoFiltro;

public interface PermissaoService {
	
	void addRegistroPermissao(Permissao permissao);

	void updateRegistroPermissao(Permissao permissao);

	void removeRegistroPermissao(Permissao permissao);

	List<Permissao> getAllPermissao();

	Permissao findRegistroPermissao(Integer id);

	Page<Permissao> listPermissaoWithPagination(PermissaoFiltro permissaoFiltro, Pageable pageable);
	

}
