package com.financeiro.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.security.ResetarSenhaToken;
import com.financeiro.model.security.Usuario;
import com.financeiro.repository.RegistrarUsuarioRepository;
import com.financeiro.repository.UserRepository;
import com.financeiro.repository.ResetarSenhaTokenRepository;
import com.financeiro.service.RegistrarUsuarioService;
import com.financeiro.service.exception.EmailUsuarioCadastradoException;
import com.financeiro.util.data.DataSistema;


@Service
@Transactional
public class RegistrarUsuarioServiceImpl implements RegistrarUsuarioService {

	public static final String TOKEN_INVALIDO = "invalido";
    public static final String TOKEN_EXPIRADO = "expirado";
    public static final String TOKEN_VALIDO = "valido";
    
	@Autowired
	private DataSistema dataSistema;
    
	@Autowired
	private RegistrarUsuarioRepository registrarUsuarioRepository;
	
	@Autowired
	private UserRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ResetarSenhaTokenRepository resetarSenhaTokenRepository;
	
	@Autowired
	private ResetarSenhaTokenRepository passwordResetTokenRepository;
	
	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		if (emailExiste(usuario.getEmail())) {
			throw new EmailUsuarioCadastradoException("Usuário já cadastrado!");
		}
		usuario.setAtivo(false);
		usuario.setPassword(encodeUsuarioPassword(usuario.getPassword()));
		return registrarUsuarioRepository.saveAndFlush(usuario);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario getUsuario(String verificationToken) {
		final ResetarSenhaToken token = resetarSenhaTokenRepository.findByToken(verificationToken);
		if (token != null) {
			return token.getUsuario();
		}
		return null;
	}

	@Override
	public void criaVerificacaoTokenParaUsuario(Usuario usuario, String token) {
        final ResetarSenhaToken myToken = new ResetarSenhaToken(token, usuario);
        resetarSenhaTokenRepository.save(myToken);
	}

	@Override
	@Transactional(readOnly=true)
	public ResetarSenhaToken pegarVerificacaoToken(String TokenVerification) {
    	 return resetarSenhaTokenRepository.findByToken(TokenVerification);
	}

	@Override
	public ResetarSenhaToken gerarNovaValidacaoParaToken(String token) {
		ResetarSenhaToken vToken = resetarSenhaTokenRepository.findByToken(token);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = resetarSenhaTokenRepository.save(vToken);
        return vToken;
	}

	@Override
	public void criarNovaSenhaComTokenParaUsuario(Usuario usuario, String token) {
		 final ResetarSenhaToken tokenUsuario = new ResetarSenhaToken(token, usuario);
	     passwordResetTokenRepository.save(tokenUsuario);
	}


	@Override
	@Transactional(readOnly=true)
	public ResetarSenhaToken pegarNovaSenhaComToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario pegarUsuarioComNovaSenhaToken(String token) {
		return passwordResetTokenRepository.findByToken(token).getUsuario();
	}

	@Override
	public void alterarUsuarioSenha(Usuario usuario, String password) {
		 usuario.setPassword(encodeUsuarioPassword(password));
		 usuario.setContraSenha(usuario.getPassword());
		 usuario.setDataVencimentoSenha(dataSistema.somaData(new Date()));
	     registrarUsuarioRepository.saveAndFlush(usuario);
	}

	@Override
	public boolean verificarSenhaAntigoUsuario(Usuario usuario, String password) {
		return passwordEncoder.matches(password, usuario.getPassword());
	}

	@Override
	public String verificarValidacaoDoToken(String token) {
		
		final ResetarSenhaToken verificationToken = resetarSenhaTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALIDO;
        }
        final Usuario usuario = verificationToken.getUsuario();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getDataExpiracao().getTime() - cal.getTime().getTime()) <= 0) {
        	resetarSenhaTokenRepository.delete(verificationToken);
            return TOKEN_EXPIRADO;
        }

        usuario.setAtivo(true);
        registrarUsuarioRepository.save(usuario);
        
        return TOKEN_VALIDO;
	}

	@Override
    public String validarSenhaAlteradaComToken(long id, String token) {
        final ResetarSenhaToken passToken = passwordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUsuario().getId() != id)) {
            return TOKEN_INVALIDO;
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getDataExpiracao()
            .getTime() - cal.getTime()
            .getTime()) <= 0) {
            return TOKEN_EXPIRADO;
        }

        final Usuario usuario = passToken.getUsuario();
        final Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, Arrays.asList(new SimpleGrantedAuthority("USUARIO")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
	
	private boolean emailExiste(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		return (Objects.isNull(usuario) ? false : true);
	}
	
	
	private String encodeUsuarioPassword(String password) {
		 return passwordEncoder.encode(password);
	}
	

}
