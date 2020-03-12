package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.LoginAttempt;
import com.financeiro.model.security.Usuario;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

	LoginAttempt findByUsername(String username);
	
}
