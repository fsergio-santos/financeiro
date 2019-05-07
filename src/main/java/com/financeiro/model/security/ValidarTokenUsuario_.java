package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ValidarTokenUsuario.class)
public abstract class ValidarTokenUsuario_ {

	public static volatile SingularAttribute<ValidarTokenUsuario, Date> expiryDate;
	public static volatile SingularAttribute<ValidarTokenUsuario, Usuario> usuario;
	public static volatile SingularAttribute<ValidarTokenUsuario, Long> id;
	public static volatile SingularAttribute<ValidarTokenUsuario, String> token;

	public static final String EXPIRY_DATE = "expiryDate";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String TOKEN = "token";

}

