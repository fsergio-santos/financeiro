package com.financeiro.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.financeiro.model.negocio.Categoria;
import com.financeiro.model.security.Permissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.model.dto.GrupoPermissaoId;
import com.financeiro.service.PermissaoService;
import com.financeiro.service.RolePermissaoService;

@RestController
@RequestMapping(value="/rolepermissao",
                consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
                produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RolePermissaoRestController {

	@Autowired
	private RolePermissaoService rolePermissaoService;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/excluir",method=RequestMethod.DELETE)
	public void removerRolePermissao(@RequestBody GrupoPermissaoId grupoPermissaoId){
		
		RolePermissaoId id = new RolePermissaoId(grupoPermissaoId.getPermissao_id(),
												 grupoPermissaoId.getRole_id(), 
												 grupoPermissaoId.getEscopo_id());
		
		rolePermissaoService.delete(id);
		
	}
	
	@RequestMapping(value="/fragments")
	public String loadFragmentsRolePermissao(Model model) {
		System.out.println("carregando as permissoes");
		List<Permissao> permissoes = permissaoService.getAllPermissao();
		model.addAttribute("permissoes", permissoes);
		return "cadastro :: permissoes";
		
	}
	
}
