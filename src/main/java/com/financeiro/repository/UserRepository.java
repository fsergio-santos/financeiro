package com.financeiro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.Usuario;
import com.financeiro.repository.query.UserRepositoryQuery;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer>, UserRepositoryQuery {

}