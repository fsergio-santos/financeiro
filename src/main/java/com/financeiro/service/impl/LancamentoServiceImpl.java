package com.financeiro.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.negocio.Lancamento;
import com.financeiro.repository.LancamentoRepository;
import com.financeiro.repository.filtros.LancamentoFiltro;
import com.financeiro.service.LancamentoService;


@Service
@Transactional
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Transactional(readOnly=true)
	@Override
	public List<Lancamento> listarTodasLancamentos() {
		return lancamentoRepository.findAll();
		
	}

	@Override
	public Lancamento salvar(Lancamento lancamento) {
		return lancamentoRepository.saveAndFlush(lancamento);
	}

	@Override
	public Lancamento findById(Integer id) {
		Lancamento Lancamento = lancamentoRepository.getOne(id);
		if (Lancamento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return Lancamento;
	}

	@Override
	public Lancamento salvar(Integer id, Lancamento lancamento) {
		Lancamento lancamentoSalva = findById(id);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "id");
		return lancamentoRepository.saveAndFlush(lancamentoSalva);
	}

	@Override
	public void delete(Integer id) {
		Lancamento lancamento = findById(id);
		lancamentoRepository.delete(lancamento);
	}

	@Override
	public Page<Lancamento> filtrar(LancamentoFiltro lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

}
