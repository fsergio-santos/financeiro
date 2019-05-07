package com.financeiro.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RolePermissaoId implements Serializable {
	
	private static final long serialVersionUID = -5838205134618363082L;

	private int permissao_id;
	private int role_id;
	private int escopo_id;
	
	
	public RolePermissaoId() {
		super();
	}

	public RolePermissaoId(int permissao_id, int role_id, int escopo_id) {
		super();
		this.permissao_id = permissao_id;
		this.role_id = role_id;
        this.escopo_id = escopo_id;
	}
	
	@Column(name="permissao_id",insertable=false, updatable=false, nullable=false)
	public int getPermissao_id() {
		return permissao_id;
	}
	public void setPermissao_id(int permissao_id) {
		this.permissao_id = permissao_id;
	}
	
	@Column(name="role_id",insertable=false, updatable=false, nullable=false)
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	@Column(name="escopo_id",insertable=false, updatable=false, nullable=false)	
	public int getEscopo_id() {
		return escopo_id;
	}

	public void setEscopo_id(int escopo_id) {
		this.escopo_id = escopo_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + permissao_id;
		result = prime * result + role_id;
		result = prime * result + escopo_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePermissaoId other = (RolePermissaoId) obj;
		if (permissao_id != other.permissao_id)
			return false;
		if (role_id != other.role_id)
			return false;
		if (escopo_id != other.escopo_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RolePermissaoId [permissao_id=" + permissao_id + ", role_id=" + role_id + ", escopo_id=" + escopo_id
				+ "]";
	}

	

}
