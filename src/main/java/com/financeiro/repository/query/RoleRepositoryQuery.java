package com.financeiro.repository.query;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.Role;
import com.financeiro.repository.filtros.RoleFiltro;

public interface RoleRepositoryQuery {
	
    Page<Role> listRoleWithPagination(RoleFiltro roleFiltro, Pageable pageable);
	
	Role findByRole(String role_name);
	
	Optional<Role> buscarRolePeloNome(String nome);

	
}
