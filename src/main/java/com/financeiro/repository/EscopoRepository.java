package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financeiro.model.security.Escopo;

public interface EscopoRepository extends JpaRepository<Escopo, Integer> {

}
