package com.financeiro.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.security.Permissao;
import com.financeiro.repository.filtros.PermissaoFiltro;
import com.financeiro.service.PermissaoService;
import com.financeiro.web.page.PageWrapper;

@Controller
@RequestMapping(value="/permissao")
public class PermissaoController {

	@Autowired
	private PermissaoService permissaoService;
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public String listarPermissao(PermissaoFiltro permissaoFiltro, BindingResult result, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest, ModelMap model) {
		PageWrapper<Permissao> pagina = new PageWrapper<>(permissaoService.listPermissaoWithPagination(permissaoFiltro, pageable), httpServletRequest);
		model.addAttribute("pagina", pagina);
		return "/permissao/lista";
	}
	
	@RequestMapping(value="/cadastrar")
	public String cadastrarPermissao(Permissao permissao) {
		return "/permissao/cadastro";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid Permissao permissao, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/permissao/cadastro";
		}
		permissaoService.addRegistroPermissao(permissao);
		attr.addFlashAttribute("success", "Registro inserido com sucesso.");
		return "redirect:/permissao/cadastrar";
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable("id") Integer id, ModelMap model) {
		Permissao permissao = permissaoService.findRegistroPermissao(id);
		model.addAttribute("permissao", permissao);
		return "/permissao/cadastro";
	}
	
	@RequestMapping(value="/editar", method=RequestMethod.POST)
	public String editarPermissao(@Valid Permissao permissao, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/permissao/cadastro";
		}
		permissaoService.addRegistroPermissao(permissao);
		attr.addFlashAttribute("success", "Resgistro alterado com sucesso.");
		return "redirect:/permissao/cadastrar";
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("id") Integer id) {
		Permissao permissao = permissaoService.findRegistroPermissao(id);
		ModelAndView model = new ModelAndView("/permissao/excluir");
		model.addObject("permissao", permissao);
		return model;
	}
	
	@RequestMapping(value="/excluir}", method=RequestMethod.POST)
	public String excluirPermissao(Permissao permissao, RedirectAttributes attr) {
		permissaoService.removeRegistroPermissao(permissao);
		attr.addFlashAttribute("success", "Registro removido com sucesso.");
		return "redirect:/permissao/excluir";
	}
	
	@RequestMapping(value="/consultar/{id}", method=RequestMethod.GET)
	public String consulta(@PathVariable("id") Integer id,  ModelMap model) {
		Permissao permissao = permissaoService.findRegistroPermissao(id);
		model.addAttribute("permissao", permissao);
		return "/permissao/consultar";
	}
		

	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=Cancelar")
	public String cancelarCadastroPermissao() {
		return "redirect:/permissao/pesquisar";
	}
}
