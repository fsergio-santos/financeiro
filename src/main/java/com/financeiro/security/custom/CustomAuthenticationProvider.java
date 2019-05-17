package com.financeiro.security.custom;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.financeiro.model.security.QuantidadesAcessoLogin;
import com.financeiro.security.UsuarioSistema;
import com.financeiro.service.QuantidadeAcessoLoginService;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
	
	private static int MAX_LOGIN_ATTEMPT_ALLOWED = 5;
	
    private static final String BAD_CRED_MSG_KEY = "AbstractUserDetailsAuthenticationProvider.badCredentials";
    private static final String BAD_CRED_DEFAULT_MSG = "Bad credentials";
    private static final String ACCOUNT_LOCKED_MSG_KEY = "AbstractUserDetailsAuthenticationProvider.locked";
    private static final String ACCOUNT_LOCKED_DEFAULT_MSG = "User account is locked";
    private static final String TOO_MANY_ATTEMPTS_MSG_FORMAT = "Too many attempts [%d]";
    private static final String INVALID_CAPTCHA_MSG_FORMAT = "%s - Recaptcha Invalid";
      
    @Autowired
    private UserDetailsService usuarioDetailsService;
    
    @Autowired
    private QuantidadeAcessoLoginService loginAttemptService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
    	UsuarioSistema user =  (UsuarioSistema) usuarioDetailsService.loadUserByUsername(auth.getName());
        try {
        	final Authentication result = super.authenticate(auth);
            return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), user.getAuthorities());
        } catch (BadCredentialsException ex) {
            bloquearConta(auth);
        	throw new BadCredentialsException("Usuário ou Senha inválidos!", ex);
        }
        
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    
    private void bloquearConta(Authentication auth) {
    	
        Optional<QuantidadesAcessoLogin> quantidadeAcessoLogin = loginAttemptService.updateQtdFalhasAcesso(auth.getName());
        if ( quantidadeAcessoLogin.isPresent()) {
            if ( quantidadeAcessoLogin.get().getQtdFalhasAcesso() == MAX_LOGIN_ATTEMPT_ALLOWED) {
                throw new LockedException(messages.getMessage(ACCOUNT_LOCKED_MSG_KEY, ACCOUNT_LOCKED_DEFAULT_MSG));
            } else if ( quantidadeAcessoLogin.get().getQtdFalhasAcesso() > 2) {
                throw new BadCredentialsException(String.format(TOO_MANY_ATTEMPTS_MSG_FORMAT,
                        MAX_LOGIN_ATTEMPT_ALLOWED -  quantidadeAcessoLogin.get().getQtdFalhasAcesso()));
            }
        }
    }
    
    
}
