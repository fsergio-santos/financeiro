package com.financeiro.security;

import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.Role;
import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.Usuario;
import com.financeiro.service.UserService;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	
	private UserService userService;
	
	
	public CustomPermissionEvaluator(UserService userService) {
		this.userService = userService;
	}

	@Override
    public boolean hasPermission(Authentication usuarioLogado, Object acesso, Object permissao) {
    	boolean toReturn = false;
		if (usuarioLogado == null || !usuarioLogado.isAuthenticated()) {
			return toReturn;
		} else {
			toReturn = hasPrivilege(usuarioLogado, acesso.toString().toUpperCase(), permissao.toString().toUpperCase());
		} 
		return toReturn;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return false;
    }
    
    public boolean hasRole(Authentication auth, String role) {
		
		boolean toReturn = false;
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		if ( CollectionUtils.isEmpty(authorities)) {
			return toReturn;
		}
		return authorities.stream()
				          .filter(r -> role.equals(r.getAuthority()))
				          .findAny()
				          .isPresent();
	}

	
	private boolean hasPrivilege(Authentication usuarioLogado, String objetoAcesso, String permissao) {
		if ((usuarioLogado == null) || (objetoAcesso == null) || !(permissao instanceof String)) {
            return false;
        }
		return direitoAcessoUsuario(usuarioLogado, objetoAcesso, permissao);
    }
    
    private boolean direitoAcessoUsuario(Authentication usuarioLogado, String objetoAcesso, String permissao) {
    	    	
    	boolean toReturn = false;
    	
    	UsuarioSistema usuarioSistema = (UsuarioSistema) usuarioLogado.getPrincipal();
    	
    	Usuario usuario = userService.findByIdAndRoleAndPermission(usuarioSistema.getUsuario().getId());
  	    
    	for (Role authority : usuario.getRoles()) {

    	    		
			for (RolePermissao rolePermissao : authority.getRolePermissao()) {
					
				
				if ((objetoAcesso.equals(rolePermissao.getPermissaoId().getNome().toUpperCase()))	&& 
					(permissao.equals(rolePermissao.getScopeId().getNome().toUpperCase()))) {
					toReturn = true;
					break;
				}
   
			}
			if ( toReturn == true ) {
				break;
			}
		}
		return toReturn;
    }
            
}
