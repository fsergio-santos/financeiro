package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(QuantidadesAcessoLogin.class)
public abstract class QuantidadesAcessoLogin_ {

	public static volatile SingularAttribute<QuantidadesAcessoLogin, Date> horaAcesso;
	public static volatile SingularAttribute<QuantidadesAcessoLogin, Integer> qtdFalhasAcesso;
	public static volatile SingularAttribute<QuantidadesAcessoLogin, Date> dataAcesso;
	public static volatile SingularAttribute<QuantidadesAcessoLogin, String> ip;
	public static volatile SingularAttribute<QuantidadesAcessoLogin, Long> id;
	public static volatile SingularAttribute<QuantidadesAcessoLogin, String> username;

	public static final String HORA_ACESSO = "horaAcesso";
	public static final String QTD_FALHAS_ACESSO = "qtdFalhasAcesso";
	public static final String DATA_ACESSO = "dataAcesso";
	public static final String IP = "ip";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

