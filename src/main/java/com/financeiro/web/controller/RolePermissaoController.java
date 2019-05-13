package com.financeiro.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.dto.ListaRolePermissao;
import com.financeiro.model.security.Escopo;
import com.financeiro.model.security.Permissao;
import com.financeiro.model.security.Role;
import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.repository.RolePermissaoRepository;
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
	
	private List<Permissao> listaPermissao;
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public String listarRolePermissao(RolePermissaoFiltro rolePermissaoFiltro, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest, ModelMap model) {
		PageWrapper<RolePermissao> pagina = new PageWrapper<>(rolePermissaoService.listRolePermissaoWithPagination(rolePermissaoFiltro, pageable), httpServletRequest);
		model.addAttribute("pagina", pagina);
		return "/direitos/lista";
	}
	
	@RequestMapping(value="/incluir")
	public ModelAndView incluirRolePermissao(ListaRolePermissao rolepermissao) {
		ModelAndView mv = new ModelAndView("/direitos/incluir");
		mv.addObject("rolepermissao",rolepermissao);
		return mv;
	}
	
	@RequestMapping(value="/alterar")
	public ModelAndView alterarRolePermissao(ListaRolePermissao rolepermissao) {
		ModelAndView mv = new ModelAndView("/direitos/alterar");
		mv.addObject("rolepermissao",rolepermissao);
		return mv;
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST,params="action=salvar")
	public ModelAndView salvar(@Valid ListaRolePermissao rolepermissao, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return incluirRolePermissao(rolepermissao);
		}
		registrarRolePermissao(rolepermissao);
		ModelAndView mv = new ModelAndView("/direitos/incluir");
		ListaRolePermissao rolePermissao = new ListaRolePermissao();
		mv.addObject("success", "Registro inserido com sucesso.");
		mv.addObject("rolepermissao", rolePermissao);
		return mv;
	}


	@RequestMapping(value="/editar/{permissao_id}/{role_id}/{escopo_id}", method=RequestMethod.GET)
	public String editar(@PathVariable("permissao_id") Integer permissao_id, 
			             @PathVariable("role_id") Integer role_id,
			             @PathVariable("escopo_id") Integer escopo_id, ModelMap model) {
		ListaRolePermissao rolepermissao = new ListaRolePermissao();
		rolepermissao = rolePermissaoService.findByRolePermissao(role_id, escopo_id);
		listaPermissao = new ArrayList<>();
		for (Permissao lista: rolepermissao.getListaPermissoes()) {
			listaPermissao.add(lista);
		}
		model.addAttribute("rolepermissao", rolepermissao);
		return "/direitos/alterar";
	}
	
	@RequestMapping(value="/editar", method=RequestMethod.POST, params="action=salvar")
	public ModelAndView editarRolePermissao(ListaRolePermissao rolepermissao, RedirectAttributes attr) {
		/*if (result.hasErrors()) {
			return altararRolePermissao(rolepermissao);
		}*/
		ModelAndView model = new ModelAndView("/direitos/alterar");
		model.addObject("success","Resgistro alterado com sucesso.");
		if (!Objects.isNull(listaPermissao)) {
			if ( rolepermissao.getListaPermissoes().size() < listaPermissao.size()) {
				for ( int i = 0 ; i < listaPermissao.size(); i++) {
					Permissao permissao = listaPermissao.get(i);
					if (!rolepermissao.getListaPermissoes().contains(permissao)) {
						RolePermissaoId id = new RolePermissaoId();
				        id.setRole_id(rolepermissao.getId().getRole_id());
				        id.setEscopo_id(rolepermissao.getId().getEscopo_id());
				        id.setPermissao_id(permissao.getId());
	
				        rolePermissaoService.delete(id);
				        
					}
				}
			}else {
				registrarRolePermissao(rolepermissao);
			}
		}
		rolepermissao = rolePermissaoService.findByRolePermissao(rolepermissao.getId().getRole_id(), rolepermissao.getId().getEscopo_id());
		model.addObject("rolepermissao", rolepermissao);
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
	
	private void registrarRolePermissao(ListaRolePermissao rolepermissao) {
		RolePermissaoId rpId = new RolePermissaoId();
		for ( Permissao permissao :rolepermissao.getListaPermissoes()) {
			 rpId.setPermissao_id(permissao.getId());
			 rpId.setRole_id(rolepermissao.getRole().getId());
			 rpId.setEscopo_id(rolepermissao.getScope().getId());
			 RolePermissao rolePermissao = new RolePermissao();
 			 rolePermissao.setId(rpId);
 			 rolePermissao.setDataCadastro(rolepermissao.getDataCadastro());
     		 rolePermissaoService.salvar(rolePermissao);
		}
	}

	
}
