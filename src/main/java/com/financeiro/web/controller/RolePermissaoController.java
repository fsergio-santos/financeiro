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

import com.financeiro.model.security.Escopo;
import com.financeiro.model.security.Permissao;
import com.financeiro.model.security.Role;
import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.repository.filtros.RolePermissaoFiltro;
import com.financeiro.service.EscopoService;
import com.financeiro.service.PermissaoService;
import com.financeiro.service.RolePermissaoService;
import com.financeiro.service.RoleService;
import com.financeiro.web.page.PageWrapper;

@Controller
@RequestMapping(value="/direitos")
public class RolePermissaoController {

	@Autowired
	private RolePermissaoService rolePermissaoService;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private EscopoService escopoService;
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public String listarRolePermissao(RolePermissaoFiltro rolePermissaoFiltro, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest, ModelMap model) {
		PageWrapper<RolePermissao> pagina = new PageWrapper<>(rolePermissaoService.listRolePermissaoWithPagination(rolePermissaoFiltro, pageable), httpServletRequest);
		model.addAttribute("pagina", pagina);
		return "/direitos/lista";
	}
	
	@RequestMapping(value="/cadastrar")
	public ModelAndView cadastrarRolePermissao(RolePermissao rolepermissao) {
		ModelAndView mv = new ModelAndView("/direitos/cadastro");
		mv.addObject("rolepermissao",rolepermissao);
		return mv;
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST,params="action=salvar")
	public ModelAndView salvar(@Valid RolePermissao rolePermissao, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return cadastrarRolePermissao(rolePermissao);
		}
		
		RolePermissaoId rpId = new RolePermissaoId(rolePermissao.getPermissaoId().getId(), 
												   rolePermissao.getRoleId().getId(),
			 								       rolePermissao.getScopeId().getId());
		
		rolePermissao.setId(rpId);
		rolePermissaoService.salvar(rolePermissao);
		attr.addFlashAttribute("success", "Registro inserido com sucesso.");
		return new ModelAndView("/direitos/cadastro");
	}

	@RequestMapping(value="/editar/{permissao_id}/{role_id}/{escopo_id}", method=RequestMethod.GET)
	public String editar(@PathVariable("permissao_id") Integer permissao_id, 
			             @PathVariable("role_id") Integer role_id,
			             @PathVariable("escopo_id") Integer escopo_id, ModelMap model) {
		RolePermissaoId rpId = new RolePermissaoId(permissao_id,role_id,escopo_id);
		RolePermissao rolePermissao = rolePermissaoService.findById(rpId);
		model.addAttribute("rolePermissao", rolePermissao);
		return "/direitos/cadastro";
	}
	
	@RequestMapping(value="/editar", method=RequestMethod.POST, params="action=salvar")
	public ModelAndView editarRolePermissao(@Valid RolePermissao rolePermissao, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return cadastrarRolePermissao(rolePermissao);
		}
		RolePermissaoId rpId = new RolePermissaoId(rolePermissao.getPermissaoId().getId(), 
												   rolePermissao.getRoleId().getId(),
												   rolePermissao.getScopeId().getId());
		rolePermissao.setId(rpId);
		rolePermissaoService.salvar(rolePermissao);
		ModelAndView model = new ModelAndView("/direitos/cadastro");
		model.addObject("success","Resgistro alterado com sucesso.");
		return model;
	}
	
	@RequestMapping(value="/excluir/{permissao_id}/{role_id}/{escopo_id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("permissao_id") Integer permissao_id, 
					            @PathVariable("role_id") Integer role_id,
					            @PathVariable("escopo_id") Integer escopo_id) {
		RolePermissaoId rpId = new RolePermissaoId(permissao_id,role_id,escopo_id);
		RolePermissao rolePermissao = rolePermissaoService.findById(rpId);
		ModelAndView model = new ModelAndView("/direitos/excluir");
		model.addObject("rolePermissao", rolePermissao);
		return model;
	}
	
	@RequestMapping(value="/excluir", method=RequestMethod.POST, params="action=excluir")
	public ModelAndView excluirRolePermissao(RolePermissao rolePermissao, RedirectAttributes attr) {
		RolePermissaoId rpId = new RolePermissaoId(rolePermissao.getPermissaoId().getId(), 
												   rolePermissao.getRoleId().getId(),
												   rolePermissao.getScopeId().getId());
		rolePermissao.setId(rpId);
		rolePermissaoService.delete(rolePermissao.getId());
		ModelAndView model = new ModelAndView("/direitos/excluir");
		model.addObject("success","Registro removido com sucesso.");
		return model;
	}
	
	@RequestMapping(value="/consultar/{permissao_id}/{role_id}/{escopo_id}", method=RequestMethod.GET)
	public String consulta(@PathVariable("permissao_id") Integer permissao_id, 
				           @PathVariable("role_id") Integer role_id,
				           @PathVariable("escopo_id") Integer escopo_id, ModelMap model) {
		RolePermissaoId rpId = new RolePermissaoId(permissao_id,role_id,escopo_id);
		RolePermissao rolePermissao = rolePermissaoService.findById(rpId);
		model.addAttribute("rolePermissao", rolePermissao);
		return "/direitos/consultar";
	}
		

	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=cancelar")
	public String cancelarCadastroRolePermissao() {
		return "redirect:/direitos/pesquisar";
	}
	
	@ModelAttribute("roles")
	public List<Role> listaRoles(){
		return roleService.listarTodosRoles();
	}
	
	@ModelAttribute("permissoes")
	public List<Permissao> listaPermissoes(){
		return permissaoService.getAllPermissao();
	}
		
	@ModelAttribute("scopes")
	public List<Escopo> getScopes() {
		return escopoService.listarTodasEscopos();
	}	

	
}
