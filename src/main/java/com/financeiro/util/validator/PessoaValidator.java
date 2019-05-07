package com.financeiro.util.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.financeiro.model.negocio.Pessoa;

public class PessoaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Pessoa.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Pessoa pessoa = (Pessoa) target;
		

	}

}
