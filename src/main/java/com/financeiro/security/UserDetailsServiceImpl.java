package com.financeiro.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.Usuario;
import com.financeiro.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
    @Autowired
    private UserService userService;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = findUserByUsername(username);
        
        if ( Objects.isNull(usuario)) {
        	 
        	 throw new UsernameNotFoundException("Usuário não encontrado!  "+username);
        }
        
        return new UsuarioSistema(getRolesAndPermissions(usuario));
    }
    
    
    private Usuario getRolesAndPermissions(Usuario usuario) {
		return userService.findByIdAndRoleAndPermission(usuario.getId());
	}


	private Usuario findUserByUsername(String username) {
    	return userService.buscarUserAtivoByEmail(username);
    }
    
    
}
