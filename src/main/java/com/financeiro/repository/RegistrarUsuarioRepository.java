package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.Usuario;

@Repository
public interface RegistrarUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
