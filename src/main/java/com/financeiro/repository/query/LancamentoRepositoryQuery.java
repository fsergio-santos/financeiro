package com.financeiro.repository.query;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.model.negocio.Lancamento;
import com.financeiro.repository.filtros.LancamentoFiltro;


public interface LancamentoRepositoryQuery {

   Page<Lancamento> filtrar(LancamentoFiltro lancamentoFilter, Pageable pageable);

}
