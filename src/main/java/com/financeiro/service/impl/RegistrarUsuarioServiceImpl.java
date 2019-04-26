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

import com.financeiro.model.security.ResetPasswordToken;
import com.financeiro.model.security.TokenVerification;
import com.financeiro.model.security.Usuario;
import com.financeiro.repository.PasswordResetTokenRepository;
import com.financeiro.repository.RegistrarUsuarioRepository;
import com.financeiro.repository.UserRepository;
import com.financeiro.repository.VerificationTokenRepository;
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
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
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
		final TokenVerification token = verificationTokenRepository.findByToken(verificationToken);
		if (token != null) {
			return token.getUsuario();
		}
		return null;
	}

	@Override
	public void criaVerificationTokenForUsuario(Usuario usuario, String token) {
        final TokenVerification myToken = new TokenVerification(token, usuario);
        verificationTokenRepository.save(myToken);
	}

	@Override
	@Transactional(readOnly=true)
	public TokenVerification getVerificationToken(String TokenVerification) {
    	 return verificationTokenRepository.findByToken(TokenVerification);
	}

	@Override
	public TokenVerification generateNewVerificationToken(String token) {
        TokenVerification vToken = verificationTokenRepository.findByToken(token);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = verificationTokenRepository.save(vToken);
        return vToken;
	}

	@Override
	public void createPasswordResetTokenForUsuario(Usuario usuario, String token) {
		 final ResetPasswordToken myToken = new ResetPasswordToken(token, usuario);
	     passwordResetTokenRepository.save(myToken);
	}


	@Override
	@Transactional(readOnly=true)
	public ResetPasswordToken getPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario getUsuarioByPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token).getUsuario();
	}

	@Override
	public void changeUsuarioPassword(Usuario usuario, String password) {
		 usuario.setPassword(encodeUsuarioPassword(password));
		 usuario.setContraSenha(usuario.getPassword());
		 usuario.setDataVencimentoSenha(dataSistema.somaData(new Date()));
	     registrarUsuarioRepository.saveAndFlush(usuario);
	}

	@Override
	public boolean checkIfValidOldPassword(Usuario usuario, String password) {
		return passwordEncoder.matches(password, usuario.getPassword());
	}

	@Override
	public String validateVerificationToken(String token) {
		
		final TokenVerification verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALIDO;
        }
        final Usuario usuario = verificationToken.getUsuario();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	verificationTokenRepository.delete(verificationToken);
            return TOKEN_EXPIRADO;
        }

        usuario.setAtivo(true);
        registrarUsuarioRepository.save(usuario);
        
        return TOKEN_VALIDO;
	}

	@Override
    public String validatePasswordResetToken(long id, String token) {
        final ResetPasswordToken passToken = passwordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUsuario().getId() != id)) {
            return TOKEN_INVALIDO;
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
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
