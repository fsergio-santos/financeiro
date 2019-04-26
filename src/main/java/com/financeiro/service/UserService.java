package com.financeiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.security.Usuario;
import com.financeiro.repository.filtros.UserFiltro;

public interface UserService {
	
    List<Usuario> listarTodosUsers();
    
    Usuario updateRegistroUsuario(Usuario usuario);
	
	Usuario salvar(Usuario user);

	Usuario findById(int id);

	Usuario salvar(int id, Usuario user);

	void delete(int id);
	
	Page<Usuario> listUserWithPagination(UserFiltro userFiltro, Pageable pageable);
	
	Usuario findUserByEmail(String email);
	
	Usuario buscarUserAtivoByEmail(String email);

	Usuario findEmail(String username);
	
	Usuario findByIdAndRoleAndPermission(Integer id);
	

}
