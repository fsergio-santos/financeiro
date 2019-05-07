package com.financeiro.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.financeiro.model.negocio.Categoria;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.service.RolePermissaoService;

@RestController
@RequestMapping(value="/rolepermissao",
                consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
                produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RolePermissaoRestController {

	@Autowired
	private RolePermissaoService rolePermissaoService;
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/excluir",method=RequestMethod.DELETE)
	public void removerRolePermissao(@RequestBody RolePermissaoId rolePermissaoId){
		
		System.out.println("passando aqui.....................");
		
/*		RolePermissaoId id = new RolePermissaoId(Integer.parseInt(permissao_id),
				                                 Integer.parseInt(role_id),
				                                 Integer.parseInt(escopo_id));
*/		
		//rolePermissaoService.delete(id);
		
	}
	
}
