package com.financeiro.web.rest;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.model.negocio.Categoria;
import com.financeiro.service.CategoriaService;
import com.financeiro.web.event.ObjetoCriadoEvent;

@RestController
@RequestMapping(value="/categoria",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoriaRestController {

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="listar_categoria",method=RequestMethod.GET)
	public List<Categoria> listarTodasCategorias(){
		return categoriaService.listarTodasCategorias();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/buscar_categoria/{id}",method=RequestMethod.GET)
    public ResponseEntity<Categoria> getCategoria(@PathVariable("id") Integer id) {
		Categoria categoria = categoriaService.findById(id);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/salvar_categoria",method=RequestMethod.POST)
	public ResponseEntity<Categoria> salvarCategoriaPost(@Valid @RequestBody Categoria categoria,  HttpServletResponse response) {
        categoria = categoriaService.salvar(categoria);
        publisher.publishEvent(new ObjetoCriadoEvent(this, response, categoria.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/salvar_categoria/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Categoria> salvarCategoriaPut(@PathVariable("id") Integer id, @RequestBody Categoria categoria){
		categoria = categoriaService.salvar(id, categoria);
		return ResponseEntity.ok(categoria);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/salvar_categoria/{id}", method=RequestMethod.DELETE)
	public void remover(@PathVariable Integer id) {
		categoriaService.delete(id);
	}
	
	
}
