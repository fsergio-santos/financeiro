package com.financeiro.model.negocio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ForeignKey;

@Entity
@Table(name="TAB_TELEFONE")
public class Telefone implements Serializable{

	private static final long serialVersionUID = -248385711548681855L;
	
	private Integer id;
	private String  tipoTelefone;
	private String  numeroTelefone;
    private String  modeloTelefone;
	private Pessoa  pessoa;
	
	public Telefone() {
		super();
	}


	public Telefone(Integer id, String tipoTelefone, String numeroTelefone) {
		super();
		this.id = id;
		this.tipoTelefone = tipoTelefone;
		this.numeroTelefone = numeroTelefone;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="telefone_id")
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@NotBlank(message="O tipo do telefone deve ser informado.")
	@NotNull(message="O tipo do telefone deve ser informado.")
    @Column(name="telefone_tipo",length=20,nullable=false)
	public String getTipoTelefone() {
		return tipoTelefone;
	}


	public void setTipoTelefone(String tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	@NotBlank(message="O número do telefone deve ser informado.")
	@NotNull(message="O número do telefone deve ser informado.")
	@Size(max=14, message="O número do telefone deve conter {max} de caracteres")
    @Column(name="telefone_numero",length=20,nullable=false)
	public String getNumeroTelefone() {
		return numeroTelefone;
	}


	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}
	
	
	@NotBlank(message="O modelo do telefone deve ser informado.")
	@NotNull(message="O modelo do telefone deve ser informado.")
    @Column(name="telefone_modelo",length=20,nullable=false)
	public String getModeloTelefone() {
		return modeloTelefone;
	}


	public void setModeloTelefone(String modeloTelefone) {
		this.modeloTelefone = modeloTelefone;
	}


	@ManyToOne(targetEntity=Pessoa.class,fetch=FetchType.LAZY)
    @JoinColumn(nullable=false, name="pessoa_id",foreignKey=@ForeignKey(name="FK_TELEFONE_PESSOA"))
	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
		Telefone other = (Telefone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Telefone [id=" + id + ", tipoTelefone=" + tipoTelefone + ", numeroTelefone=" + numeroTelefone + "]";
	}
	
	

}
