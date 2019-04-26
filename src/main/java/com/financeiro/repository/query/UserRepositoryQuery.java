package com.financeiro.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.Usuario;
import com.financeiro.repository.filtros.UserFiltro;

public interface UserRepositoryQuery {
	
	Page<Usuario> listUserWithPagination(UserFiltro userFiltro, Pageable pageable);
	
	Usuario findByEmail(String user_email);
	
    Usuario buscarUserAtivoByEmail(String email);
	
	Usuario findEmail(String email);
	
	Usuario findByIdAndRoleAndPermission(Integer id);

}
