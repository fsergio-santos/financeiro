package com.financeiro.model.negocio;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Endereco {

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String estado;

	@NotBlank(message="Informe o nome da rua.")
	@NotNull(message = "O campo logradouro não pode ser nulo.")
	@Size(max = 100, min = 10, message="O nome da rua deve ter entre {min} e {max} caracteres.")
	@Column(name = "pessoa_logradouro", length = 100, nullable = false)
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@NotBlank(message="Informe o número da rua.")
	@NotNull(message = "O campo número não pode ser nulo.")
	@Size(max = 5, min = 1, message="O número da rua deve ter entre {min} e {max} caracteres.")
	@Column(name = "pessoa_numero", length = 5, nullable = false)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@NotBlank(message="Informe o complemento do endereço.")
	@NotNull(message = "O campo complemento não pode ser nulo.")
	@Size(max = 50, min = 4, message="O nome do complemento deve ter entre {min} e {max} caracteres.")
	@Column(name = "pessoa_complemento", length = 50, nullable = false)
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@NotBlank(message="Informe o bairro.")
	@NotNull(message = "O campo bairro não pode ser nulo.")
	@Size(max = 50, min = 4, message="O nome do bairro deve ter entre {min} e {max} caracteres.")
	@Column(name = "pessoa_bairro", length = 50, nullable = false)
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@NotBlank(message="Informe o cep.")
	@Size(max = 15, message="O cep deve ter entre {max} caracteres.")
	@NotNull(message = "O campo cep não pode ser nulo.")
	@Column(name = "pessoa_cep", length = 15, nullable = false)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@NotBlank(message="Informe a cidade.")
	@Size(max = 50, min = 5, message="O nome da cidade deve ter entre {min} e {max} caracteres.")
	@NotNull(message = "O campo cidade não pode ser nulo.")
	@Column(name = "pessoa_cidade", length = 50, nullable = false)
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@NotBlank(message="Informe o estado.")
	@Size(max = 2, min = 2, message="O nome do estado deve ter entre {min} e {max} caracteres.")
	@NotNull(message = "O campo estado não pode ser nulo.")
	@Column(name = "pessoa_estado", length = 2, nullable = false)
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
