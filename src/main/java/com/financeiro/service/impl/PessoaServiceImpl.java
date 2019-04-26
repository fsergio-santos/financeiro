package com.financeiro.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.negocio.Telefone;
import com.financeiro.repository.PessoaRepository;
import com.financeiro.repository.TelefoneRepository;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.service.PessoaService;
import com.financeiro.service.event.PessoaSalvaEvent;
import com.financeiro.service.exception.CnpjCpfExistente;
import com.financeiro.service.exception.ValorSalarioInvalido;

@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	protected PessoaRepository pessoaRepository;
	
	@Autowired
	protected TelefoneRepository telefoneRepository;

	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional(readOnly=true)
	@Override
	@PostFilter("hasPermission(filterObject,'leitura')")
	public List<Pessoa> listarTodasPessoas() {
		return pessoaRepository.findAll();
	}

	@Override
	@Secured({"ROLE_ADMINISTRADOR","ROLE_USUARIO"})
	@PreAuthorize("hasPermission('cadastro_pessoa','escrita')")
	public Pessoa salvar(Pessoa pessoa) {
		Optional<Pessoa> pessoaCadastrada = buscarPessoaPeloCnpjCpf(pessoa.getCnpjCpf());
		if (pessoaCadastrada.isPresent()) {
			throw new CnpjCpfExistente("CNPJ ou CPF já cadastrado!");
		}
		if (pessoa.getTipoPessoa().equals("FISICA")) {
			throw new ValorSalarioInvalido("O Valor do Salário deve ser informado.");
		}
		Pessoa pessoaSalva = pessoaRepository.saveAndFlush(pessoa);
		publisher.publishEvent(new PessoaSalvaEvent(pessoa));
		return pessoaSalva;
	}
	
	
	@Override
	@Secured({"ROLE_ADMINISTRADOR","ROLE_USUARIO"})
	@PreAuthorize("hasPermission('cadastro_pessoa','escrita')")
	public Pessoa update(Pessoa pessoa) {
		return pessoaRepository.saveAndFlush(pessoa);
	}

	@Override
	@PreAuthorize("hasPermission('cadastro_pessoa','leitura')")
	public Pessoa findById(Integer id) {
		Pessoa pessoa = pessoaRepository.getOne(id);
		if (pessoa == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoa;
	}

	@Override
	@Secured({"ROLE_ADMINISTRADOR","ROLE_USUARIO"})
	@PreAuthorize("hasPermission('cadastro_pessoa','escrita')")
	public Pessoa salvar(Integer id, Pessoa pessoa) {
		Pessoa pessoaSalva = findById(id);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.saveAndFlush(pessoaSalva);
	}

	@Override
	@Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasPermission('cadastro_pessoa','exclusao')")
	public void delete(Integer id) {
		Pessoa pessoa = findById(id);
		List<Telefone> listaTelefone = pessoaRepository.findTelefoneByIdPessoa(id);
		if (listaTelefone.size() != -1 ) {
			for (Telefone telefone : listaTelefone) {
				telefoneRepository.delete(telefone);
			}
		}
		pessoaRepository.delete(pessoa);
	}

	@Override
	@Transactional(readOnly=true)
	@PreAuthorize("hasPermission('cadastro_pessoa','leitura')")
	public Page<Pessoa> listPessoaWithPagination(PessoaFiltro pessoaFiltro, Pageable pageable) {
		return pessoaRepository.listPessoaWithPagination(pessoaFiltro, pageable);
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
	public Pessoa removeTelefonePessoaTabela(Pessoa pessoa, int index) {
		Telefone telefone = pessoa.getTelefones().get(index);
	    telefoneRepository.deleteById(telefone.getId());
	    pessoa.getTelefones().remove(telefone);
		return pessoa;
	}

	@Override
	public void salvarPessoaTelefone(Pessoa pessoa) {
		if (pessoa.getTelefones().size() != -1) {
			for (Telefone tel: pessoa.getTelefones()) {
				tel.setPessoa(pessoa);
				telefoneRepository.saveAndFlush(tel);
			}
			
		}
		
	}

	@Override
	public Pessoa findByPessoaIdWithTelefone(Integer id) {
		return pessoaRepository.findByPessoaIdWithTelefone(id);
	}

	@Override
	public Optional<Pessoa> buscarPessoaPeloCnpjCpf(String cnpjCpf) {
		return pessoaRepository.buscarPessoaPeloCnpjCpf(cnpjCpf);
	}
	
}
