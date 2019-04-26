package com.financeiro.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import com.financeiro.model.security.Role_;
import com.financeiro.model.security.Usuario_;
import com.financeiro.model.security.Role;
import com.financeiro.model.security.RolePermissao;
import com.financeiro.model.security.Usuario;
import com.financeiro.repository.filtros.UserFiltro;
import com.financeiro.repository.query.UserRepositoryQuery;

public class UserRepositoryImpl implements UserRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Usuario> listUserWithPagination(UserFiltro userFiltro, Pageable pageable){ 
		List<Usuario> listaUser = new ArrayList<>();
		List<Predicate> lp = new ArrayList<>(); 
		TypedQuery<Usuario> query = null;

		int totalRegistrosPorPaginas = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);

		Root<Usuario> rootFromUser = criteriaQuery.from(Usuario.class);

		Sort sort = pageable.getSort();
		if ( !sort.isUnsorted() ) {
			Sort.Order order = sort.iterator().next();
			String propriedade = order.getProperty();
			criteriaQuery.orderBy(order.isAscending()? criteriaBuilder.asc(rootFromUser.get(propriedade)): criteriaBuilder.desc(rootFromUser.get(propriedade)));
		}

		lp = filtros(userFiltro, criteriaBuilder, rootFromUser );

		if ( lp.size() != -1 ) {
			criteriaQuery.where(criteriaBuilder.and(lp.toArray(new Predicate[lp.size()])));
			query = entityManager.createQuery(criteriaQuery);
		} else {
			query = entityManager.createQuery(criteriaQuery);
		}

		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPaginas);

		listaUser = query.getResultList();

		return new PageImpl<>(listaUser, pageable, totalRegistro(lp) );
	}

	
	@Override
	public Usuario findByEmail(String email) {
		TypedQuery<Usuario> query = entityManager
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}

	@Override
	public Usuario buscarUserAtivoByEmail(String email) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> rootFrom = criteriaQuery.from(Usuario.class);
		Predicate predicates = criteriaBuilder.conjunction();
		predicates = criteriaBuilder.and(criteriaBuilder.equal(rootFrom.get(Usuario_.email), email),
				criteriaBuilder.equal(rootFrom.get(Usuario_.ativo), true));
		criteriaQuery.select(rootFrom);
		criteriaQuery.where(predicates);
		TypedQuery<Usuario> query = entityManager.createQuery(criteriaQuery);
		try {
			Usuario usuario = query.getSingleResult();
			return usuario;
		} catch(NoResultException nre) {
			
		}
		return null;
	}

	@Override
	public Usuario findEmail(String email) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> rootFrom = criteriaQuery.from(Usuario.class);
		Predicate predicates = criteriaBuilder.conjunction();
		predicates = criteriaBuilder.and(criteriaBuilder.equal(rootFrom.get(Usuario_.email), email));
		criteriaQuery.select(rootFrom);
		criteriaQuery.where(predicates);
		TypedQuery<Usuario> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@SuppressWarnings("unused")
	@Override
	public Usuario findByIdAndRoleAndPermission(Integer id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> rootFromUsuario = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(rootFromUsuario);
		Join<Usuario, Role> joinUsuarioRole = rootFromUsuario.join(Usuario_.roles);
		Join<Role, RolePermissao> joinRolePermissao = joinUsuarioRole.join(Role_.rolePermissao);
		Predicate predicates = criteriaBuilder.conjunction();
		predicates = criteriaBuilder.and(criteriaBuilder.equal(rootFromUsuario.get(Usuario_.id), id));
		criteriaQuery.where(predicates);
		TypedQuery<Usuario> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	private Long totalRegistro(List<Predicate> lp) {

		TypedQuery<Long> query = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Usuario> rootFrom = criteriaQuery.from(Usuario.class);
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

	private List<Predicate> filtros(UserFiltro userFiltro, CriteriaBuilder cb, Root<Usuario> from) {
		List<Predicate> predicados = new ArrayList<>();
		if (!StringUtils.isEmpty(userFiltro.getUser_name())) {
			predicados.add(cb.like(cb.lower(from.get(Usuario_.username)), "%" + userFiltro.getUser_name() + "%"));
		}
		return predicados;
	}

}
