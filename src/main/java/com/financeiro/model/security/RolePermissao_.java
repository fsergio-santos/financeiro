package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RolePermissao.class)
public abstract class RolePermissao_ {

	public static volatile SingularAttribute<RolePermissao, Escopo> scopeId;
	public static volatile SingularAttribute<RolePermissao, Role> roleId;
	public static volatile SingularAttribute<RolePermissao, RolePermissaoId> id;
	public static volatile SingularAttribute<RolePermissao, Permissao> permissaoId;
	public static volatile SingularAttribute<RolePermissao, Date> dataCadastro;

	public static final String SCOPE_ID = "scopeId";
	public static final String ROLE_ID = "roleId";
	public static final String ID = "id";
	public static final String PERMISSAO_ID = "permissaoId";
	public static final String DATA_CADASTRO = "dataCadastro";

}

