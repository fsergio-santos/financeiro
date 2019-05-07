package com.financeiro.model.negocio;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Telefone.class)
public abstract class Telefone_ {

	public static volatile SingularAttribute<Telefone, String> tipoTelefone;
	public static volatile SingularAttribute<Telefone, Pessoa> pessoa;
	public static volatile SingularAttribute<Telefone, String> modeloTelefone;
	public static volatile SingularAttribute<Telefone, String> numeroTelefone;
	public static volatile SingularAttribute<Telefone, Integer> id;

	public static final String TIPO_TELEFONE = "tipoTelefone";
	public static final String PESSOA = "pessoa";
	public static final String MODELO_TELEFONE = "modeloTelefone";
	public static final String NUMERO_TELEFONE = "numeroTelefone";
	public static final String ID = "id";

}

