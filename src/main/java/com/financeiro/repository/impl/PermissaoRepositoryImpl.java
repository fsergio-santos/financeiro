package com.financeiro.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.financeiro.model.security.Permissao;
import com.financeiro.model.security.Permissao_;
import com.financeiro.repository.filtros.PermissaoFiltro;
import com.financeiro.repository.query.PermissaoRepositoryQuery;

public class PermissaoRepositoryImpl implements PermissaoRepositoryQuery {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Permissao> listPermissaoWithPagination(PermissaoFiltro permissaoFiltro, Pageable pageable) {
		List<Permissao> listaPermissao = new ArrayList<>();
		List<Predicate> lp = new ArrayList<>(); 
		TypedQuery<Permissao> query = null;
		
		int totalRegistrosPorPaginas = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;
		
		CriteriaBuilder criteriaBuider = entityManager.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteriaQuery = criteriaBuider.createQuery(Permissao.class);
		Root<Permissao> rootFrom = criteriaQuery.from(Permissao.class);
		
		Sort sort = pageable.getSort();
		if ( !sort.isUnsorted() ) {
			Sort.Order order = sort.iterator().next();
			String propriedade = order.getProperty();
			criteriaQuery.orderBy(order.isAscending()? criteriaBuider.asc(rootFrom.get(propriedade)): criteriaBuider.desc(rootFrom.get(propriedade)));
		}
	    
		lp = filtros(permissaoFiltro, criteriaBuider, rootFrom );
		
		if ( lp.size() != -1 ) {
		    criteriaQuery.where(criteriaBuider.and(lp.toArray(new Predicate[lp.size()])));
		    query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}
		
	    query.setFirstResult(primeiroRegistro);
	    query.setMaxResults(totalRegistrosPorPaginas);
		listaPermissao = query.getResultList();
	
	    return new PageImpl<>(listaPermissao, pageable, totalRegistro(lp) );
	}

	@Override
	public int deleteAllPermissao() {
		return entityManager.createQuery("DELETE FROM Permissao p").executeUpdate();
	}
	
    private Long totalRegistro(List<Predicate> lp ) {
		
		TypedQuery<Long> query = null;

		CriteriaBuilder criteriaBuider = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery =  criteriaBuider.createQuery(Long.class);
		Root<Permissao> rootFrom = criteriaQuery.from(Permissao.class);
		criteriaQuery.select(criteriaBuider.count(rootFrom));
		if ( lp.size() != -1 ) {
		    criteriaQuery.where(criteriaBuider.and(lp.toArray(new Predicate[lp.size()])));
		    query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}
		Long result = query.getSingleResult();
		return result;
	}

	
	private List<Predicate> filtros(PermissaoFiltro permissaoFiltro, CriteriaBuilder cb, Root<Permissao> from) {
		List<Predicate> predicados = new ArrayList<>();
		if (!StringUtils.isEmpty(permissaoFiltro.getNome())){
			predicados.add( cb.like(cb.lower(from.get(Permissao_.nome)), "%"+permissaoFiltro.getNome()+"%"));
		} 
		return predicados;
	}

	

}
