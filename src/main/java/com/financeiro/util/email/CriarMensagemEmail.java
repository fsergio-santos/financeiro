package com.financeiro.util.email;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.ResetarSenhaToken;
import com.financeiro.model.security.Usuario;
import com.financeiro.service.EmailService;

@Component
public class CriarMensagemEmail {
	
	@Autowired
	private EmailService emailService;
	
	public void ReenviarEmailTokenConfirmado(String contextPath, final ResetarSenhaToken newToken, final Usuario usuario) {
        final String confirmarUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        final String mensagem = "Use o link em seu e-mail para realizar um novo registro ";
        criarEmail("Fazer um novo registro", mensagem + " \r\n" + confirmarUrl, usuario);
    }
	
	public void criarRenvioTokenPorEmail(final String contextPath, final String token, final Usuario usuario) {
        final String url = contextPath + "/registro/changePassword?id=" + usuario.getId() + "&token=" + token;
        final String mensagem = " Enviamos mensagem no seu e-mail para você trocar sua senha atravês deste link"+" \r\n" + url;
        criarEmail("Modifique sua senha", mensagem  , usuario);
    }
    
    public void criarEmail(String subject, String body, Usuario usuario) {
        emailService.sendSimpleMessage(usuario.getEmail(), subject, body, "fsergio.santos@gmail.com");
    }
    
    public String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
