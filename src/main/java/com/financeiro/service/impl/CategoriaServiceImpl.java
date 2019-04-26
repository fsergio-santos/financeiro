package com.financeiro.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.negocio.Categoria;
import com.financeiro.repository.CategoriaRepository;
import com.financeiro.service.CategoriaService;

@Transactional
@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional(readOnly=true)
	@Override
	public List<Categoria> listarTodasCategorias() {
		return categoriaRepository.findAll();
		
	}

	@Override
	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.saveAndFlush(categoria);
	}

	@Override
	public Categoria findById(Integer id) {
		Categoria categoria = categoriaRepository.getOne(id);
		if (categoria == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoria;
	}

	@Override
	public Categoria salvar(Integer id, Categoria categoria) {
		Categoria categoriaSalva = findById(id);
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaRepository.saveAndFlush(categoriaSalva);
	}

	@Override
	public void delete(Integer id) {
		Categoria categoria = findById(id);
		categoriaRepository.delete(categoria);
	}

}
