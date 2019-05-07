package com.financeiro.model.dto;

public class RolePermissaoId {

	private int permissao_id;
	private int role_id;
	private int escopo_id;
	
	public RolePermissaoId() {
		
	}
	
	public int getPermissao_id() {
		return permissao_id;
	}
	public void setPermissao_id(int permissao_id) {
		this.permissao_id = permissao_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getEscopo_id() {
		return escopo_id;
	}
	public void setEscopo_id(int escopo_id) {
		this.escopo_id = escopo_id;
	}
	
	
}
