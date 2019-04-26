package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financeiro.model.security.UsuarioSenha;

public interface UsuarioSenhaRepository extends JpaRepository<UsuarioSenha, Integer> {

}
