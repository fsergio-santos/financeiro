package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.repository.query.RolePermissaoRepositoryQuery;


@Repository
public interface RolePermissaoRepository extends JpaRepository<RolePermissao, RolePermissaoId>, RolePermissaoRepositoryQuery {

}
