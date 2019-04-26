package com.financeiro.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.security.Role;
import com.financeiro.model.security.Usuario;
import com.financeiro.repository.filtros.UserFiltro;
import com.financeiro.service.RoleService;
import com.financeiro.service.UserService;
import com.financeiro.service.exception.EmailUsuarioCadastradoException;
import com.financeiro.web.page.PageWrapper;

@Controller
@RequestMapping(value="/usuario")
public class UserController {

	@Autowired
	private UserService userService;
	
    @Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public String listarUser(UserFiltro userFiltro, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest, ModelMap model) {
		PageWrapper<Usuario> pagina = new PageWrapper<>(userService.listUserWithPagination(userFiltro, pageable), httpServletRequest);
		model.addAttribute("pagina", pagina);
		return "/usuario/lista";
	}
	
	@RequestMapping(value="/cadastrar")
	public String cadastrarUser(Usuario usuario) {
		return "/usuario/cadastro";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid Usuario usuario, BindingResult result,  RedirectAttributes attr) {
		System.out.println(usuario.getRoles());
		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		try {
			userService.salvar(usuario);	
		} catch (EmailUsuarioCadastradoException e) {
		  result.rejectValue("email", e.getMessage(), e.getMessage());
		  return "/usuario/cadastro";
		}
		attr.addFlashAttribute("success", "Registro inserido com sucesso.");
		return "redirect:/usuario/cadastrar";
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable("id") int id, ModelMap model) {
		Usuario usuario = userService.findById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}
	
	@RequestMapping(value="/editar", method=RequestMethod.POST)
	public String editarUser(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		userService.salvar(usuario);
		attr.addFlashAttribute("success", "Resgistro alterado com sucesso.");
		return "redirect:/usuario/cadastrar";
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("id") int id) {
		Usuario usuario = userService.findById(id);
		ModelAndView model = new ModelAndView("/usuario/excluir");
		model.addObject("usuario", usuario);
		return model;
	}
	
	@RequestMapping(value="/excluir}", method=RequestMethod.POST)
	public String excluirUser(Usuario usuario, RedirectAttributes attr) {
		userService.delete(usuario.getId());
		attr.addFlashAttribute("success", "Registro removido com sucesso.");
		return "redirect:/usuario/excluir";
	}
	
	@RequestMapping(value="/consultar/{id}", method=RequestMethod.GET)
	public String consulta(@PathVariable("id") int id,  ModelMap model) {
		Usuario usuario = userService.findById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/consultar";
	}
		

	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=Cancelar")
	public String cancelarCadastroUser() {
		return "redirect:/usuario/pesquisar";
	}
	
	@ModelAttribute("roles")
	public List<Role> listaRoles(){
		return roleService.listarTodosRoles();
	}
}
