package com.financeiro.model.security;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsuarioSenha.class)
public abstract class UsuarioSenha_ {

	public static volatile SingularAttribute<UsuarioSenha, String> senha;
	public static volatile SingularAttribute<UsuarioSenha, Usuario> usuario;
	public static volatile SingularAttribute<UsuarioSenha, Integer> id;
	public static volatile SingularAttribute<UsuarioSenha, Date> dataUpdate;

}

