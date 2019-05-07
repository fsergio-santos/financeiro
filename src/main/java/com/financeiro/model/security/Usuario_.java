package com.financeiro.model.security;

import java.time.LocalDate;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, LocalDate> lastLogin;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, Boolean> ativo;
	public static volatile SingularAttribute<Usuario, String> foto;
	public static volatile SingularAttribute<Usuario, Date> dataVencimentoSenha;
	public static volatile ListAttribute<Usuario, Role> roles;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile ListAttribute<Usuario, UsuarioSenha> usuarioSenhas;
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> contentType;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> username;

	public static final String LAST_LOGIN = "lastLogin";
	public static final String PASSWORD = "password";
	public static final String ATIVO = "ativo";
	public static final String FOTO = "foto";
	public static final String DATA_VENCIMENTO_SENHA = "dataVencimentoSenha";
	public static final String ROLES = "roles";
	public static final String NOME = "nome";
	public static final String USUARIO_SENHAS = "usuarioSenhas";
	public static final String ID = "id";
	public static final String CONTENT_TYPE = "contentType";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

