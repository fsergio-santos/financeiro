package com.financeiro.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "TAB_ROLE")
public class Role implements Serializable {
    
	private static final long serialVersionUID = 1325207739816858857L;

	private int id;
    private String nome;
    private List<Usuario> usuarios;
    private List<RolePermissao> rolePermissao;
    
    public Role() {
		super();
	}


	public Role(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
    
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Size(max = 50, min = 3, message="O nome da regra deve ter entre {min} e {max} caracteres.")
	@NotBlank(message="Informe o nome da pessoa.")
	@NotNull(message = "O campo nome não pode ser nulo.")
	@Column(name="role_name",length=50, nullable=false)
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	@ManyToMany(mappedBy="roles")
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@OneToMany(mappedBy="roleId",fetch=FetchType.LAZY)
	public List<RolePermissao> getRolePermissao() {
		return rolePermissao;
	}


	public void setRolePermissao(List<RolePermissao> permissoes) {
		this.rolePermissao = permissoes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + nome + "]";
	}
    
    
}
