package com.financeiro.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.estatico.Scopes;
import com.financeiro.model.security.Escopo;
import com.financeiro.service.EscopoService;

@Controller
@RequestMapping(value="/escopo")
public class EscopoController {

	@Autowired
	private EscopoService escopoService;
	

	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public String listar(ModelMap model) {
		model.addAttribute("escopos", escopoService.listarTodasEscopos());
		return "/escopo/lista"; 
	}
	
	@RequestMapping(value="/cadastrar")
	public String cadastrarUser(Escopo escopo) {
		return "/escopo/cadastro";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid Escopo escopo, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/escopo/cadastro";
		}
		escopoService.salvar(escopo);
		attr.addFlashAttribute("success", "Registro inserido com sucesso.");
		return "redirect:/escopo/cadastrar";
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable("id") int id, ModelMap model) {
		Escopo escopo = escopoService.findById(id);
		model.addAttribute("escopo", escopo);
		return "/escopo/cadastro";
	}
	
	@RequestMapping(value="/editar", method=RequestMethod.POST)
	public String editarUser(@Valid Escopo escopo, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/escopo/cadastro";
		}
		escopoService.salvar(escopo);
		attr.addFlashAttribute("success", "Resgistro alterado com sucesso.");
		return "redirect:/escopo/cadastrar";
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("id") int id) {
		Escopo escopo = escopoService.findById(id);
		ModelAndView model = new ModelAndView("/escopo/excluir");
		model.addObject("escopo", escopo);
		return model;
	}
	
	@RequestMapping(value="/excluir}", method=RequestMethod.POST)
	public String excluirEscopo(Escopo escopo, RedirectAttributes attr) {
		escopoService.delete(escopo.getId());
		attr.addFlashAttribute("success", "Registro removido com sucesso.");
		return "redirect:/escopo/excluir";
	}
	
	@RequestMapping(value="/consultar/{id}", method=RequestMethod.GET)
	public String consulta(@PathVariable("id") int id,  ModelMap model) {
		Escopo escopo = escopoService.findById(id);
		model.addAttribute("escopo", escopo);
		return "/escopo/consultar";
	}
		

	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=cancelar")
	public String cancelarCadastroUser() {
		return "redirect:/escopo/pesquisar";
	}
	
	@ModelAttribute("scopes")
	public Scopes[] getScopes() {
		return Scopes.values();
	}
}
