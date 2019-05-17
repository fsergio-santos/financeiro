package com.financeiro.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.QuantidadesAcessoLogin;
import com.financeiro.model.security.Usuario;
import com.financeiro.repository.QuantidadeAcessoLoginRepository;
import com.financeiro.model.security.QuantidadesAcessoLogin;
import com.financeiro.service.QuantidadeAcessoLoginService;
import com.financeiro.service.UserService;


@Service
@Transactional
public class QuantidadeAcessoLoginServiceImpl implements QuantidadeAcessoLoginService {
	
	private static int MAX_LOGIN_ATTEMPT_ALLOWED = 5;
	

	@Autowired
	private QuantidadeAcessoLoginRepository loginAttemptRepository;
	
	@Autowired
	private UserService userService;
		
	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_loginAttempt','escrita')")
	public void addRegistroLoginAttempt(QuantidadesAcessoLogin loginAttempt) {
		   loginAttemptRepository.save(loginAttempt);
	}
	
	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_loginAttempt','escrita')")
	public void updateRegistroLoginAttempt(QuantidadesAcessoLogin loginAttempt) {
		loginAttemptRepository.saveAndFlush(loginAttempt);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_loginAttempt','exclusao')")
	public void removeRegistroLoginAttempt(QuantidadesAcessoLogin loginAttempt) {
		loginAttemptRepository.deleteById(loginAttempt.getId());
	}
	
	@Override
	public Optional<QuantidadesAcessoLogin> updateQtdFalhasAcesso(String username){
		 Usuario usuario = userService.buscarUserAtivoByEmail(username);
		 if (!Objects.isNull(usuario) && usuario.getAtivo()) {
			 QuantidadesAcessoLogin login = loginAttemptRepository.findByUsername(username);
			 if (Objects.isNull(login)) {
				 login = new QuantidadesAcessoLogin();
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
		QuantidadesAcessoLogin login = loginAttemptRepository.findByUsername(username);
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
