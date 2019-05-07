package com.financeiro.util.email;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.ValidarTokenUsuario;
import com.financeiro.model.security.Usuario;
import com.financeiro.service.EmailService;

@Component
public class CriarMensagemEmail {
	
	@Autowired
	private EmailService emailService;
	
	public void constructResendVerificationTokenEmail(String contextPath, final ValidarTokenUsuario newToken, final Usuario usuario) {
        final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        final String message = "Use o link em seu e-mail para realizar um novo registro ";
        constructEmail("Fazer um novo registro", message + " \r\n" + confirmationUrl, usuario);
    }
	
	public void constructResetTokenEmail(final String contextPath, final String token, final Usuario usuario) {
        final String url = contextPath + "/registro/changePassword?id=" + usuario.getId() + "&token=" + token;
        final String message = " Enviamos mensagem no seu e-mail para você trocar sua senha atravês deste link"+" \r\n" + url;
        constructEmail("Modifique sua senha", message  , usuario);
    }
    
    public void constructEmail(String subject, String body, Usuario usuario) {
        emailService.sendSimpleMessage(usuario.getEmail(), subject, body, "fsergio.santos@gmail.com");
    }
    
    public String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
