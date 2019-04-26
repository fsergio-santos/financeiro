package com.financeiro.model.security;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.financeiro.util.ValidarEmail;
import com.financeiro.util.ValidarSenha;

@DynamicUpdate(true)
@Entity
@Table(name = "TAB_USER")
//@ValidarSenha(senha="password",contraSenha="contraSenha",message="contra Senha não confere...")
public class Usuario implements UserDetails, Serializable{

	private static final long serialVersionUID = -2113184215530751581L;

	private int id;
    private String email;
    private String password;
    private String contraSenha;
    private String username;
    private LocalDate lastLogin;
    private Date dataVencimentoSenha;
    private boolean ativo = false;
    private List<Role> roles;
    private List<UsuarioSenha> usuarioSenhas;
        
    public Usuario() {
	}
    
	public Usuario(int id, String email, String password, String name, boolean ativo, Set<Role> roles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = name;
		this.ativo = ativo;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ValidarEmail
    @Column(name = "user_email",length=100, nullable=false,unique=true)
    @Email(message = "Insira um e-mail válido.")
    @NotEmpty(message = "Insira um e-mail válido.")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    @Column(name = "user_password",length=100, nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	   
    @Column(name = "user_active", nullable=false)
	public boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	//@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "user_last_login",nullable=true)
	public LocalDate getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(LocalDate lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Transient
	public String getContraSenha() {
		return contraSenha;
	}

	public void setContraSenha(String contraSenha) {
		this.contraSenha = contraSenha;
	}

	@Size(min = 1, message = "Selecione pelo menos um grupo")
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "TAB_USER_ROLE", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@OneToMany(mappedBy="usuario")
	public List<UsuarioSenha> getUsuarioSenhas() {
		return usuarioSenhas;
	}

	public void setUsuarioSenhas(List<UsuarioSenha> usuarioSenhas) {
		this.usuarioSenhas = usuarioSenhas;
	}
	
    @Temporal(TemporalType.DATE)
	@Column(name = "user_data_vencimento_senha",nullable=true)
	public Date getDataVencimentoSenha() {
		return dataVencimentoSenha;
	}

	public void setDataVencimentoSenha(Date dataVencimentoSenha) {
		this.dataVencimentoSenha = dataVencimentoSenha;
	}

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	for (Role role : this.getRoles()) {
    		authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getNome().toUpperCase()));
    	}
        return authorities;
	}
	
	@Override
	@Column(name = "user_name",length=100, nullable=false)
    @NotEmpty(message = "Insira um nome válido.")
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return ativo;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return ativo;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return ativo;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return ativo;
	}
	
	@PreUpdate
	private void preUpdate() {
		this.contraSenha = password;
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
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", name=" + username + ", active=" + ativo +  "]";
	}

}
