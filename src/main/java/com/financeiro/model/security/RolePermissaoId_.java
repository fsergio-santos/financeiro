package com.financeiro.model.security;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RolePermissaoId.class)
public abstract class RolePermissaoId_ {

	public static volatile SingularAttribute<RolePermissaoId, Integer> role_id;
	public static volatile SingularAttribute<RolePermissaoId, Integer> escopo_id;
	public static volatile SingularAttribute<RolePermissaoId, Integer> permissao_id;

	public static final String ROLE_ID = "role_id";
	public static final String ESCOPO_ID = "escopo_id";
	public static final String PERMISSAO_ID = "permissao_id";

}

