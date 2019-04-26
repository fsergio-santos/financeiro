package com.financeiro.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.financeiro.model.negocio.Pessoa_;
import com.financeiro.model.negocio.Telefone_;
import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.negocio.Telefone;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.repository.query.TelefoneRepositoryQuery;

public class TelefoneRepositoryImpl implements TelefoneRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Pessoa> listPessoaWithTelefonePagination(PessoaFiltro pessoaFiltro, Pageable pageable) {
		List<Pessoa> listaPessoa = new ArrayList<>();
		List<Predicate> lp = new ArrayList<>(); 
		TypedQuery<Pessoa> query = null;
		
		int totalRegistrosPorPaginas = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
		Root<Pessoa> rootFromPessoa = criteriaQuery.from(Pessoa.class);
        criteriaQuery = criteriaQuery.distinct(true);
		Join<Pessoa, Telefone> joinPessoaTelefone = rootFromPessoa.join(Pessoa_.telefones);
        criteriaQuery.select(rootFromPessoa).where(criteriaBuilder.equal(joinPessoaTelefone.get(Telefone_.pessoa), criteriaBuilder.parameter(Integer.class, "id")));
		Sort sort = pageable.getSort();
		if ( !sort.isUnsorted() ) {
			Sort.Order order = sort.iterator().next();
			String propriedade = order.getProperty();
			criteriaQuery.orderBy(order.isAscending()? criteriaBuilder.asc(rootFromPessoa.get(propriedade)): criteriaBuilder.desc(rootFromPessoa.get(propriedade)));
		}
	
		lp = filtros(pessoaFiltro, criteriaBuilder, rootFromPessoa);
		
		if ( lp.size() != -1 ) {
		    criteriaQuery.where(criteriaBuilder.and(lp.toArray(new Predicate[lp.size()])));
		    query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}
	    query.setFirstResult(primeiroRegistro);
	    query.setMaxResults(totalRegistrosPorPaginas);
	    listaPessoa = query.getResultList();
	    return new PageImpl<>(listaPessoa, pageable, totalRegistro(lp) );
	}
	
	
	private Long totalRegistro(List<Predicate> lp ) {
		
		TypedQuery<Long> query = null;

		CriteriaBuilder criteriaBuider = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery =  criteriaBuider.createQuery(Long.class);
		Root<Pessoa> rootFromPessoa = criteriaQuery.from(Pessoa.class);
		criteriaQuery = criteriaQuery.distinct(true);
		Join<Pessoa, Telefone> joinPessoaTelefone = rootFromPessoa.join(Pessoa_.telefones);
		criteriaQuery.select(
					    criteriaBuider.count(rootFromPessoa)
					  ).where(
						criteriaBuider.equal(joinPessoaTelefone.get(Telefone_.pessoa), 
						criteriaBuider.parameter(Integer.class, "id"))
					  );
		if ( lp.size() != -1 ) {
		    criteriaQuery.where(criteriaBuider.and(lp.toArray(new Predicate[lp.size()])));
		    query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}
		Long result = query.getSingleResult();
		return result;
	}

    private List<Predicate> filtros(PessoaFiltro pessoaFiltro, CriteriaBuilder cb, Root<Pessoa> fromPessoa) {
		List<Predicate> predicados = new ArrayList<>();
		if (!StringUtils.isEmpty(pessoaFiltro.getNome())){
			predicados.add( cb.like(cb.lower(fromPessoa.get(Pessoa_.nome)), "%"+pessoaFiltro.getNome()+"%"));
		} 
		if (!StringUtils.isEmpty(pessoaFiltro.getCpf())){
			predicados.add( cb.like(cb.lower(fromPessoa.get(Pessoa_.cnpjCpf)), "%"+pessoaFiltro.getCpf()+"%"));
		} 
		if (!StringUtils.isEmpty(pessoaFiltro.getCnpj())){
			predicados.add( cb.like(cb.lower(fromPessoa.get(Pessoa_.cnpjCpf)), "%"+pessoaFiltro.getCnpj()+"%"));
		} 
		
		return predicados;
	}

	@Override
	public Pessoa findByTelefoneNomePessoa(PessoaFiltro pessoaFiltro) {
		String nome = pessoaFiltro.getNome();
		TypedQuery<Pessoa> query = entityManager
									.createQuery("SELECT p FROM Pessoa p "
	             								+"JOIN p.telefones WHERE "
	             								+"p.nome LIKE :nome", Pessoa.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getSingleResult();
	}

	@Override
	public Pessoa findByTelefoneIdPessoa(Integer id) {
		TypedQuery<Pessoa> query = entityManager
				            .createQuery("SELECT p FROM Pessoa p "
							+"JOIN p.telefones WHERE "
							+"p.id = :id", Pessoa.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
