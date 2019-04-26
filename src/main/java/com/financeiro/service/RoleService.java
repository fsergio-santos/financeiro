package com.financeiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.Role;
import com.financeiro.repository.filtros.RoleFiltro;

public interface RoleService {
	
    List<Role> listarTodosRoles();
	
	Role salvar(Role role);

	Role findById(Integer id);

	Role salvar(Integer id, Role role);

	void delete(Integer id);
	
	Page<Role> listRoleWithPagination(RoleFiltro roleFiltro, Pageable pageable);
	
	Role findRole(String role);
	
	Optional <Role> buscarRolePeloNome(String nome);
	
}
