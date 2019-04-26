package com.financeiro.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.financeiro.model.security.Role;
import com.financeiro.model.security.Role_;
import com.financeiro.repository.filtros.RoleFiltro;
import com.financeiro.repository.query.RoleRepositoryQuery;

public class RoleRepositoryImpl implements RoleRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Role> listRoleWithPagination(RoleFiltro roleFiltro, Pageable pageable){ 
		List<Role> listaRole = new ArrayList<>();
		List<Predicate> lp = new ArrayList<>(); 
		TypedQuery<Role> query = null;

		int totalRegistrosPorPaginas = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);

		Root<Role> rootFromRole = criteriaQuery.from(Role.class);

	    Sort sort = pageable.getSort();
		if ( !sort.isUnsorted() ) {
			Sort.Order order = sort.iterator().next();
			String propriedade = order.getProperty();
			criteriaQuery.orderBy(order.isAscending()? criteriaBuilder.asc(rootFromRole.get(propriedade)): criteriaBuilder.desc(rootFromRole.get(propriedade)));
		}

		lp = filtros(roleFiltro, criteriaBuilder, rootFromRole );

		if ( lp.size() != -1 ) {
			criteriaQuery.where(criteriaBuilder.and(lp.toArray(new Predicate[lp.size()])));
			query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}

		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPaginas);

		listaRole = query.getResultList();

		return new PageImpl<>(listaRole, pageable, totalRegistro(lp) );
	}
	
	
	@Override
	public Role findByRole(String role_name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
		Root<Role> rootFrom = criteriaQuery.from(Role.class);
		Predicate predicates = criteriaBuilder.conjunction();
		predicates = criteriaBuilder.and(criteriaBuilder.equal(rootFrom.get(Role_.nome), role_name));
		criteriaQuery.select(rootFrom);
		criteriaQuery.where(predicates);
		TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	private Long totalRegistro(List<Predicate> lp) {

		TypedQuery<Long> query = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Role> rootFrom = criteriaQuery.from(Role.class);
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

	private List<Predicate> filtros(RoleFiltro roleFiltro, CriteriaBuilder cb, Root<Role> from) {
		List<Predicate> predicados = new ArrayList<>();
		if (!StringUtils.isEmpty(roleFiltro.getRole_name())) {
			predicados.add(cb.like(cb.lower(from.get(Role_.nome)), "%" + roleFiltro.getRole_name() + "%"));
		}
		return predicados;
	}


	@Override
	public Optional<Role> buscarRolePeloNome(String nome) {
		TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.nome  = :nome", Role.class);
	    return query.setParameter("nome", nome)
					.setMaxResults(1)
					.getResultList()
					.stream()
					.findFirst();
	}


	

}
