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

import com.financeiro.model.security.Role;
import com.financeiro.repository.filtros.RoleFiltro;
import com.financeiro.service.RoleService;
import com.financeiro.service.exception.NomeRoleCadastradaException;
import com.financeiro.web.page.PageWrapper;

@Controller
@RequestMapping(value="/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public String listarRole(RoleFiltro roleFiltro, BindingResult result, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest, ModelMap model) {
		PageWrapper<Role> pagina = new PageWrapper<>(roleService.listRoleWithPagination(roleFiltro, pageable), httpServletRequest);
		model.addAttribute("pagina", pagina);
		return "/role/lista";
	}
	
	@RequestMapping(value="/cadastrar")
	public ModelAndView cadastrarRole(Role role) {
		ModelAndView mv = new ModelAndView("/role/cadastro");
		mv.addObject("grupo", role);
		return mv;
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public ModelAndView salvar(@Valid Role role, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return cadastrarRole(role);
		}
		try {
			roleService.salvar(role);
		} catch (NomeRoleCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return cadastrarRole(role);
		} 
		attr.addFlashAttribute("success", "Registro inserido com sucesso.");
		ModelAndView mv = new ModelAndView("/role/cadastro");
		return mv;
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable("id") Integer id, ModelMap model) {
		Role role = roleService.findById(id);
		model.addAttribute("role", role);
		return "/role/cadastro";
	}
	
	@RequestMapping(value="/editar", method=RequestMethod.POST)
	public String editarRole(@Valid Role role, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/role/cadastro";
		}
		roleService.salvar(role);
		attr.addFlashAttribute("success", "Resgistro alterado com sucesso.");
		return "redirect:/role/cadastrar";
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("id") Integer id) {
		Role role = roleService.findById(id);
		ModelAndView model = new ModelAndView("/role/excluir");
		model.addObject("role", role);
		return model;
	}
	
	@RequestMapping(value="/excluir}", method=RequestMethod.POST)
	public String excluirRole(Role role, RedirectAttributes attr) {
		roleService.delete(role.getId());
		attr.addFlashAttribute("success", "Registro removido com sucesso.");
		return "redirect:/role/excluir";
	}
	
	@RequestMapping(value="/consultar/{id}", method=RequestMethod.GET)
	public String consulta(@PathVariable("id") Integer id,  ModelMap model) {
		Role role = roleService.findById(id);
		model.addAttribute("role", role);
		return "/role/consultar";
	}
		

	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=Cancelar")
	public String cancelarCadastroRole() {
		return "redirect:/role/pesquisar";
	}
}
