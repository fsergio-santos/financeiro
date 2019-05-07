package com.financeiro.repository.query;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.financeiro.model.security.RolePermissao;
import com.financeiro.repository.filtros.RolePermissaoFiltro;

public interface RolePermissaoRepositoryQuery {
	
	Page<RolePermissao> listRolePermissionPagination(RolePermissaoFiltro pessoaFiltro, Pageable pageable);
	
	List<RolePermissao> findByRolePermissao(Integer role_id, Integer escope_id);

}
