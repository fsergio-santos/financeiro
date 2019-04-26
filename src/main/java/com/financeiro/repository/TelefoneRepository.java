package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.negocio.Telefone;
import com.financeiro.repository.query.TelefoneRepositoryQuery;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer>, TelefoneRepositoryQuery {
	

}
