package com.financeiro.web.rest;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.model.negocio.Lancamento;
import com.financeiro.repository.filtros.LancamentoFiltro;
import com.financeiro.service.LancamentoService;
import com.financeiro.web.event.ObjetoCriadoEvent;


@RestController
@RequestMapping("/lancamentos")
public class LancamentoRestController {

			
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="listar_lancamento",method=RequestMethod.GET)
	public Page<Lancamento> pesquisar(LancamentoFiltro lancamentoFilter, Pageable pageable) {
		return lancamentoService.filtrar(lancamentoFilter, pageable);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/buscar_lancamento/{id}",method=RequestMethod.GET)
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Integer id) {
		Lancamento lancamento = lancamentoService.findById(id);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/salvar_lancamento",method=RequestMethod.POST)
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new ObjetoCriadoEvent(this, response, lancamentoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/salvar_lancamento/{id}", method=RequestMethod.DELETE)
	public void remover(@PathVariable Integer id) {
		lancamentoService.delete(id);
	}
	
}
