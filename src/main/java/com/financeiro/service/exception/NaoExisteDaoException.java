package com.financeiro.service.exception;

public class NaoExisteDaoException extends RuntimeException {

	private static final long serialVersionUID = -7607998308112938802L;

	public NaoExisteDaoException(String message) {
        super(message);
    }
}
