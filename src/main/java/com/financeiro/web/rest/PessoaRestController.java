package com.financeiro.web.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.service.PessoaService;
import com.financeiro.web.error.MensagensRest;
import com.financeiro.web.event.ObjetoCriadoEvent;

@RestController
@RequestMapping(value="/pessoa",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PessoaRestController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="listar_pessoa",method=RequestMethod.GET)
	public List<Pessoa> listarTodasPessoas(){
		return pessoaService.listarTodasPessoas();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/buscar_pessoa/{id}",method=RequestMethod.GET)
    public ResponseEntity<Pessoa> getPessoa(@PathVariable("id") Integer id) {
		Pessoa pessoa = pessoaService.findById(id);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/salvar_pessoa",method=RequestMethod.POST)
	public ResponseEntity<Pessoa> salvarPessoaPost(@Valid @RequestBody Pessoa pessoa,  HttpServletResponse response) {
        pessoa = pessoaService.salvar(pessoa);
        publisher.publishEvent(new ObjetoCriadoEvent(this, response, pessoa.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/salvar_pessoa/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Pessoa> salvarPessoaPut(@PathVariable("id") Integer id, @RequestBody Pessoa pessoa){
		pessoa = pessoaService.salvar(id, pessoa);
		return ResponseEntity.ok(pessoa);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/salvar_pessoa/{id}", method=RequestMethod.DELETE)
	public void remover(@PathVariable Integer id) {
		pessoaService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/buscar_pessoa_documento", method=RequestMethod.GET)
	public ResponseEntity<MensagensRest> buscarPessoaPeloCpf(@RequestParam("cnpjCpf") String cnpjCpf){
		System.out.println("entrando no metodo "+cnpjCpf );
		Optional<Pessoa> pessoa = pessoaService.buscarPessoaPeloCnpjCpf(cnpjCpf);
		MensagensRest msg = new MensagensRest();
		if (pessoa.isPresent()) {
			System.out.println("passando no if ");
		    msg.setId("201");
		    msg.setMensagem("Cpf OU Cnpj já cadastrado");
		}
		return ResponseEntity.ok(msg);
	}
	

}
