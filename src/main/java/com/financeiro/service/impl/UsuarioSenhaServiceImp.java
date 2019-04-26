package com.financeiro.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.dto.UpdatePassword;
import com.financeiro.model.security.UsuarioSenha;
import com.financeiro.repository.UsuarioSenhaRepository;
import com.financeiro.security.UsuarioSistema;
import com.financeiro.service.UsuarioSenhaService;
import com.financeiro.util.data.DataSistema;

@Service
@Transactional
public class UsuarioSenhaServiceImp implements UsuarioSenhaService {
	
	@Autowired
	private DataSistema dataSistema;

	@Autowired
	private UsuarioSenhaRepository usuarioSenhaRepository;
	
	@Autowired
	private RegistrarUsuarioServiceImpl registrarUsuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Secured(value = { "ROLE_ADMINISTRADOR","ROLE_USUARIO" })
	public boolean buscarUsuarioSenhaPorId(UpdatePassword password) {
		boolean toReturn = false;
		List<UsuarioSenha> usuarioSenhaBanco = usuarioSenhaRepository.findAll();
		for (UsuarioSenha usrSenha : usuarioSenhaBanco ) {
			if (usrSenha.getSenha().equals(encodeUsuarioPassword(password.getNewPassword()))) {
			   toReturn = true;
			   break;
			}	
		}
		return toReturn;
	}

	@Override
	@Secured(value = { "ROLE_ADMINISTRADOR","ROLE_USUARIO" })
	public UsuarioSenha salvarUsuarioSenha(UsuarioSistema usuario_logado, UpdatePassword password) {
		UsuarioSenha usuarioSenha = new UsuarioSenha();
		usuarioSenha.setSenha(password.getNewPassword());
		usuarioSenha.setDataUpdate(new Date());
		usuarioSenha.setUsuario(usuario_logado.getUsuario());
		usuarioSenha.getUsuario().setPassword(password.getNewPassword());
		usuarioSenha.getUsuario().setDataVencimentoSenha(dataSistema.somaData(new Date()));
     	usuarioSenhaRepository.saveAndFlush(usuarioSenha);
		registrarUsuarioService.changeUsuarioPassword(usuario_logado.getUsuario(), password.getNewPassword());
	    return usuarioSenha; 	
	}
	
	
	private String encodeUsuarioPassword(String password) {
		 return passwordEncoder.encode(password);
	}
	
	

}
