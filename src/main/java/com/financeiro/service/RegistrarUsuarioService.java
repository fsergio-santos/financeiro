package com.financeiro.service;


import com.financeiro.model.security.ResetarSenhaToken;
import com.financeiro.model.security.Usuario;


public interface RegistrarUsuarioService {

	Usuario registrarUsuario(Usuario usuario);
	
	Usuario getUsuario(String verificationToken);
	
    void criaVerificacaoTokenParaUsuario(Usuario usuario, String token);
    
    ResetarSenhaToken pegarVerificacaoToken(String VerificationToken);

    ResetarSenhaToken gerarNovaValidacaoParaToken(String token);

    void criarNovaSenhaComTokenParaUsuario(Usuario usuario, String token);

	ResetarSenhaToken pegarNovaSenhaComToken(String token);

	Usuario pegarUsuarioComNovaSenhaToken(String token);

	void alterarUsuarioSenha(Usuario usuario, String password);

	boolean verificarSenhaAntigoUsuario(Usuario usuario, String password); 

	String verificarValidacaoDoToken(String token); 

	String validarSenhaAlteradaComToken(long id, String token);

}