package com.financeiro.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="TAB_USUARIO_SENHA")
public class UsuarioSenha implements Serializable{

	private static final long serialVersionUID = -9145833766251101793L;
	private Integer Id;
	private Usuario usuario;
	private String  senha;
	private Date    dataUpdate;
	
	public UsuarioSenha() {
	}

	public UsuarioSenha(Integer id, Usuario usuario, String senha, Date dataUpdate) {
		super();
		Id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.dataUpdate = dataUpdate;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="usuario_senha_id")
	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}

	@Column(name="usuario_senha_senha",nullable=false,length=100)
	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="usuario_senha_data_update",nullable=false,columnDefinition="DATE")
	public Date getDataUpdate() {
		return dataUpdate;
	}


	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	@ManyToOne(targetEntity=Usuario.class,fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id",nullable=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		UsuarioSenha other = (UsuarioSenha) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioSenha [Id=" + Id + ", usuario=" + usuario + ", senha=" + senha + ", dataUpdate=" + dataUpdate
				+ "]";
	}
	
	
	
	

}
