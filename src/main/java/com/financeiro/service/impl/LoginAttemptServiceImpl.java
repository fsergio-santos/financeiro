package com.financeiro.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import com.financeiro.model.security.LoginAttempt;
import com.financeiro.model.security.Usuario;
import com.financeiro.repository.LoginAttemptRepository;
import com.financeiro.model.security.LoginAttempt;
import com.financeiro.service.LoginAttemptService;
import com.financeiro.service.UserService;

public class LoginAttemptServiceImpl implements LoginAttemptService {
	
	private static int MAX_LOGIN_ATTEMPT_ALLOWED = 5;

	@Autowired
	private LoginAttemptRepository loginAttemptRepository;
	
	@Autowired
	private UserService userService;
		
	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_loginAttempt','escrita')")
	public void addRegistroLoginAttempt(LoginAttempt loginAttempt) {
		   loginAttemptRepository.save(loginAttempt);
	}
	
	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_loginAttempt','escrita')")
	public void updateRegistroLoginAttempt(LoginAttempt loginAttempt) {
		loginAttemptRepository.saveAndFlush(loginAttempt);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_loginAttempt','exclusao')")
	public void removeRegistroLoginAttempt(LoginAttempt loginAttempt) {
		loginAttemptRepository.deleteById(loginAttempt.getId());
	}
	
	@Override
	public Optional<LoginAttempt> updateQtdFalhasAcesso(String username){
		 Usuario usuario = userService.buscarUserAtivoByEmail(username);
		 if (!Objects.isNull(usuario) && usuario.getAtivo()) {
			 LoginAttempt login = loginAttemptRepository.findByUsername(username);
			 if (Objects.isNull(login)) {
				 login = new LoginAttempt();
				 login.setUsername(username);
				 login.setQtdFalhasAcesso(1);
			 } else {
				 login.setQtdFalhasAcesso(login.getQtdFalhasAcesso()+1);
			 }
			 login.setDataAcesso(new Date());
			 login.setHoraAcesso(new Date());
			 if (login.getQtdFalhasAcesso() >= MAX_LOGIN_ATTEMPT_ALLOWED) {
				 usuario.setAtivo(false);
				 userService.salvar(usuario);
			 }
			 return Optional.of(loginAttemptRepository.save(login));
		 }
		 return Optional.empty();
	}
	
	@Override
	public void ressetarQtdFalhasAcesso(String username) {
		LoginAttempt login = loginAttemptRepository.findByUsername(username);
		if (!Objects.isNull(login)) {
			login.setQtdFalhasAcesso(0);
			login.setDataAcesso(new Date());
			login.setHoraAcesso(new Date());
			Usuario usuario = userService.findEmail(username);
			usuario.setAtivo(true);
			userService.salvar(usuario);
		}
	}

}
