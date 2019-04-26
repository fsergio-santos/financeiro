package com.financeiro.web.registrar;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.Usuario;
import com.financeiro.service.EmailService;
import com.financeiro.service.RegistrarUsuarioService;



@Component
public class RegistrarUsuarioListerner implements ApplicationListener<RegistrarUsuario> {
    
	@Autowired
	private RegistrarUsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void onApplicationEvent(RegistrarUsuario registrarUsuario) {
		confirmRegistration(registrarUsuario);

	}
	
	private void confirmRegistration(final RegistrarUsuario event) {
        final Usuario usuario = event.getUsuario();
        final String token = UUID.randomUUID().toString();
        usuarioService.criaVerificationTokenForUsuario(usuario, token);
        constructEmailMessage(event, usuario, token);
    }
	
	private final void constructEmailMessage(final RegistrarUsuario event, final Usuario usuario, final String token) {
        final String recipientAddress = usuario.getEmail();
        final String subject = "Confirmar Registro";
        final String confirmationUrl = event.getUrl() + "/registro/registrationConfirm.html?token=" + token;
        												 
        final String message = "Confirme o seu registro no sistema "+event.getLocale()+" "+"sua senha de acesso = "+usuario.getPassword();
        final String text = message + " \r\n" + confirmationUrl;
        emailService.sendSimpleMessage(recipientAddress, subject, text, "fsergio.santos@gmail.com");
    }
	 
}
