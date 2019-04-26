package com.financeiro.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.model.negocio.Pessoa_;
import com.financeiro.model.negocio.Telefone;
import com.financeiro.model.negocio.Telefone_;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.repository.query.PessoaRepositoryQuery;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Pessoa> listPessoaWithPagination(PessoaFiltro pessoaFiltro, Pageable pageable) {
		List<Pessoa> listaPessoa = new ArrayList<>();
		List<Predicate> lp = new ArrayList<>(); 
		TypedQuery<Pessoa> query = null;
		
		int totalRegistrosPorPaginas = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
		Root<Pessoa> rootFromPessoa = criteriaQuery.from(Pessoa.class);

		Sort sort = pageable.getSort();
		if ( !sort.isUnsorted() ) {
			Sort.Order order = sort.iterator().next();
			String propriedade = order.getProperty();
			criteriaQuery.orderBy(order.isAscending()? criteriaBuilder.asc(rootFromPessoa.get(propriedade)): criteriaBuilder.desc(rootFromPessoa.get(propriedade)));
		}
	
		lp = filtros(pessoaFiltro, criteriaBuilder, rootFromPessoa );
		
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
		Root<Pessoa> rootFrom = criteriaQuery.from(Pessoa.class);
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

    private List<Predicate> filtros(PessoaFiltro pessoaFiltro, CriteriaBuilder cb, Root<Pessoa> from) {
		List<Predicate> predicados = new ArrayList<>();
		if (!StringUtils.isEmpty(pessoaFiltro.getNome())){
			predicados.add( cb.like(cb.lower(from.get(Pessoa_.nome)), "%"+pessoaFiltro.getNome()+"%"));
		} 
		if (!StringUtils.isEmpty(pessoaFiltro.getCpf())){
			predicados.add( cb.like(cb.lower(from.get(Pessoa_.cnpjCpf)), "%"+pessoaFiltro.getCpf()+"%"));
		} 
		if (!StringUtils.isEmpty(pessoaFiltro.getCnpj())){
			predicados.add( cb.like(cb.lower(from.get(Pessoa_.cnpjCpf)), "%"+pessoaFiltro.getCnpj()+"%"));
		} 
		return predicados;
	}

	@Override
	public List<Pessoa> findByNomeStartingWithIgnoreCase(String nome) {
		TypedQuery<Pessoa> query = entityManager.createQuery("SELECT p FROM Pessoa p WHERE lower(p.nome) like lower(:nome)", Pessoa.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}


	@Override
	public Pessoa findByPessoaIdWithTelefone(Integer id) {
        try {   
			CriteriaBuilder criteriaBuider = entityManager.getCriteriaBuilder();
			CriteriaQuery<Pessoa> criteriaQuery = criteriaBuider.createQuery(Pessoa.class);
			Root<Pessoa> rootFromPessoa = criteriaQuery.from(Pessoa.class);
			rootFromPessoa.fetch(Pessoa_.telefones);
			criteriaQuery.select(rootFromPessoa);
			criteriaQuery.where(criteriaBuider.equal(rootFromPessoa.get(Pessoa_.id), id));
			TypedQuery<Pessoa> query = entityManager.createQuery(criteriaQuery);
			Pessoa pessoa = query.getSingleResult();
			return pessoa;
        } catch(NoResultException nre) {
        	return null;
        }
	}
    
	@Override
	public List<Telefone> findTelefoneByIdPessoa(Integer id){
		List<Telefone> listaTelefone = new ArrayList<>();
		CriteriaBuilder criteriaBuider = entityManager.getCriteriaBuilder();
		CriteriaQuery<Telefone> criteriaQuery = criteriaBuider.createQuery(Telefone.class);
		Root<Telefone> rootFromPessoa = criteriaQuery.from(Telefone.class);
		criteriaQuery.select(rootFromPessoa);
		criteriaQuery.where(criteriaBuider.equal(rootFromPessoa.get(Telefone_.pessoa), id));
		TypedQuery<Telefone> query = entityManager.createQuery(criteriaQuery);
		listaTelefone = query.getResultList();
		return listaTelefone;
	}


	@Override
	public Optional<Pessoa> buscarPessoaPeloCnpjCpf(String cnpjCpf) {
		TypedQuery<Pessoa> query = entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.cnpjCpf  = :cnpjCpf", Pessoa.class);
	    return query.setParameter("cnpjCpf", cnpjCpf)
					.setMaxResults(1)
					.getResultList()
					.stream()
					.findFirst();
	}
	
}
