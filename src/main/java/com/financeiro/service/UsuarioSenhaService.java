package com.financeiro.service;

import com.financeiro.model.dto.UpdatePassword;
import com.financeiro.model.security.UsuarioSenha;
import com.financeiro.security.UsuarioSistema;

public interface UsuarioSenhaService {

	boolean buscarUsuarioSenhaPorId(UpdatePassword password);
	
	UsuarioSenha salvarUsuarioSenha(UsuarioSistema usuario_logado, UpdatePassword password);
}
