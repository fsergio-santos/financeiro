package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.QuantidadesAcessoLogin;
import com.financeiro.model.security.Usuario;

@Repository
public interface QuantidadeAcessoLoginRepository extends JpaRepository<QuantidadesAcessoLogin, Long> {

	QuantidadesAcessoLogin findByUsername(String username);
	
}
