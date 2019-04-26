
package com.financeiro.model.security;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="TAB_ROLE_PERMISSAO")
public class RolePermissao implements Serializable {
		
	private static final long serialVersionUID = 5730181902200118340L;

	private RolePermissaoId id;
	
	private Permissao permissaoId;
	private Role roleId;
	private Escopo scopeId;
	
	private LocalDate dataCadastro;
	
	private boolean right_read   = false;
	private boolean right_write  = false;
	private boolean right_delete = false;
		
	public RolePermissao() {
		super();
	}

	public RolePermissao(RolePermissaoId id, 
			             Permissao permissaoId, 
			             Role roleId,  
			             Escopo scopeId,
			             boolean right_read, 
			             boolean right_write,
			             boolean right_delete) {
		super();
		this.id = id;
		this.permissaoId = permissaoId;
		this.roleId = roleId;
		this.scopeId = scopeId;
		this.right_read = right_read;
		this.right_write = right_write;
		this.right_delete = right_delete;

	}
	
    @EmbeddedId	
	public RolePermissaoId getId() {
		return id;
	}
	public void setId(RolePermissaoId id) {
		this.id = id;
	}
	
    @ManyToOne
	@JoinColumn(name="permissao_id",nullable=false,insertable=false, updatable=false)
	public Permissao getPermissaoId() {
		return permissaoId;
	}
	public void setPermissaoId(Permissao permissaoId) {
		this.permissaoId = permissaoId;
	}
	
    @ManyToOne
	@JoinColumn(name="role_id",nullable=false,insertable=false, updatable=false)
	public Role getRoleId() {
		return roleId;
	}
	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}
	
	@ManyToOne
	@JoinColumn(name="escopo_id",nullable=false,insertable=false, updatable=false)
    public Escopo getScopeId() {
		return scopeId;
	}

	public void setScopeId(Escopo scopeId) {
		this.scopeId = scopeId;
	}

	@NotNull(message="A data de cadastro deve ser informada.")
	@DateTimeFormat(iso = ISO.DATE, pattern = "")
	@Column(name="data_cadastro", nullable=false, columnDefinition = "DATE")
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Column(nullable=false)
	public boolean isRight_read() {
		return right_read;
	}
	public void setRight_read(boolean right_read) {
		this.right_read = right_read;
	}
	
	@Column(nullable=false)
	public boolean isRight_write() {
		return right_write;
	}
	public void setRight_write(boolean right_write) {
		this.right_write = right_write;
	}
	
	@Column(nullable=false)
	public boolean isRight_delete() {
		return right_delete;
	}
	public void setRight_delete(boolean right_delete) {
		this.right_delete = right_delete;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RolePermissao other = (RolePermissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RolePermissao [id=" + id + ", permissaoId=" + permissaoId.getId() + ", roleId=" + roleId.getId() + ", scopeId="
				+ scopeId + ", dataCadastro=" + dataCadastro + ", right_read=" + right_read + ", right_write="
				+ right_write + ", right_delete=" + right_delete + "]";
	}
	
	
	

}
