package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.model.negocio.Lancamento;
import com.financeiro.repository.query.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Integer>, LancamentoRepositoryQuery{

}
