package com.financeiro.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.repository.RolePermissaoRepository;
import com.financeiro.repository.filtros.RolePermissaoFiltro;
import com.financeiro.service.RolePermissaoService;

@Service
@Transactional
public class RolePermissaoServiceImpl implements RolePermissaoService {
	
    @Autowired
	private RolePermissaoRepository rolePermissaoRepository;
	

	@Override
	@Transactional(readOnly=true)
	public List<RolePermissao> listarTodosRolePermissaos() {
		return rolePermissaoRepository.findAll();
	}

	@Override
	@Secured({"ROLE_ADMINISTRADOR","ROLE_USUARIO"})
	@PreAuthorize("hasPermission('cadastro_role_permissao','escrita')")
	public RolePermissao salvar(RolePermissao rolePermissao) {
		return rolePermissaoRepository.saveAndFlush(rolePermissao);
	}

	@Override
	@Transactional(readOnly=true)
	public RolePermissao findById(RolePermissaoId id) {
		return rolePermissaoRepository.getOne(id);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_role_permissao','escrita')")
	public RolePermissao salvar(RolePermissaoId id, RolePermissao rolePermissao) {
		return rolePermissaoRepository.save(rolePermissao);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_role_permissao','exclusao')")
	public void delete(RolePermissaoId id) {
		RolePermissao rolePermissao = findById(id);
		rolePermissaoRepository.delete(rolePermissao);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<RolePermissao> listRolePermissaoWithPagination(RolePermissaoFiltro rolePermissaoFiltro, Pageable pageable) {
		return rolePermissaoRepository.listRolePermissionPagination(rolePermissaoFiltro, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<RolePermissao> findByRolePermissao(Integer role_id) {
		return rolePermissaoRepository.findByRolePermissao(role_id);
	}
    
}
