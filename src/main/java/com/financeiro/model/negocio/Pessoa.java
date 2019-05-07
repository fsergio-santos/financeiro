package com.financeiro.model.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.util.StringUtils;

@Entity
@Table(name="TAB_PESSOA")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 57873505408611836L;
	
	private Integer id;
	private String nome;
	private Endereco endereco;
	private boolean ativo = true;
	private String cnpjCpf;
	private BigDecimal salario;
	private Date dataNascimento;
	private String tipoPessoa;
	private List<Telefone> telefones;
	
	private String foto;
	private String contentType;
		
	public Pessoa() {
		super();
		this.telefones = new ArrayList<Telefone>();
	}
	
	public Pessoa(Integer id, String nome, Endereco endereco, boolean ativo, List<Telefone> telefones) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.ativo = ativo;
		this.telefones = telefones;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pessoa_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Size(max = 255, min = 3, message="O nome da pessoa deve ter entre {min} e {max} caracteres.")
	@NotBlank(message="Informe o nome da pessoa.")
	@NotNull(message = "O campo nome não pode ser nulo.")
	@Column(name="pessoa_nome",length=100, nullable=false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Valid
    @Embedded
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	@NotNull
	@Column(name="pessoa_ativo", nullable=false)
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Size(min=1,message="Escolha um tipo de Pessoa.")
	@NotNull(message="O CNPJ/CPF deve ser informado.")
	@Column(name="pessoa_cnpj_cpf", nullable=false, length=25, unique=true)
	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	@Column(name="pessoa_salario", nullable = true, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	@NotNull(message="A data de nascimento deve ser informada.")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="pessoa_data_nascimento", nullable=false, columnDefinition = "DATE")
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	@Column(name = "pessoa_tipo",length=20,nullable=false )
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@Column(name="pessoa_foto", length=100, nullable=true)
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	@Column(name = "content_type", length=100, nullable=true)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	@OneToMany(mappedBy="pessoa",fetch=FetchType.LAZY)
	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Transient
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "users.png";
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", ativo=" + ativo +  "]";
	}
	
	
	

}
