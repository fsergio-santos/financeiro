package com.financeiro.service;


import com.financeiro.model.security.ResetPasswordToken;
import com.financeiro.model.security.TokenVerification;
import com.financeiro.model.security.Usuario;


public interface RegistrarUsuarioService {

	Usuario registrarUsuario(Usuario usuario);
	
	Usuario getUsuario(String verificationToken);
	
    void criaVerificationTokenForUsuario(Usuario usuario, String token);
    
	TokenVerification getVerificationToken(String VerificationToken);

    TokenVerification generateNewVerificationToken(String token);

    void createPasswordResetTokenForUsuario(Usuario usuario, String token);

	ResetPasswordToken getPasswordResetToken(String token);

	Usuario getUsuarioByPasswordResetToken(String token);

	void changeUsuarioPassword(Usuario usuario, String password);

	boolean checkIfValidOldPassword(Usuario usuario, String password); 

	String validateVerificationToken(String token); 

	String validatePasswordResetToken(long id, String token);

}