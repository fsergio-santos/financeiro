package com.financeiro.util.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.financeiro.model.negocio.Pessoa;
import com.financeiro.service.PessoaService;
import com.financeiro.util.FindCpfCnpj;
    
public class CpfCnpjValidator implements ConstraintValidator<FindCpfCnpj, String>{

	@Autowired
	private PessoaService pessoaService;
	
	@Override
	public void initialize(FindCpfCnpj cnpjCpf) {
		
	}

	@Override
	public boolean isValid(String cnpjCpf, ConstraintValidatorContext constraintValidatorContext) {
    	Optional<Pessoa> pessoa = pessoaService.buscarPessoaPeloCnpjCpf(cnpjCpf);
		if (pessoa.isPresent()) {
			return true;
		}
		return false;
	}

	
}
