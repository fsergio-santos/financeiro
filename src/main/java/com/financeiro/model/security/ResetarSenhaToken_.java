package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ResetarSenhaToken.class)
public abstract class ResetarSenhaToken_ {

	public static volatile SingularAttribute<ResetarSenhaToken, Date> dataExpiracao;
	public static volatile SingularAttribute<ResetarSenhaToken, Usuario> usuario;
	public static volatile SingularAttribute<ResetarSenhaToken, Long> id;
	public static volatile SingularAttribute<ResetarSenhaToken, String> token;

	public static final String DATA_EXPIRACAO = "dataExpiracao";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String TOKEN = "token";

}

