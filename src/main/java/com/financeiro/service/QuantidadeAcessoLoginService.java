package com.financeiro.service;

import java.util.List;
import java.util.Optional;

import com.financeiro.model.security.QuantidadesAcessoLogin;
import com.financeiro.model.security.QuantidadesAcessoLogin;

public interface QuantidadeAcessoLoginService {
	
	void addRegistroLoginAttempt(QuantidadesAcessoLogin loginAttempt);
	
	void updateRegistroLoginAttempt(QuantidadesAcessoLogin loginAttempt);

	void removeRegistroLoginAttempt(QuantidadesAcessoLogin loginAttempt);
	
	Optional<QuantidadesAcessoLogin> updateQtdFalhasAcesso(String username);
	
	void ressetarQtdFalhasAcesso(String username);


}
