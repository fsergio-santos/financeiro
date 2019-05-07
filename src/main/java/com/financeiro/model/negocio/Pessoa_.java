package com.financeiro.model.negocio;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile SingularAttribute<Pessoa, Boolean> ativo;
	public static volatile SingularAttribute<Pessoa, String> tipoPessoa;
	public static volatile SingularAttribute<Pessoa, String> foto;
	public static volatile SingularAttribute<Pessoa, BigDecimal> salario;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, Integer> id;
	public static volatile SingularAttribute<Pessoa, Date> dataNascimento;
	public static volatile SingularAttribute<Pessoa, String> cnpjCpf;
	public static volatile SingularAttribute<Pessoa, String> contentType;
	public static volatile ListAttribute<Pessoa, Telefone> telefones;

	public static final String ATIVO = "ativo";
	public static final String TIPO_PESSOA = "tipoPessoa";
	public static final String FOTO = "foto";
	public static final String SALARIO = "salario";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String DATA_NASCIMENTO = "dataNascimento";
	public static final String CNPJ_CPF = "cnpjCpf";
	public static final String CONTENT_TYPE = "contentType";
	public static final String TELEFONES = "telefones";

}

