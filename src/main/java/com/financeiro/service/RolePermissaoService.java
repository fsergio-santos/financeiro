package com.financeiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.repository.filtros.RolePermissaoFiltro;

public interface RolePermissaoService {
	
    List<RolePermissao> listarTodosRolePermissaos();
	
	RolePermissao salvar(RolePermissao role);

	RolePermissao findById(RolePermissaoId id);

	RolePermissao salvar(RolePermissaoId id, RolePermissao role);

	void delete(RolePermissaoId id);
	
	Page<RolePermissao> listRolePermissaoWithPagination(RolePermissaoFiltro rolePermissaoFiltro, Pageable pageable);
	
	List<RolePermissao> findByRolePermissao(Integer role_id);
	
}
