package com.financeiro.service.impl;

import java.util.List;

import javax.validation.ReportAsSingleViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.dto.ListaRolePermissao;
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
	@PreAuthorize("hasPermission('cadastro_direitos','escrita')")
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
	@PreAuthorize("hasPermission('cadastro_direitos','escrita')")
	public RolePermissao salvar(RolePermissaoId id, RolePermissao rolePermissao) {
		return rolePermissaoRepository.save(rolePermissao);
	}

	@Override
 	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_direitos','exclusao')")
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
	public ListaRolePermissao findByRolePermissao(Integer role_id, Integer escope_id) {
		
		List<RolePermissao> listaPermissao =  rolePermissaoRepository.findByRolePermissao(role_id,escope_id);
        ListaRolePermissao listaRolePermissao = new ListaRolePermissao();
        RolePermissaoId rpId = new RolePermissaoId();
      
        for (RolePermissao rp : listaPermissao) {
        	 rpId.setEscopo_id(rp.getScopeId().getId());
        	 rpId.setPermissao_id(rp.getPermissaoId().getId());
        	 rpId.setRole_id(rp.getRoleId().getId());
        	 listaRolePermissao.setId(rpId); 
             listaRolePermissao.setRole(rp.getRoleId());
             listaRolePermissao.setScope(rp.getScopeId());
             listaRolePermissao.setDataCadastro(rp.getDataCadastro());
             listaRolePermissao.getListaPermissoes().add(rp.getPermissaoId());
        }
    	return listaRolePermissao;
		
	
	}
    
}
