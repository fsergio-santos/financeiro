package com.financeiro.model.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TAB_RESET_TOKEN")
public class ResetarSenhaToken implements Serializable {

	private static final int EXPIRAR = 60 * 24;

    private Long id;
    private String token;
    private Usuario usuario;
    private Date dataExpiracao;

    public ResetarSenhaToken() {
        super();
    }

    public ResetarSenhaToken(final String token) {
        this.token = token;
        this.dataExpiracao = calcularDataExpiracao(EXPIRAR);
    }

    public ResetarSenhaToken(final String token, final Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.dataExpiracao = calcularDataExpiracao(EXPIRAR);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(final Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    private Date calcularDataExpiracao(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.dataExpiracao = calcularDataExpiracao(EXPIRAR);
    }

    //

    

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expirar").append(dataExpiracao).append("]");
        return builder.toString();
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
		ResetarSenhaToken other = (ResetarSenhaToken) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
