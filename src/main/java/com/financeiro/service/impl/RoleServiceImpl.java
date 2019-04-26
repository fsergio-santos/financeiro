package com.financeiro.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.Role;
import com.financeiro.repository.RoleRepository;
import com.financeiro.repository.filtros.RoleFiltro;
import com.financeiro.service.RoleService;
import com.financeiro.service.exception.NomeRoleCadastradaException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
    @Autowired
	private RoleRepository roleRepository;
	

	@Override
	@Transactional(readOnly=true)
	public List<Role> listarTodosRoles() {
		return roleRepository.findAll();
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_role','escrita')")
	public Role salvar(Role role) {
		Optional<Role> roleExitente = buscarRolePeloNome(role.getNome());
		if (roleExitente.isPresent()) {
			throw new NomeRoleCadastradaException("Essa Role já foi cadastrada.");
		}
		return roleRepository.saveAndFlush(role);
	}

	@Override
	@Transactional(readOnly=true)
	public Role findById(Integer id) {
		return roleRepository.getOne(id);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_role','escrita')")
	public Role salvar(Integer id, Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_role','exclusao')")
	public void delete(Integer id) {
		Role role = findById(id);
		roleRepository.delete(role);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Role> listRoleWithPagination(RoleFiltro roleFiltro, Pageable pageable) {
		return roleRepository.listRoleWithPagination(roleFiltro, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Role findRole(String role) {
		return roleRepository.findByRole(role);
	}

	@Override
	public Optional<Role> buscarRolePeloNome(String nome) {
		return roleRepository.buscarRolePeloNome(nome);
	}


}
