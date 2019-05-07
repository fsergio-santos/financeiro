package com.financeiro.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TAB_PERMISSAO",indexes={@Index(name="PERMISSAO_ID",columnList="PERMISSAO_ID"),@Index(name="PERMISSAO_NOME",columnList="PERMISSAO_NOME")},schema="FINANCEIRO")
public class Permissao  implements Serializable{
	
	private static final long serialVersionUID = 2793317666807558501L;
	private Integer id;
	private String nome;
	private List<RolePermissao> rolePermissao;
			
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="permissao_id")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@NotNull( message = "O nome da Permissão é obrigatório.")
	@NotBlank(message="O nome da Permissão é obrigatório.")
	@Column(name="permissao_nome",length=50, nullable=false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
    
	@OneToMany(mappedBy="permissaoId",fetch=FetchType.EAGER)
	public List<RolePermissao> getRolePermissao() {
		return rolePermissao;
	}

	public void setRolePermissao(List<RolePermissao> rolePermissao) {
		this.rolePermissao = rolePermissao;
	}
	
	
   
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
	

	
}
