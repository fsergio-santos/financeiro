package com.financeiro.model.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.financeiro.model.security.Escopo;
import com.financeiro.model.security.Permissao;
import com.financeiro.model.security.Role;
import com.financeiro.model.security.RolePermissaoId;

public class ListaRolePermissao {
     
	private RolePermissaoId id;
	private Role role;
	private List<Permissao> listaPermissoes = new ArrayList<>();
	private Escopo scope;
	private Date dataCadastro;
	
	public ListaRolePermissao() {
	}

	public ListaRolePermissao(Role role, List<Permissao> listaPermissoes, Escopo scope, Date dataCadastro) {
		super();
		this.role = role;
		this.listaPermissoes = listaPermissoes;
		this.scope = scope;
		this.dataCadastro = dataCadastro;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Permissao> getListaPermissoes() {
		return listaPermissoes;
	}

	public void setListaPermissoes(List<Permissao> listaPermissoes) {
		this.listaPermissoes = listaPermissoes;
	}

	public Escopo getScope() {
		return scope;
	}

	public void setScope(Escopo scope) {
		this.scope = scope;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public RolePermissaoId getId() {
		return id;
	}

	public void setId(RolePermissaoId id) {
		this.id = id;
	}
	
	
}
