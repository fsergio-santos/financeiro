package com.financeiro.service;

import java.util.List;
import java.util.Optional;

import com.financeiro.model.security.LoginAttempt;
import com.financeiro.model.security.LoginAttempt;

public interface LoginAttemptService {
	
	void addRegistroLoginAttempt(LoginAttempt loginAttempt);
	
	void updateRegistroLoginAttempt(LoginAttempt loginAttempt);

	void removeRegistroLoginAttempt(LoginAttempt loginAttempt);
	
	Optional<LoginAttempt> updateQtdFalhasAcesso(String username);
	
	void ressetarQtdFalhasAcesso(String username);


}
