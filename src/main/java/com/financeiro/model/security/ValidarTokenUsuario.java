package com.financeiro.model.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="TAB_TOKEN")
public class ValidarTokenUsuario implements Serializable{

	private static final long serialVersionUID = 7373767026910156087L;

	private static final int EXPIRATION = 60 * 24;
    private Long id;
    private String token;
    private Usuario usuario;
    private Date expiryDate;

    public ValidarTokenUsuario() {
    }

    public ValidarTokenUsuario(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public ValidarTokenUsuario(final String token, final Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
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
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFIQUE_USUARIO"))
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    //

   
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
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
		ValidarTokenUsuario other = (ValidarTokenUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



}
