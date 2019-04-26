package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TokenVerification.class)
public abstract class TokenVerification_ {

	public static volatile SingularAttribute<TokenVerification, Date> expiryDate;
	public static volatile SingularAttribute<TokenVerification, Usuario> usuario;
	public static volatile SingularAttribute<TokenVerification, Long> id;
	public static volatile SingularAttribute<TokenVerification, String> token;

}

