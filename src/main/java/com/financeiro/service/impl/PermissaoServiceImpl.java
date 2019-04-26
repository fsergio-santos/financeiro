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

import com.financeiro.model.security.Permissao;
import com.financeiro.repository.PermissaoRepository;
import com.financeiro.repository.filtros.PermissaoFiltro;
import com.financeiro.service.PermissaoService;
import com.financeiro.service.exception.NomePermissaoCadastradoException;

@Service
@Transactional
public class PermissaoServiceImpl implements PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
		
	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_permissao','escrita')")
	public void addRegistroPermissao(Permissao permissao) {
		if (this.permissaoExistente(permissao.getNome()).isPresent()) {
			throw new NomePermissaoCadastradoException("Nome de Permissão já foi cadastrada!");
		} else {
		   permissaoRepository.save(permissao);
		}
	}
	
	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_permissao','escrita')")
	public void updateRegistroPermissao(Permissao permissao) {
		if (this.permissaoExistente(permissao.getNome()).isPresent()) {
			throw new NomePermissaoCadastradoException("Nome de Permissão já foi cadastrada!");
		} else {
			permissaoRepository.saveAndFlush(permissao);
		}
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_permissao','exclusao')")
	public void removeRegistroPermissao(Permissao permissao) {
		Permissao permissaoEncontrada = this.findRegistroPermissao(permissao.getId());
		permissaoRepository.delete(permissaoEncontrada);
	}
	
	@Override
	@Transactional(readOnly=true)
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_permissao','leitura')")
	public List<Permissao> getAllPermissao(){
		return permissaoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_permissao','leitura')")
	public Permissao findRegistroPermissao(Integer id) {
		return permissaoRepository.getOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_permissao','leitura')")
	public Page<Permissao> listPermissaoWithPagination(PermissaoFiltro permissaoFiltro, Pageable pageable){
		return permissaoRepository.listPermissaoWithPagination(permissaoFiltro, pageable);
	}
	
	
	private Optional<Permissao> permissaoExistente(String nomePermissao){
		return permissaoRepository.findByNome(nomePermissao);
	}

	

}
