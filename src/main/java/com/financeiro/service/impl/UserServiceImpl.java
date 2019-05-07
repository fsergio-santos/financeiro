package com.financeiro.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.Usuario;
import com.financeiro.model.security.UsuarioSenha;
import com.financeiro.repository.UserRepository;
import com.financeiro.repository.UsuarioSenhaRepository;
import com.financeiro.repository.filtros.UserFiltro;
import com.financeiro.service.UserService;
import com.financeiro.service.exception.EmailUsuarioCadastradoException;
import com.financeiro.util.data.DataSistema;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private DataSistema dataSistema;
	
    @Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioSenhaRepository usuarioSenhaRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Usuario> listarTodosUsers() {
		return userRepository.findAll();
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_usuario','escrita')")
	public Usuario salvar(Usuario user) {
		if (emailExiste(user.getEmail())) {
			throw new EmailUsuarioCadastradoException("UsuÃ¡rio jÃ¡ cadastrado!");
		}
		user.setAtivo(true);
		user.setDataVencimentoSenha(dataSistema.somaData(new Date()));
		user.setPassword(encodeUsuarioPassword(user.getPassword()));
		UsuarioSenha usuarioSenha = new UsuarioSenha();
		usuarioSenha.setSenha(user.getPassword());
		usuarioSenha.setDataUpdate(new Date());
		usuarioSenha.setUsuario(user);
		
	 	usuarioSenhaRepository.saveAndFlush(usuarioSenha);
		return userRepository.saveAndFlush(user);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findById(int id) {
		return userRepository.getOne(id);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_usuario','escrita')")
	public Usuario salvar(int id, Usuario user) {
		user.setPassword(encodeUsuarioPassword(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_usuario','exclusao')")
	public void delete(int id) {
		Usuario user = findById(id);
		userRepository.delete(user);
	}

	@Override
	@Transactional(readOnly=true)
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_usuario','leitura')")
	public Page<Usuario> listUserWithPagination(UserFiltro userFiltro, Pageable pageable) {
		return userRepository.listUserWithPagination(userFiltro, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findUserByEmail(String user_email) {
		return userRepository.findByEmail(user_email);
	}

	@Override
	public Usuario buscarUserAtivoByEmail(String email) {
		return userRepository.buscarUserAtivoByEmail(email);
	}

	@Override
	@Secured("hasRole('ROLE_ADMINISTRADOR')")
	@PreAuthorize("hasPermission('cadastro_usuario','leitura')")
	public Usuario findEmail(String username) {
		return userRepository.findEmail(username);
	}

	@Override
	public Usuario updateRegistroUsuario(Usuario usuario) {
		Usuario usuarioLogado = findById(usuario.getId());
		usuarioLogado.setLastLogin(usuario.getLastLogin());
		usuarioLogado.setDataVencimentoSenha(usuario.getDataVencimentoSenha());
		return userRepository.save(usuarioLogado);
	}

	@Override
	public Usuario findByIdAndRoleAndPermission(Integer id) {
		return userRepository.findByIdAndRoleAndPermission(id);
	}
	
	private boolean emailExiste(String email) {
		Usuario usuario = userRepository.findByEmail(email);
		return (Objects.isNull(usuario) ? false : true);
	}
	
	private String encodeUsuarioPassword(String password) {
		 return passwordEncoder.encode(password);
	}
	
}
