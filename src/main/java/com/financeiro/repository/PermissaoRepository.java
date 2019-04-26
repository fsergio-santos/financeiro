package com.financeiro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.Permissao;
import com.financeiro.repository.query.PermissaoRepositoryQuery;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer>, PermissaoRepositoryQuery {

	public Optional<Permissao> findByNome(String nome);
}
