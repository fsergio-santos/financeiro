package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.Role;
import com.financeiro.repository.query.RoleRepositoryQuery;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, RoleRepositoryQuery {

}
