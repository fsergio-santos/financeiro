package com.financeiro.model.security;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RolePermissao.class)
public abstract class RolePermissao_ {

	public static volatile SingularAttribute<RolePermissao, Boolean> right_read;
	public static volatile SingularAttribute<RolePermissao, Escopo> scopeId;
	public static volatile SingularAttribute<RolePermissao, Role> roleId;
	public static volatile SingularAttribute<RolePermissao, Boolean> right_write;
	public static volatile SingularAttribute<RolePermissao, RolePermissaoId> id;
	public static volatile SingularAttribute<RolePermissao, Permissao> permissaoId;
	public static volatile SingularAttribute<RolePermissao, LocalDate> dataCadastro;
	public static volatile SingularAttribute<RolePermissao, Boolean> right_delete;

}

