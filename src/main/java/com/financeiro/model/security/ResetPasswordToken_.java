package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ResetPasswordToken.class)
public abstract class ResetPasswordToken_ {

	public static volatile SingularAttribute<ResetPasswordToken, Date> expiryDate;
	public static volatile SingularAttribute<ResetPasswordToken, Usuario> usuario;
	public static volatile SingularAttribute<ResetPasswordToken, Long> id;
	public static volatile SingularAttribute<ResetPasswordToken, String> token;

}

