package com.financeiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.Escopo;
import com.financeiro.repository.EscopoRepository;
import com.financeiro.service.EscopoService;

@Service
@Transactional
public class EscopoServiceImpl implements EscopoService {
	
	@Autowired
	private EscopoRepository escopoRepository;

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_escopo','leitura')")
	public List<Escopo> listarTodasEscopos() {
		return escopoRepository.findAll();
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_escopo','escrita')")
	public Escopo salvar(Escopo escopo) {
		return escopoRepository.saveAndFlush(escopo);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_escopo','leitura')")
	public Escopo findById(Integer id) {
		return escopoRepository.getOne(id);
	}

	@Override
	public Escopo salvar(Integer id, Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_escopo','exclusao')")
	public void delete(Integer id) {
		escopoRepository.deleteById(id);
	}

}
