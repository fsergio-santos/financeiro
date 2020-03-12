package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LoginAttempt.class)
public abstract class LoginAttempt_ {

	public static volatile SingularAttribute<LoginAttempt, Date> horaAcesso;
	public static volatile SingularAttribute<LoginAttempt, Integer> qtdFalhasAcesso;
	public static volatile SingularAttribute<LoginAttempt, Date> dataAcesso;
	public static volatile SingularAttribute<LoginAttempt, String> ip;
	public static volatile SingularAttribute<LoginAttempt, Long> id;
	public static volatile SingularAttribute<LoginAttempt, String> username;

	public static final String HORA_ACESSO = "horaAcesso";
	public static final String QTD_FALHAS_ACESSO = "qtdFalhasAcesso";
	public static final String DATA_ACESSO = "dataAcesso";
	public static final String IP = "ip";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

