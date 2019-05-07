package com.financeiro.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

import com.financeiro.model.security.RolePermissao_;
import com.financeiro.model.dto.ListaRolePermissao;
import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.security.Permissao;
import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.RolePermissaoId;
import com.financeiro.repository.filtros.RolePermissaoFiltro;
import com.financeiro.repository.query.RolePermissaoRepositoryQuery;


public class RolePermissaoRepositoryImpl implements RolePermissaoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<RolePermissao> listRolePermissionPagination(RolePermissaoFiltro rolePermissaoFiltro, Pageable pageable) {
			List<RolePermissao> listaRolePermissao = new ArrayList<>();
			List<Predicate> lp = new ArrayList<>(); 
			TypedQuery<RolePermissao> query = null;
	
			int totalRegistrosPorPaginas = pageable.getPageSize();
			int paginaAtual = pageable.getPageNumber();
			int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;
	
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolePermissao> criteriaQuery = criteriaBuilder.createQuery(RolePermissao.class);
	
			Root<RolePermissao> rootFromRolePermissao = criteriaQuery.from(RolePermissao.class);
	
			Sort sort = pageable.getSort();
			if ( !sort.isUnsorted() ) {
				Sort.Order order = sort.iterator().next();
				String propriedade = order.getProperty();
				criteriaQuery.orderBy(order.isAscending()? criteriaBuilder.asc(rootFromRolePermissao.get(propriedade)): criteriaBuilder.desc(rootFromRolePermissao.get(propriedade)));
			}
	
			lp = filtros(rolePermissaoFiltro, criteriaBuilder, rootFromRolePermissao );
	
			if ( lp.size() != -1 ) {
				criteriaQuery.where(criteriaBuilder.and(lp.toArray(new Predicate[lp.size()])));
				query = entityManager.createQuery(criteriaQuery);
			} else {
				query = entityManager.createQuery(criteriaQuery);
			}
	
			query.setFirstResult(primeiroRegistro);
			query.setMaxResults(totalRegistrosPorPaginas);
	
			listaRolePermissao = query.getResultList();
	
			return new PageImpl<>(listaRolePermissao, pageable, totalRegistro(lp) );
	}
	
	private Long totalRegistro(List<Predicate> lp) {

		TypedQuery<Long> query = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<RolePermissao> rootFrom = criteriaQuery.from(RolePermissao.class);
		criteriaQuery.select(criteriaBuilder.count(rootFrom));

		if (lp.size() != -1) {
			criteriaQuery.where(criteriaBuilder.and(lp.toArray(new Predicate[lp.size()])));
			query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}
		Long result = query.getSingleResult();
		return result;
	}

	private List<Predicate> filtros(RolePermissaoFiltro rolePermissaoFiltro, CriteriaBuilder cb, Root<RolePermissao> from) {
		List<Predicate> predicados = new ArrayList<>();
		if (!StringUtils.isEmpty(rolePermissaoFiltro.getRole_name())) {
			predicados.add(cb.like(cb.lower(from.get(RolePermissao_.roleId.getName())), "%" + rolePermissaoFiltro.getRole_name() + "%"));
		}
		return predicados;
	}

	@Override
	public List<RolePermissao> findByRolePermissao(Integer role_id, Integer escope_id) {
		Query query = entityManager.createQuery("SELECT rp FROM RolePermissao rp WHERE rp.id.role_id =:role_id and rp.id.escopo_id =:escope_id");
		query.setParameter("role_id", role_id);
		query.setParameter("escope_id", escope_id);
		return query.getResultList();
	}	
	
	
	
	
}
