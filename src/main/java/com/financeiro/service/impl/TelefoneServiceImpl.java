package com.financeiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.negocio.Telefone;
import com.financeiro.repository.TelefoneRepository;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.repository.filtros.TelefoneFiltro;
import com.financeiro.service.TelefoneService;

@Service
@Transactional
public class TelefoneServiceImpl implements TelefoneService {
	
	@Autowired
	protected TelefoneRepository telefoneRepository;
	
	@Override
	public List<Telefone> listarTodasTelefones() {
		return telefoneRepository.findAll();
	}

	@Override
	public Telefone salvar(Telefone telefone) {
		return telefoneRepository.saveAndFlush(telefone);
	}

	@Override
	public Telefone findById(Integer id) {
		return telefoneRepository.getOne(id);
	}

	@Override
	public Telefone salvar(Integer id, Telefone telefone) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		telefoneRepository.deleteById(id);
	}

	@Override
	public Page<Telefone> listTelefoneWithPagination(TelefoneFiltro telefoneFiltro, Pageable pageable) {
		return null;
	}

	@Override
	public Pessoa findByTelefoneNomePessoa(PessoaFiltro pessoaFiltro) {
		return telefoneRepository.findByTelefoneNomePessoa(pessoaFiltro);
	}

	@Override
	public Pessoa findByTelefoneIdPessoa(Integer id) {
		return telefoneRepository.findByTelefoneIdPessoa(id);
	}

	@Override
	public Page<Pessoa> listPessoaWithTelefonePagination(PessoaFiltro pessoaFiltro, Pageable pageable) {
		return telefoneRepository.listPessoaWithTelefonePagination(pessoaFiltro,pageable);
	}

	@Override
	public Pessoa adicionaNovoTelefonePessoa(Pessoa pessoa) {
		Telefone telefone = new Telefone();
		telefone.setPessoa(pessoa);
		pessoa.getTelefones().add(telefone);
		return pessoa;
	}

	@Override
	public Pessoa removeTelefonePessoa(Pessoa pessoa, int index) {
		pessoa.getTelefones().remove(index);
		return pessoa;
	}

	@Override
	public Pessoa removeTelefonePessoaTabela(Pessoa pessoa, Integer index) {
		Telefone telefone = pessoa.getTelefones().get(index);
	    telefoneRepository.deleteById(telefone.getId());
	    pessoa.getTelefones().remove(telefone);
		return pessoa;
	}

	@Override
	public void salvarTelefonePessoa(Pessoa pessoa) {
		for (Telefone telefone: pessoa.getTelefones()) {
			salvar(telefone);
		}
	}

	@Override
	public void deleteTelefonePessoa(Pessoa pessoa) {
		for (Telefone telefone: pessoa.getTelefones()) {
			telefoneRepository.deleteById(telefone.getId());
		}
		
	}

}
