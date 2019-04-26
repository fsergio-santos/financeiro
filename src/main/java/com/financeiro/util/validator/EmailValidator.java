package com.financeiro.util.validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.financeiro.model.security.Usuario;
import com.financeiro.service.UserService;
import com.financeiro.util.ValidarEmail;

public class EmailValidator implements ConstraintValidator<ValidarEmail, String> {
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	@Autowired
	private UserService usuarioService;

	@Override
	public void initialize(final ValidarEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		boolean valid = false;
		boolean achou = false;

		valid = validateEmail(email);

		achou = validaEmailUsuario(email.trim());

		if (valid == true && achou == false) {
			return true;
		} else {
			return false;
		}

	}

	private boolean validateEmail(final String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean validaEmailUsuario(final String email) {
		boolean toReturn = false;
		try {
			Usuario usuario = usuarioService.findEmail(email);
			toReturn = ( Objects.isNull(usuario) ? false : true );
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return toReturn;
	}
}
