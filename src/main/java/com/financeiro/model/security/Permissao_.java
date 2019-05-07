package com.financeiro.model.security;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permissao.class)
public abstract class Permissao_ {

	public static volatile ListAttribute<Permissao, RolePermissao> rolePermissao;
	public static volatile SingularAttribute<Permissao, String> nome;
	public static volatile SingularAttribute<Permissao, Integer> id;

	public static final String ROLE_PERMISSAO = "rolePermissao";
	public static final String NOME = "nome";
	public static final String ID = "id";

}

